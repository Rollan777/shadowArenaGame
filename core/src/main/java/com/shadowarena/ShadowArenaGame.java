package com.shadowarena;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.shadowarena.audio.AudioService;
import com.shadowarena.facade.GameFacade;
import com.shadowarena.screen.MenuScreen;

public class ShadowArenaGame extends Game {

    private SpriteBatch batch;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;
    private GameFacade gameFacade;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
        gameFacade = new GameFacade(this);

        setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        if (getScreen() != null) {
            getScreen().dispose();
        }

        AudioService.getInstance().dispose();

        batch.dispose();
        font.dispose();
        shapeRenderer.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public BitmapFont getFont() {
        return font;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public GameFacade getGameFacade() {
        return gameFacade;
    }
}
