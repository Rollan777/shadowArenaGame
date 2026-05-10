package com.shadowarena.service;

public class GameSettings {

    private static GameSettings instance;

    private final int windowWidth;
    private final int windowHeight;
    private final int targetFps;
    private final String gameTitle;

    private GameSettings() {
        this.windowWidth = 800;
        this.windowHeight = 480;
        this.targetFps = 60;
        this.gameTitle = "Shadow Arena";
    }

    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }

        return instance;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public int getTargetFps() {
        return targetFps;
    }

    public String getGameTitle() {
        return gameTitle;
    }
}
