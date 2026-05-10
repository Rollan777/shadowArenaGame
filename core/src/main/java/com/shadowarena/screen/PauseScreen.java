package com.shadowarena.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.shadowarena.ShadowArenaGame;

public class PauseScreen extends ScreenAdapter {

    private final ShadowArenaGame game;
    private final GameScreen pausedGameScreen;

    public PauseScreen(ShadowArenaGame game, GameScreen pausedGameScreen) {
        this.game = game;
        this.pausedGameScreen = pausedGameScreen;
    }

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0.03f, 0.03f, 0.06f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();

        game.getFont().draw(game.getBatch(), "GAME PAUSED", 350, 400);

        game.getFont().draw(game.getBatch(), "Press R to Resume", 325, 340);
        game.getFont().draw(game.getBatch(), "Press M to Menu", 330, 310);
        game.getFont().draw(game.getBatch(), "Press ESC to Exit", 325, 280);

        game.getFont().draw(game.getBatch(), "Shadow Arena", 355, 210);
        game.getFont().draw(game.getBatch(), "Pause Screen", 355, 185);

        game.getBatch().end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            game.getGameFacade().resumeGame(pausedGameScreen);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            game.getGameFacade().showMenu();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }
}
