package com.shadowarena.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.shadowarena.ShadowArenaGame;

public class MenuScreen extends ScreenAdapter {

    private final ShadowArenaGame game;

    public MenuScreen(ShadowArenaGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0.08f, 0.08f, 0.12f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getFont().draw(game.getBatch(), "SHADOW ARENA", 340, 420);
        game.getFont().draw(game.getBatch(), "Press ENTER to Start", 320, 360);
        game.getFont().draw(game.getBatch(), "Press ESC to Exit", 330, 330);
        game.getBatch().draw(game.getFont().getRegion().getTexture(), 0, 0, 0, 0, 0, 0); // harmless no-op draw safety not required, can remove
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
