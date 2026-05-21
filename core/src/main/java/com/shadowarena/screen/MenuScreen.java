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

        Gdx.gl.glClearColor(0.02f, 0.03f, 0.05f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ShapeRenderer shapeRenderer = game.getShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        hudRenderer.renderPanel(shapeRenderer, 350, 145, 400, 330);
        shapeRenderer.end();

        GameStatsService statsService = GameStatsService.getInstance();

        game.getBatch().begin();

        game.getFont().draw(game.getBatch(), "SHADOW ARENA", 490, 420);
        game.getFont().draw(game.getBatch(), "NEON SURVIVAL", 492, 390);

        game.getFont().draw(game.getBatch(), "[ ENTER ]  START GAME", 460, 330);
        game.getFont().draw(game.getBatch(), "[ ESC ]    EXIT", 490, 300);

        game.getFont().draw(game.getBatch(), "Best Score: " + statsService.getBestScore(), 490, 245);
        game.getFont().draw(game.getBatch(), "Games Played: " + statsService.getTotalGamesPlayed(), 475, 220);

        game.getFont().draw(game.getBatch(), "Software Design Patterns Project", 430, 175);
        game.getFont().draw(game.getBatch(), "Java + LibGDX", 500, 150);

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
