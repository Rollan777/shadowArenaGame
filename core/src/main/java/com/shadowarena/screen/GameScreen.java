package com.shadowarena.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.shadowarena.ShadowArenaGame;

public class GameScreen extends ScreenAdapter {

    private final ShadowArenaGame game;

    private float playerX;
    private float playerY;
    private float playerSpeed;

    private int hp;
    private int score;
    private float survivalTime;

    public GameScreen(ShadowArenaGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        playerX = 390;
        playerY = 230;
        playerSpeed = 230f;

        hp = 100;
        score = 0;
        survivalTime = 0f;
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0.04f, 0.09f, 0.08f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();

        drawHud();
        drawPlayer();
        drawHelpText();

        game.getBatch().end();
    }

    private void update(float delta) {
        survivalTime += delta;
        handleInput(delta);

        if (hp <= 0) {
            game.getGameFacade().showGameOver(score);
        }
    }

    private void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerY += playerSpeed * delta;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerY -= playerSpeed * delta;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerX -= playerSpeed * delta;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerX += playerSpeed * delta;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            score += 10;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            hp -= 10;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            game.getGameFacade().showGameOver(score);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.getGameFacade().showMenu();
        }

        clampPlayerToScreen();
    }

    private void clampPlayerToScreen() {
        if (playerX < 0) {
            playerX = 0;
        }

        if (playerX > Gdx.graphics.getWidth() - 60) {
            playerX = Gdx.graphics.getWidth() - 60;
        }

        if (playerY < 20) {
            playerY = 20;
        }

        if (playerY > Gdx.graphics.getHeight() - 20) {
            playerY = Gdx.graphics.getHeight() - 20;
        }
    }

    private void drawHud() {
        game.getFont().draw(game.getBatch(), "SHADOW ARENA", 340, 460);
        game.getFont().draw(game.getBatch(), "HP: " + hp, 30, 440);
        game.getFont().draw(game.getBatch(), "Score: " + score, 30, 415);
        game.getFont().draw(game.getBatch(), "Time: " + String.format("%.1f", survivalTime), 30, 390);
    }

    private void drawPlayer() {
        game.getFont().draw(game.getBatch(), "[ PLAYER ]", playerX, playerY);
    }

    private void drawHelpText() {
        game.getFont().draw(game.getBatch(), "Controls", 610, 440);
        game.getFont().draw(game.getBatch(), "WASD - Move", 610, 410);
        game.getFont().draw(game.getBatch(), "SPACE - Add Score", 610, 385);
        game.getFont().draw(game.getBatch(), "H - Lose HP", 610, 360);
        game.getFont().draw(game.getBatch(), "G - Game Over", 610, 335);
        game.getFont().draw(game.getBatch(), "ESC - Menu", 610, 310);
    }
}
