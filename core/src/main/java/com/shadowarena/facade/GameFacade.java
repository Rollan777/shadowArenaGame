package com.shadowarena.facade;

import com.shadowarena.ShadowArenaGame;
import com.shadowarena.screen.GameOverScreen;
import com.shadowarena.screen.GameScreen;
import com.shadowarena.screen.MenuScreen;

public class GameFacade {

    private final ShadowArenaGame game;

    public GameFacade(ShadowArenaGame game) {
        this.game = game;
    }

    public void showMenu() {
        game.setScreen(new MenuScreen(game));
    }

    public void startNewGame() {
        game.setScreen(new GameScreen(game));
    }

    public void showGameOver(int score) {
        game.setScreen(new GameOverScreen(game, score));
    }
}
