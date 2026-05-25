package com.shadowarena.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.shadowarena.ShadowArenaGame;
import com.shadowarena.audio.AudioService;
import com.shadowarena.config.GameConfig;
import com.shadowarena.effect.EffectManager;
import com.shadowarena.entity.Player;
import com.shadowarena.manager.EnemyManager;
import com.shadowarena.manager.WaveManager;
import com.shadowarena.observer.GameSubject;
import com.shadowarena.observer.UiManager;
import com.shadowarena.ui.ArenaRenderer;
import com.shadowarena.ui.HudRenderer;

public class GameScreen extends ScreenAdapter {

    private final ShadowArenaGame game;

    private Player player;
    private EnemyManager enemyManager;
    private WaveManager waveManager;

    private GameSubject gameSubject;
    private UiManager uiManager;

    private ArenaRenderer arenaRenderer;
    private HudRenderer hudRenderer;
    private EffectManager effectManager;
    private AudioService audioService;

    private int score;
    private float survivalTime;
    private float damageCooldown;

    private boolean initialized;
    private boolean gameOverTriggered;

    public GameScreen(ShadowArenaGame game) {
        this.game = game;
        this.initialized = false;
        this.gameOverTriggered = false;
    }

    @Override
    public void show() {
        if (!initialized) {
            initializeGame();
            initialized = true;
        }
    }

    private void initializeGame() {
        player = new Player(
            GameConfig.ARENA_X + GameConfig.ARENA_WIDTH / 2f,
            GameConfig.ARENA_Y + GameConfig.ARENA_HEIGHT / 2f
        );

        enemyManager = new EnemyManager();
        waveManager = new WaveManager();

        gameSubject = new GameSubject();
        uiManager = new UiManager();
        gameSubject.addObserver(uiManager);

        arenaRenderer = new ArenaRenderer();
        hudRenderer = new HudRenderer();
        effectManager = new EffectManager();
        audioService = AudioService.getInstance();

        score = 0;
        survivalTime = 0f;
        damageCooldown = 0f;
        gameOverTriggered = false;

        startNextWave();
        notifyUi();
    }

    @Override
    public void render(float delta) {
        update(delta);
        clearScreen();
        renderGameObjects();
        renderHudPanels();
        renderHudText();
        renderEffectsText();
    }

    private void update(float delta) {
        survivalTime += delta;

        if (damageCooldown > 0) {
            damageCooldown -= delta;
        }

        handleGlobalInput();

        player.update(delta);
        enemyManager.update(delta, player);
        effectManager.update(delta);

        int earnedScore = enemyManager.handlePlayerAttack(player, effectManager);

        if (earnedScore > 0) {
            score += earnedScore;
            audioService.playEnemyKill();
        }

        if (damageCooldown <= 0 && enemyManager.checkCollisionWithPlayer(player)) {
            damageCooldown = 0.7f;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            player.heal(10);
        }

        if (enemyManager.isEmpty()) {
            startNextWave();
        }

        notifyUi();

        if (player.isDead() && !gameOverTriggered) {
            gameOverTriggered = true;
            audioService.playGameOver();
            game.getGameFacade().showGameOver(score);
        }
    }

    private void startNextWave() {
        waveManager.startNextWave(enemyManager);

        if (effectManager != null) {
            float waveTextX;
            float waveTextY = GameConfig.ARENA_Y + GameConfig.ARENA_HEIGHT / 2f;

            if (waveManager.isCurrentWaveBossWave()) {
                waveTextX = GameConfig.ARENA_X + GameConfig.ARENA_WIDTH / 2f - 80f;
            } else {
                waveTextX = GameConfig.ARENA_X + GameConfig.ARENA_WIDTH / 2f - 40f;
            }

            effectManager.showWave(waveTextX, waveTextY, waveManager.getCurrentWave());
        }

        if (audioService != null) {
            audioService.playWave();
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
            audioService.playGameOver();
            game.getGameFacade().showGameOver(score);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            audioService.playAttack();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            boolean upgraded = player.applyDamageBoost();

            if (upgraded) {
                audioService.playUpgrade();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            boolean upgraded = player.applySpeedBoost();

            if (upgraded) {
                audioService.playUpgrade();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            boolean upgraded = player.applyHealthBoost();

            if (upgraded) {
                audioService.playUpgrade();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            audioService.toggleMute();
        }
    }

    private void notifyUi() {
        gameSubject.notifyObservers("HP_CHANGED", player.getHp());
        gameSubject.notifyObservers("MAX_HP_CHANGED", player.getMaxHp());
        gameSubject.notifyObservers("SCORE_CHANGED", score);
        gameSubject.notifyObservers("WAVE_CHANGED", waveManager.getCurrentWave());
        gameSubject.notifyObservers("ENEMIES_CHANGED", enemyManager.getEnemyCount());
        gameSubject.notifyObservers("STATE_CHANGED", player.getStateName());
        gameSubject.notifyObservers("SPEED_CHANGED", player.getSpeed());
        gameSubject.notifyObservers("DAMAGE_CHANGED", player.getDamage());
        gameSubject.notifyObservers("TIME_CHANGED", survivalTime);

        gameSubject.notifyObservers("DAMAGE_BOOST_CHANGED", player.getDamageBoostLevel());
        gameSubject.notifyObservers("SPEED_BOOST_CHANGED", player.getSpeedBoostLevel());
        gameSubject.notifyObservers("HEALTH_BOOST_CHANGED", player.getHealthBoostLevel());
        gameSubject.notifyObservers("MAX_UPGRADE_CHANGED", player.getMaxUpgradeLevel());
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0.02f, 0.03f, 0.05f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void renderGameObjects() {
        ShapeRenderer shapeRenderer = game.getShapeRenderer();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        arenaRenderer.render(shapeRenderer);
        player.render(shapeRenderer);
        enemyManager.render(shapeRenderer);
        effectManager.renderParticles(shapeRenderer);
        shapeRenderer.end();
    }

    private void renderHudPanels() {
        ShapeRenderer shapeRenderer = game.getShapeRenderer();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        hudRenderer.renderPanel(
            shapeRenderer,
            GameConfig.SIDEBAR_X,
            GameConfig.SIDEBAR_Y,
            GameConfig.SIDEBAR_WIDTH,
            GameConfig.SIDEBAR_HEIGHT
        );

        hudRenderer.renderHealthBar(
            shapeRenderer,
            GameConfig.SIDEBAR_X + 22f,
            GameConfig.SIDEBAR_Y + GameConfig.SIDEBAR_HEIGHT - 112f,
            GameConfig.SIDEBAR_WIDTH - 44f,
            12f,
            player.getHp(),
            player.getMaxHp()
        );

        shapeRenderer.end();
    }

    private void renderHudText() {
        game.getBatch().begin();
        uiManager.render(game.getBatch(), game.getFont());

        String audioStatus = AudioService.getInstance().isMuted() ? "Audio: OFF" : "Audio: ON";

        game.getFont().draw(
            game.getBatch(),
            audioStatus,
            GameConfig.SIDEBAR_X + 22f,
            GameConfig.SIDEBAR_Y + 25f
        );

        game.getBatch().end();
    }

    private void renderEffectsText() {
        game.getBatch().begin();
        effectManager.renderTexts(game.getBatch(), game.getFont());
        game.getBatch().end();
    }

    @Override
    public void dispose() {
        if (gameSubject != null && uiManager != null) {
            gameSubject.removeObserver(uiManager);
        }

        if (effectManager != null) {
            effectManager.clear();
        }
    }
}
