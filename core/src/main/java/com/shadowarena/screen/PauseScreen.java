package com.shadowarena.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.shadowarena.ShadowArenaGame;
import com.shadowarena.ui.HudRenderer;

public class PauseScreen extends ScreenAdapter {

    private final ShadowArenaGame game;
    private final GameScreen pausedGameScreen;
    private final HudRenderer hudRenderer;

    public PauseScreen(ShadowArenaGame game, GameScreen pausedGameScreen) {
        this.game = game;
        this.pausedGameScreen = pausedGameScreen;
        this.hudRenderer = new HudRenderer();
    }

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0.03f, 0.03f, 0.06f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ShapeRenderer shapeRenderer = game.getShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        hudRenderer.renderPanel(shapeRenderer, 250, 130, 300, 230);
        shapeRenderer.end();

        game.getBatch().begin();

        game.getFont().draw(game.getBatch(), "GAME PAUSED", 350, 325);

        game.getFont().draw(game.getBatch(), "Press R to Resume", 325, 275);
        game.getFont().draw(game.getBatch(), "Press M to Menu", 330, 250);
        game.getFont().draw(game.getBatch(), "Press ESC to Exit", 325, 225);

        game.getFont().draw(game.getBatch(), "Shadow Arena", 355, 170);
        game.getFont().draw(game.getBatch(), "Game Flow Control", 335, 145);

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
