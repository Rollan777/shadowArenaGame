package com.shadowarena.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.shadowarena.ShadowArenaGame;
import com.shadowarena.service.GameStatsService;
import com.shadowarena.ui.HudRenderer;

public class MenuScreen extends ScreenAdapter {

    private final ShadowArenaGame game;
    private final HudRenderer hudRenderer;

    public MenuScreen(ShadowArenaGame game) {
        this.game = game;
        this.hudRenderer = new HudRenderer();
    }

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0.03f, 0.05f, 0.08f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ShapeRenderer shapeRenderer = game.getShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        hudRenderer.renderPanel(shapeRenderer, 230, 105, 340, 275);
        shapeRenderer.end();

        GameStatsService statsService = GameStatsService.getInstance();

        game.getBatch().begin();

        game.getFont().draw(game.getBatch(), "SHADOW ARENA", 345, 350);
        game.getFont().draw(game.getBatch(), "2D Arena Survival Game", 315, 318);

        game.getFont().draw(game.getBatch(), "Press ENTER to Start", 315, 270);
        game.getFont().draw(game.getBatch(), "Press ESC to Exit", 330, 245);

        game.getFont().draw(game.getBatch(), "Best Score: " + statsService.getBestScore(), 335, 205);
        game.getFont().draw(game.getBatch(), "Games Played: " + statsService.getTotalGamesPlayed(), 320, 180);

        game.getFont().draw(game.getBatch(), "Software Design Patterns Project", 285, 135);
        game.getFont().draw(game.getBatch(), "Java + LibGDX", 350, 112);

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
