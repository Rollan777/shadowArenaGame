package com.shadowarena.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.shadowarena.ShadowArenaGame;
import com.shadowarena.service.GameStatsService;
import com.shadowarena.ui.HudRenderer;

public class GameOverScreen extends ScreenAdapter {

    private final ShadowArenaGame game;
    private final int finalScore;
    private final HudRenderer hudRenderer;

    public GameOverScreen(ShadowArenaGame game, int finalScore) {
        this.game = game;
        this.finalScore = finalScore;
        this.hudRenderer = new HudRenderer();
    }

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0.08f, 0.02f, 0.03f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ShapeRenderer shapeRenderer = game.getShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        hudRenderer.renderPanel(shapeRenderer, 245, 105, 310, 280);
        shapeRenderer.end();

        GameStatsService statsService = GameStatsService.getInstance();

        game.getBatch().begin();

        game.getFont().draw(game.getBatch(), "GAME OVER", 355, 350);
        game.getFont().draw(game.getBatch(), "Final Score: " + finalScore, 330, 305);
        game.getFont().draw(game.getBatch(), "Best Score: " + statsService.getBestScore(), 330, 280);
        game.getFont().draw(game.getBatch(), "Games Played: " + statsService.getTotalGamesPlayed(), 315, 255);

        game.getFont().draw(game.getBatch(), "Press R to Restart", 320, 205);
        game.getFont().draw(game.getBatch(), "Press M to Menu", 330, 180);
        game.getFont().draw(game.getBatch(), "Press ESC to Exit", 325, 155);

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
