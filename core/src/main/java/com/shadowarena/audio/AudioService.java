package com.shadowarena.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

import java.util.HashMap;
import java.util.Map;

public class AudioService {

    private static AudioService instance;

    private final Map<String, Sound> sounds;
    private boolean muted;
    private float volume;

    private AudioService() {
        this.sounds = new HashMap<>();
        this.muted = false;
        this.volume = 1.0f;

        loadSound("attack", "sounds/attack.wav");
        loadSound("enemy_kill", "sounds/enemy_kill.wav");
        loadSound("wave", "sounds/wave.wav");
        loadSound("upgrade", "sounds/upgrade.wav");
        loadSound("game_over", "sounds/game_over.wav");
    }

    public static AudioService getInstance() {
        if (instance == null) {
            instance = new AudioService();
        }

        return instance;
    }

    private void loadSound(String key, String path) {
        try {
            FileHandle file = Gdx.files.internal(path);

            if (file.exists()) {
                Sound sound = Gdx.audio.newSound(file);
                sounds.put(key, sound);
                System.out.println("[AUDIO] Loaded: " + path);
            } else {
                System.out.println("[AUDIO] NOT FOUND: " + path);
            }
        } catch (Exception e) {
            System.out.println("[AUDIO] ERROR loading " + path);
            e.printStackTrace();
        }
    }

    public void playAttack() {
        play("attack");
    }

    public void playEnemyKill() {
        play("enemy_kill");
    }

    public void playWave() {
        play("wave");
    }

    public void playUpgrade() {
        play("upgrade");
    }

    public void playGameOver() {
        play("game_over");
    }

    private void play(String key) {
        if (muted) {
            System.out.println("[AUDIO] Muted, not playing: " + key);
            return;
        }

        Sound sound = sounds.get(key);

        if (sound != null) {
            sound.play(volume);
            System.out.println("[AUDIO] Playing: " + key);
        } else {
            System.out.println("[AUDIO] Missing sound in map: " + key);
        }
    }

    public void toggleMute() {
        muted = !muted;

        if (muted) {
            System.out.println("[AUDIO] Muted");
        } else {
            System.out.println("[AUDIO] Unmuted");
        }
    }

    public boolean isMuted() {
        return muted;
    }

    public void dispose() {
        for (Sound sound : sounds.values()) {
            sound.dispose();
        }

        sounds.clear();
    }
}
