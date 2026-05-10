package com.shadowarena.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.shadowarena.ShadowArenaGame;
import com.shadowarena.service.GameStatsService;

public class MenuScreen extends ScreenAdapter {

    private final ShadowArenaGame game;

    public MenuScreen(ShadowArenaGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0.06f, 0.07f, 0.10f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        GameStatsService statsService = GameStatsService.getInstance();

        game.getBatch().begin();

        game.getFont().draw(game.getBatch(), "SHADOW ARENA", 345, 410);
        game.getFont().draw(game.getBatch(), "2D Arena Survival Game", 320, 375);

        game.getFont().draw(game.getBatch(), "Press ENTER to Start", 315, 320);
        game.getFont().draw(game.getBatch(), "Press ESC to Exit", 330, 290);

        game.getFont().draw(game.getBatch(), "Best Score: " + statsService.getBestScore(), 335, 235);
        game.getFont().draw(game.getBatch(), "Games Played: " + statsService.getTotalGamesPlayed(), 320, 210);

        game.getFont().draw(game.getBatch(), "Design Patterns Project", 305, 160);
        game.getFont().draw(game.getBatch(), "LibGDX + Java", 350, 135);

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
