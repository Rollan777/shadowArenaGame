package com.shadowarena.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.shadowarena.ShadowArenaGame;
import com.shadowarena.service.GameStatsService;

public class GameOverScreen extends ScreenAdapter {

    private final ShadowArenaGame game;
    private final int finalScore;

    private float animationTimer;

    public GameOverScreen(ShadowArenaGame game, int finalScore) {
        this.game = game;
        this.finalScore = finalScore;
        this.animationTimer = 0f;
    }

    @Override
    public void render(float delta) {
        animationTimer += delta;
        handleInput();

        clearScreen();
        renderBackground();
        renderGameOverText();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0.04f, 0.01f, 0.02f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void renderBackground() {
        ShapeRenderer shapeRenderer = game.getShapeRenderer();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        renderGrid(shapeRenderer);
        renderPanel(shapeRenderer);
        renderButtons(shapeRenderer);
        renderDecorations(shapeRenderer);

        shapeRenderer.end();
    }

    private void renderGrid(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(new Color(0.14f, 0.04f, 0.06f, 1f));

        for (int x = 0; x < Gdx.graphics.getWidth(); x += 50) {
            shapeRenderer.rectLine(x, 0, x, Gdx.graphics.getHeight(), 1f);
        }

        for (int y = 0; y < Gdx.graphics.getHeight(); y += 50) {
            shapeRenderer.rectLine(0, y, Gdx.graphics.getWidth(), y, 1f);
        }
    }

    private void renderPanel(ShapeRenderer shapeRenderer) {
        float panelX = 360;
        float panelY = 120;
        float panelWidth = 380;
        float panelHeight = 390;

        shapeRenderer.setColor(new Color(0.10f, 0.04f, 0.06f, 0.96f));
        shapeRenderer.rect(panelX, panelY, panelWidth, panelHeight);

        shapeRenderer.setColor(new Color(1.0f, 0.18f, 0.28f, 1f));
        shapeRenderer.rectLine(panelX, panelY, panelX + panelWidth, panelY, 3f);
        shapeRenderer.rectLine(panelX, panelY + panelHeight, panelX + panelWidth, panelY + panelHeight, 3f);
        shapeRenderer.rectLine(panelX, panelY, panelX, panelY + panelHeight, 3f);
        shapeRenderer.rectLine(panelX + panelWidth, panelY, panelX + panelWidth, panelY + panelHeight, 3f);

        shapeRenderer.setColor(new Color(1.0f, 0.55f, 0.0f, 1f));
        shapeRenderer.rectLine(panelX + 12, panelY + 12, panelX + panelWidth - 12, panelY + 12, 1.5f);
        shapeRenderer.rectLine(panelX + 12, panelY + panelHeight - 12, panelX + panelWidth - 12, panelY + panelHeight - 12, 1.5f);
        shapeRenderer.rectLine(panelX + 12, panelY + 12, panelX + 12, panelY + panelHeight - 12, 1.5f);
        shapeRenderer.rectLine(panelX + panelWidth - 12, panelY + 12, panelX + panelWidth - 12, panelY + panelHeight - 12, 1.5f);
    }

    private void renderButtons(ShapeRenderer shapeRenderer) {
        renderButton(shapeRenderer, 420, 245, 260, 42, new Color(0.02f, 0.22f, 0.28f, 1f), new Color(0.0f, 0.85f, 1.0f, 1f));
        renderButton(shapeRenderer, 420, 190, 260, 42, new Color(0.12f, 0.08f, 0.20f, 1f), new Color(0.65f, 0.25f, 1.0f, 1f));
        renderButton(shapeRenderer, 420, 135, 260, 42, new Color(0.16f, 0.07f, 0.10f, 1f), new Color(1.0f, 0.25f, 0.35f, 1f));
    }

    private void renderButton(
        ShapeRenderer shapeRenderer,
        float x,
        float y,
        float width,
        float height,
        Color fillColor,
        Color borderColor
    ) {
        shapeRenderer.setColor(fillColor);
        shapeRenderer.rect(x, y, width, height);

        shapeRenderer.setColor(borderColor);
        shapeRenderer.rectLine(x, y, x + width, y, 2f);
        shapeRenderer.rectLine(x, y + height, x + width, y + height, 2f);
        shapeRenderer.rectLine(x, y, x, y + height, 2f);
        shapeRenderer.rectLine(x + width, y, x + width, y + height, 2f);
    }

    private void renderDecorations(ShapeRenderer shapeRenderer) {
        float pulse = 9f + (float) Math.sin(animationTimer * 4f) * 4f;

        shapeRenderer.setColor(new Color(1.0f, 0.18f, 0.28f, 0.85f));
        shapeRenderer.circle(265, 445, pulse);

        shapeRenderer.setColor(new Color(1.0f, 0.55f, 0.0f, 0.8f));
        shapeRenderer.circle(845, 170, pulse + 2f);

        shapeRenderer.setColor(new Color(0.7f, 0.2f, 1.0f, 0.7f));
        shapeRenderer.circle(845, 440, pulse - 2f);
    }

    private void renderGameOverText() {
        GameStatsService statsService = GameStatsService.getInstance();

        game.getBatch().begin();

        game.getFont().setColor(Color.WHITE);

        game.getFont().draw(game.getBatch(), "GAME OVER", 510, 455);
        game.getFont().draw(game.getBatch(), "SURVIVAL FAILED", 493, 425);

        game.getFont().draw(game.getBatch(), "Final Score: " + finalScore, 485, 365);
        game.getFont().draw(game.getBatch(), "Best Score: " + statsService.getBestScore(), 485, 338);
        game.getFont().draw(game.getBatch(), "Games Played: " + statsService.getTotalGamesPlayed(), 470, 311);

        game.getFont().draw(game.getBatch(), "[ R ]     RESTART", 485, 272);
        game.getFont().draw(game.getBatch(), "[ M ]     MENU", 500, 217);
        game.getFont().draw(game.getBatch(), "[ ESC ]   EXIT", 493, 162);

        game.getFont().draw(game.getBatch(), "Shadow Arena", 505, 92);
        game.getFont().draw(game.getBatch(), "Java + LibGDX", 505, 70);

        game.getFont().setColor(Color.WHITE);

        game.getBatch().end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            game.getGameFacade().startNewGame();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            game.getGameFacade().showMenu();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }
}
