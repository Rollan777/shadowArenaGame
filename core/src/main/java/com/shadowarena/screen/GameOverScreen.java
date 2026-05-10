package com.shadowarena.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.shadowarena.ShadowArenaGame;

public class GameOverScreen extends ScreenAdapter {

    private final ShadowArenaGame game;
    private final int finalScore;

    public GameOverScreen(ShadowArenaGame game, int finalScore) {
        this.game = game;
        this.finalScore = finalScore;
    }

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0.12f, 0.03f, 0.04f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();

        game.getFont().draw(game.getBatch(), "GAME OVER", 350, 410);
        game.getFont().draw(game.getBatch(), "Final Score: " + finalScore, 330, 365);

        game.getFont().draw(game.getBatch(), "Press R to Restart", 320, 310);
        game.getFont().draw(game.getBatch(), "Press M to Menu", 330, 280);
        game.getFont().draw(game.getBatch(), "Press ESC to Exit", 325, 250);

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
