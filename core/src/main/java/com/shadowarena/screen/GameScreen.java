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
        playerY = 240;
        playerSpeed = 220f;

        hp = 100;
        score = 0;
        survivalTime = 0f;
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0.05f, 0.10f, 0.08f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();

        game.getFont().draw(game.getBatch(), "GAME SCREEN", 340, 460);
        game.getFont().draw(game.getBatch(), "HP: " + hp, 30, 440);
        game.getFont().draw(game.getBatch(), "Score: " + score, 30, 415);
        game.getFont().draw(game.getBatch(), "Time: " + String.format("%.1f", survivalTime), 30, 390);

        game.getFont().draw(game.getBatch(), "Controls:", 30, 330);
        game.getFont().draw(game.getBatch(), "WASD - Move", 30, 305);
        game.getFont().draw(game.getBatch(), "SPACE - Add score", 30, 280);
        game.getFont().draw(game.getBatch(), "H - Lose HP", 30, 255);
        game.getFont().draw(game.getBatch(), "G - Game Over", 30, 230);
        game.getFont().draw(game.getBatch(), "ESC - Back to Menu", 30, 205);

        game.getFont().draw(game.getBatch(), "PLAYER", playerX, playerY);

        game.getBatch().end();
    }

    private void update(float delta) {
        survivalTime += delta;
        handleInput(delta);

        if (hp <= 0) {
            game.getGameFacade().gameOver(score);
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
            game.getGameFacade().gameOver(score);
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
        if (playerX > Gdx.graphics.getWidth() - 50) {
            playerX = Gdx.graphics.getWidth() - 50;
        }
        if (playerY < 20) {
            playerY = 20;
        }
        if (playerY > Gdx.graphics.getHeight() - 20) {
            playerY = Gdx.graphics.getHeight() - 20;
        }
    }
}
