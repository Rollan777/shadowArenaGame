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

        Gdx.gl.glClearColor(0.02f, 0.03f, 0.05f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ShapeRenderer shapeRenderer = game.getShapeRenderer();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        hudRenderer.renderPanel(shapeRenderer, 390, 180, 320, 260);
        shapeRenderer.end();

        game.getBatch().begin();

        game.getFont().draw(game.getBatch(), "GAME PAUSED", 500, 390);

        game.getFont().draw(game.getBatch(), "[ R ] Resume", 500, 330);
        game.getFont().draw(game.getBatch(), "[ ESC ] Menu", 495, 300);
        game.getFont().draw(game.getBatch(), "[ Q ] Exit", 510, 270);

        game.getFont().draw(game.getBatch(), "Shadow Arena", 505, 220);
        game.getFont().draw(game.getBatch(), "Game Flow Control", 485, 195);

        game.getBatch().end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            game.getGameFacade().resumeGame(pausedGameScreen);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.getGameFacade().showMenu();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            Gdx.app.exit();
        }
    }
}
