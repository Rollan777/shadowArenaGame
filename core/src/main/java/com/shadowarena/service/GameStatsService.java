package com.shadowarena.service;

public class GameStatsService {

    private static GameStatsService instance;

    private int totalGamesPlayed;
    private int bestScore;
    private int lastScore;

    private GameStatsService() {
        this.totalGamesPlayed = 0;
        this.bestScore = 0;
        this.lastScore = 0;
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
