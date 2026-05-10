package com.shadowarena.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.shadowarena.ShadowArenaGame;
import com.shadowarena.decorator.DamageBoostDecorator;
import com.shadowarena.decorator.HealthBoostDecorator;
import com.shadowarena.decorator.SpeedBoostDecorator;
import com.shadowarena.entity.Player;
import com.shadowarena.manager.EnemyManager;
import com.shadowarena.manager.WaveManager;
import com.shadowarena.observer.GameSubject;
import com.shadowarena.observer.UiManager;

public class GameScreen extends ScreenAdapter {

    private final ShadowArenaGame game;

    private Player player;
    private EnemyManager enemyManager;
    private WaveManager waveManager;

    private GameSubject gameSubject;
    private UiManager uiManager;

    private int score;
    private float survivalTime;
    private float damageCooldown;

    private boolean initialized;

    public GameScreen(ShadowArenaGame game) {
        this.game = game;
        this.initialized = false;
    }

    @Override
    public void show() {
        if (!initialized) {
            initializeGame();
            initialized = true;
        }
    }

    private void initializeGame() {
        player = new Player(384, 224);
        enemyManager = new EnemyManager();
        waveManager = new WaveManager();

        gameSubject = new GameSubject();
        uiManager = new UiManager();
        gameSubject.addObserver(uiManager);

        score = 0;
        survivalTime = 0f;
        damageCooldown = 0f;

        waveManager.startNextWave(enemyManager);
        notifyUi();
    }

    @Override
    public void render(float delta) {
        update(delta);
        clearScreen();
        renderGameObjects();
        renderHud();
    }

    private void update(float delta) {
        survivalTime += delta;

        if (damageCooldown > 0) {
            damageCooldown -= delta;
        }

        handleGlobalInput();

        player.update(delta);
        enemyManager.update(delta, player);

        int earnedScore = enemyManager.handlePlayerAttack(player);

        if (earnedScore > 0) {
            score += earnedScore;
        }

        if (damageCooldown <= 0 && enemyManager.checkCollisionWithPlayer(player)) {
            damageCooldown = 0.7f;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            player.heal(10);
        }

        if (enemyManager.isEmpty()) {
            waveManager.startNextWave(enemyManager);
        }

        notifyUi();

        if (player.isDead()) {
            game.getGameFacade().showGameOver(score);
        }
    }

    private void handleGlobalInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            game.getGameFacade().pauseGame(this);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.getGameFacade().showMenu();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            game.getGameFacade().showGameOver(score);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            player.applyStats(new DamageBoostDecorator(player.getStats()));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            player.applyStats(new SpeedBoostDecorator(player.getStats()));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            player.applyStats(new HealthBoostDecorator(player.getStats()));
        }
    }

    private void notifyUi() {
        gameSubject.notifyObservers("HP_CHANGED", player.getHp());
        gameSubject.notifyObservers("MAX_HP_CHANGED", player.getMaxHp());
        gameSubject.notifyObservers("SCORE_CHANGED", score);
        gameSubject.notifyObservers("WAVE_CHANGED", waveManager.getCurrentWave());
        gameSubject.notifyObservers("ENEMIES_CHANGED", enemyManager.getEnemyCount());
        gameSubject.notifyObservers("STATE_CHANGED", player.getStateName());
        gameSubject.notifyObservers("UPGRADES_CHANGED", player.getStatsDescription());
        gameSubject.notifyObservers("SPEED_CHANGED", player.getSpeed());
        gameSubject.notifyObservers("DAMAGE_CHANGED", player.getDamage());
        gameSubject.notifyObservers("TIME_CHANGED", survivalTime);
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0.04f, 0.08f, 0.09f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void renderGameObjects() {
        ShapeRenderer shapeRenderer = game.getShapeRenderer();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        player.render(shapeRenderer);
        enemyManager.render(shapeRenderer);
        shapeRenderer.end();
    }

    private void renderHud() {
        game.getBatch().begin();
        uiManager.render(game.getBatch(), game.getFont());
        game.getFont().draw(game.getBatch(), "P - Pause", 590, 215);
        game.getBatch().end();
    }

    @Override
    public void dispose() {
        if (gameSubject != null && uiManager != null) {
            gameSubject.removeObserver(uiManager);
        }
    }
}
