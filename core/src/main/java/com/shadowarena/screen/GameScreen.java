package com.shadowarena.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.shadowarena.ShadowArenaGame;
import com.shadowarena.entity.Player;
import com.shadowarena.factory.EnemyFactory;
import com.shadowarena.factory.EnemyType;
import com.shadowarena.manager.EnemyManager;

public class GameScreen extends ScreenAdapter {

    private final ShadowArenaGame game;

    private Player player;
    private EnemyManager enemyManager;
    private EnemyFactory enemyFactory;

    private int score;
    private float survivalTime;
    private float damageCooldown;

    public GameScreen(ShadowArenaGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        player = new Player(384, 224);
        enemyManager = new EnemyManager();
        enemyFactory = new EnemyFactory();

        score = 0;
        survivalTime = 0f;
        damageCooldown = 0f;

        spawnTestEnemies();
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

        if (damageCooldown <= 0 && enemyManager.checkCollisionWithPlayer(player)) {
            damageCooldown = 0.7f;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            score += 10;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            player.heal(10);
        }

        if (player.isDead()) {
            game.getGameFacade().showGameOver(score);
        }
    }

    private void spawnTestEnemies() {
        enemyManager.addEnemy(enemyFactory.createEnemy(EnemyType.FAST, 80, 100));
        enemyManager.addEnemy(enemyFactory.createEnemy(EnemyType.TANK, 700, 350));
        enemyManager.addEnemy(enemyFactory.createEnemy(EnemyType.RANGED, 700, 100));
    }

    private void handleGlobalInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.getGameFacade().showMenu();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            game.getGameFacade().showGameOver(score);
        }
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

        game.getFont().draw(game.getBatch(), "SHADOW ARENA", 340, 460);

        game.getFont().draw(game.getBatch(), "HP: " + player.getHp() + "/" + player.getMaxHp(), 30, 440);
        game.getFont().draw(game.getBatch(), "Score: " + score, 30, 415);
        game.getFont().draw(game.getBatch(), "Time: " + String.format("%.1f", survivalTime), 30, 390);
        game.getFont().draw(game.getBatch(), "Enemies: " + enemyManager.getEnemyCount(), 30, 365);

        game.getFont().draw(game.getBatch(), "Controls", 610, 440);
        game.getFont().draw(game.getBatch(), "WASD - Move", 610, 415);
        game.getFont().draw(game.getBatch(), "SPACE - Add Score", 610, 390);
        game.getFont().draw(game.getBatch(), "J - Heal", 610, 365);
        game.getFont().draw(game.getBatch(), "G - Game Over", 610, 340);
        game.getFont().draw(game.getBatch(), "ESC - Menu", 610, 315);

        game.getBatch().end();
    }
}
