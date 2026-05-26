package com.shadowarena.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameStatsService {

    private static GameStatsService instance;

    private static final String PREF_NAME = "shadow_arena_stats";
    private static final String KEY_TOTAL_GAMES = "total_games_played";
    private static final String KEY_BEST_SCORE = "best_score";
    private static final String KEY_LAST_SCORE = "last_score";

    private int totalGamesPlayed;
    private int bestScore;
    private int lastScore;

    private final Preferences preferences;

    private GameStatsService() {
        preferences = Gdx.app.getPreferences(PREF_NAME);

        totalGamesPlayed = preferences.getInteger(KEY_TOTAL_GAMES, 0);
        bestScore = preferences.getInteger(KEY_BEST_SCORE, 0);
        lastScore = preferences.getInteger(KEY_LAST_SCORE, 0);
    }

    public static GameStatsService getInstance() {
        if (instance == null) {
            instance = new GameStatsService();
        }

        return instance;
    }

    public void registerGameResult(int score) {
        totalGamesPlayed++;
        lastScore = score;

        if (score > bestScore) {
            bestScore = score;
        }

        saveStats();
    }

    private void saveStats() {
        preferences.putInteger(KEY_TOTAL_GAMES, totalGamesPlayed);
        preferences.putInteger(KEY_BEST_SCORE, bestScore);
        preferences.putInteger(KEY_LAST_SCORE, lastScore);
        preferences.flush();
    }

    public void resetStats() {
        totalGamesPlayed = 0;
        bestScore = 0;
        lastScore = 0;

        saveStats();
    }

    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public int getBestScore() {
        return bestScore;
    }

    public int getLastScore() {
        return lastScore;
    }
}
