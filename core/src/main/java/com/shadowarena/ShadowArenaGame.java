package com.shadowarena;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.shadowarena.facade.GameFacade;
import com.shadowarena.screen.MenuScreen;

public class ShadowArenaGame extends Game {

    private SpriteBatch batch;
    private BitmapFont font;
    private GameFacade gameFacade;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        gameFacade = new GameFacade(this);

        setScreen(new MenuScreen(this));
    }

    @Override
    public void dispose() {
        if (screen != null) {
            screen.dispose();
        }
        batch.dispose();
        font.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public BitmapFont getFont() {
        return font;
    }

    public GameFacade getGameFacade() {
        return gameFacade;
    }
}
