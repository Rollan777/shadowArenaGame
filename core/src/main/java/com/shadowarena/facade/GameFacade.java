package com.shadowarena.facade;

import com.shadowarena.ShadowArenaGame;
import com.shadowarena.screen.GameOverScreen;
import com.shadowarena.screen.GameScreen;
import com.shadowarena.screen.MenuScreen;
import com.shadowarena.screen.PauseScreen;
import com.shadowarena.service.GameStatsService;

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

    public void pauseGame(GameScreen currentGameScreen) {
        game.setScreen(new PauseScreen(game, currentGameScreen));
    }

    public void resumeGame(GameScreen pausedGameScreen) {
        game.setScreen(pausedGameScreen);
    }

    public void showGameOver(int score) {
        GameStatsService.getInstance().registerGameResult(score);
        game.setScreen(new GameOverScreen(game, score));
    }
}
