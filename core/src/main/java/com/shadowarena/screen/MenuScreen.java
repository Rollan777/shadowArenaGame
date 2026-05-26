package com.shadowarena.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.shadowarena.ShadowArenaGame;
import com.shadowarena.service.GameStatsService;

public class MenuScreen extends ScreenAdapter {

    private final ShadowArenaGame game;
    private float animationTimer;

    public MenuScreen(ShadowArenaGame game) {
        this.game = game;
        this.animationTimer = 0f;
    }

    @Override
    public void render(float delta) {
        animationTimer += delta;
        handleInput();

        clearScreen();
        renderBackground();
        renderMenuText();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0.015f, 0.025f, 0.04f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void renderBackground() {
        ShapeRenderer shapeRenderer = game.getShapeRenderer();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Background grid
        shapeRenderer.setColor(new Color(0.06f, 0.12f, 0.16f, 1f));

        for (int x = 0; x < Gdx.graphics.getWidth(); x += 50) {
            shapeRenderer.rectLine(x, 0, x, Gdx.graphics.getHeight(), 1f);
        }

        for (int y = 0; y < Gdx.graphics.getHeight(); y += 50) {
            shapeRenderer.rectLine(0, y, Gdx.graphics.getWidth(), y, 1f);
        }

        // Main panel
        float panelX = 320;
        float panelY = 130;
        float panelWidth = 460;
        float panelHeight = 360;

        shapeRenderer.setColor(new Color(0.06f, 0.08f, 0.12f, 0.95f));
        shapeRenderer.rect(panelX, panelY, panelWidth, panelHeight);

        // Neon border
        shapeRenderer.setColor(new Color(0.0f, 0.8f, 1.0f, 1f));
        shapeRenderer.rectLine(panelX, panelY, panelX + panelWidth, panelY, 3f);
        shapeRenderer.rectLine(panelX, panelY + panelHeight, panelX + panelWidth, panelY + panelHeight, 3f);
        shapeRenderer.rectLine(panelX, panelY, panelX, panelY + panelHeight, 3f);
        shapeRenderer.rectLine(panelX + panelWidth, panelY, panelX + panelWidth, panelY + panelHeight, 3f);

        // Inner thin border
        shapeRenderer.setColor(new Color(0.35f, 0.15f, 0.85f, 1f));
        shapeRenderer.rectLine(panelX + 12, panelY + 12, panelX + panelWidth - 12, panelY + 12, 1.5f);
        shapeRenderer.rectLine(panelX + 12, panelY + panelHeight - 12, panelX + panelWidth - 12, panelY + panelHeight - 12, 1.5f);
        shapeRenderer.rectLine(panelX + 12, panelY + 12, panelX + 12, panelY + panelHeight - 12, 1.5f);
        shapeRenderer.rectLine(panelX + panelWidth - 12, panelY + 12, panelX + panelWidth - 12, panelY + panelHeight - 12, 1.5f);

        // Start button
        shapeRenderer.setColor(new Color(0.02f, 0.22f, 0.28f, 1f));
        shapeRenderer.rect(420, 275, 260, 42);

        shapeRenderer.setColor(new Color(0.0f, 0.85f, 1.0f, 1f));
        shapeRenderer.rectLine(420, 275, 680, 275, 2f);
        shapeRenderer.rectLine(420, 317, 680, 317, 2f);
        shapeRenderer.rectLine(420, 275, 420, 317, 2f);
        shapeRenderer.rectLine(680, 275, 680, 317, 2f);

        // Exit button
        shapeRenderer.setColor(new Color(0.16f, 0.07f, 0.10f, 1f));
        shapeRenderer.rect(420, 220, 260, 42);

        shapeRenderer.setColor(new Color(1.0f, 0.25f, 0.35f, 1f));
        shapeRenderer.rectLine(420, 220, 680, 220, 2f);
        shapeRenderer.rectLine(420, 262, 680, 262, 2f);
        shapeRenderer.rectLine(420, 220, 420, 262, 2f);
        shapeRenderer.rectLine(680, 220, 680, 262, 2f);

        // Decorative neon circles
        float pulse = 8f + (float) Math.sin(animationTimer * 3f) * 3f;

        shapeRenderer.setColor(new Color(0.0f, 0.8f, 1.0f, 0.8f));
        shapeRenderer.circle(250, 430, pulse);

        shapeRenderer.setColor(new Color(1.0f, 0.45f, 0.0f, 0.8f));
        shapeRenderer.circle(850, 180, pulse + 3f);

        shapeRenderer.setColor(new Color(0.7f, 0.2f, 1.0f, 0.8f));
        shapeRenderer.circle(880, 430, pulse - 2f);

        shapeRenderer.end();
    }

    private void renderMenuText() {
        GameStatsService statsService = GameStatsService.getInstance();

        game.getBatch().begin();

        game.getFont().setColor(Color.WHITE);

        game.getFont().draw(game.getBatch(), "SHADOW ARENA", 495, 440);
        game.getFont().draw(game.getBatch(), "NEON SURVIVAL", 498, 410);

        game.getFont().draw(game.getBatch(), "[ ENTER ]   START GAME", 455, 302);
        game.getFont().draw(game.getBatch(), "[ ESC ]     EXIT", 490, 247);

        game.getFont().draw(game.getBatch(), "Best Score: " + statsService.getBestScore(), 500, 185);
        game.getFont().draw(game.getBatch(), "Games Played: " + statsService.getTotalGamesPlayed(), 485, 160);

        game.getFont().draw(game.getBatch(), "Software Design Patterns Project", 430, 105);
        game.getFont().draw(game.getBatch(), "Java + LibGDX", 505, 82);

        game.getFont().setColor(Color.WHITE);

        game.getBatch().end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.getGameFacade().startNewGame();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }
}
