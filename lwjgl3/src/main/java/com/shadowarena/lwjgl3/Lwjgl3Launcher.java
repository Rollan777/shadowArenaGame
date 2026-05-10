package com.shadowarena.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.shadowarena.ShadowArenaGame;
import com.shadowarena.service.GameSettings;

public class Lwjgl3Launcher {

    public static void main(String[] args) {
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new ShadowArenaGame(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        GameSettings settings = GameSettings.getInstance();

        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();

        configuration.setTitle(settings.getGameTitle());
        configuration.setWindowedMode(settings.getWindowWidth(), settings.getWindowHeight());
        configuration.setForegroundFPS(settings.getTargetFps());
        configuration.useVsync(true);

        return configuration;
    }
}
