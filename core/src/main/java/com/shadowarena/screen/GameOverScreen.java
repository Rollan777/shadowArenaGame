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

        Gdx.gl.glClearColor(0.07f, 0.02f, 0.03f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ShapeRenderer shapeRenderer = game.getShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        hudRenderer.renderPanel(shapeRenderer, 375, 145, 350, 330);
        shapeRenderer.end();

        GameStatsService statsService = GameStatsService.getInstance();

        game.getBatch().begin();

        game.getFont().draw(game.getBatch(), "GAME OVER", 510, 420);
        game.getFont().draw(game.getBatch(), "Final Score: " + finalScore, 485, 360);
        game.getFont().draw(game.getBatch(), "Best Score: " + statsService.getBestScore(), 485, 335);
        game.getFont().draw(game.getBatch(), "Games Played: " + statsService.getTotalGamesPlayed(), 470, 310);

        game.getFont().draw(game.getBatch(), "[ R ] Restart", 500, 250);
        game.getFont().draw(game.getBatch(), "[ M ] Menu", 510, 220);
        game.getFont().draw(game.getBatch(), "[ ESC ] Exit", 505, 190);

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
