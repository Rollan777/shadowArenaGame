package com.shadowarena.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.shadowarena.ShadowArenaGame;
import com.shadowarena.entity.Player;

public class GameScreen extends ScreenAdapter {

    private final ShadowArenaGame game;

    private Player player;

    private int score;
    private float survivalTime;

    public GameScreen(ShadowArenaGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        player = new Player(384, 224);

        score = 0;
        survivalTime = 0f;
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

        handleGlobalInput();
        player.update(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            score += 10;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            player.takeDamage(10);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            player.heal(10);
        }

        if (player.isDead()) {
            game.getGameFacade().showGameOver(score);
        }
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
        shapeRenderer.end();
    }

    private void renderHud() {
        game.getBatch().begin();

        game.getFont().draw(game.getBatch(), "SHADOW ARENA", 340, 460);

        game.getFont().draw(game.getBatch(), "HP: " + player.getHp() + "/" + player.getMaxHp(), 30, 440);
        game.getFont().draw(game.getBatch(), "Score: " + score, 30, 415);
        game.getFont().draw(game.getBatch(), "Time: " + String.format("%.1f", survivalTime), 30, 390);

        game.getFont().draw(game.getBatch(), "Player Position", 30, 335);
        game.getFont().draw(game.getBatch(), "X: " + (int) player.getX(), 30, 310);
        game.getFont().draw(game.getBatch(), "Y: " + (int) player.getY(), 30, 285);

        game.getFont().draw(game.getBatch(), "Controls", 610, 440);
        game.getFont().draw(game.getBatch(), "WASD - Move", 610, 415);
        game.getFont().draw(game.getBatch(), "SPACE - Add Score", 610, 390);
        game.getFont().draw(game.getBatch(), "H - Damage", 610, 365);
        game.getFont().draw(game.getBatch(), "J - Heal", 610, 340);
        game.getFont().draw(game.getBatch(), "G - Game Over", 610, 315);
        game.getFont().draw(game.getBatch(), "ESC - Menu", 610, 290);

        game.getBatch().end();
    }
}
