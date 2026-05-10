package com.shadowarena.observer;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UiManager implements GameObserver {

    private int hp;
    private int maxHp;
    private int score;
    private int wave;
    private int enemies;
    private String playerState;
    private float survivalTime;

    public UiManager() {
        this.hp = 100;
        this.maxHp = 100;
        this.score = 0;
        this.wave = 1;
        this.enemies = 0;
        this.playerState = "IDLE";
        this.survivalTime = 0f;
    }

    @Override
    public void onNotify(String eventType, Object data) {
        switch (eventType) {
            case "HP_CHANGED":
                this.hp = (int) data;
                break;

            case "MAX_HP_CHANGED":
                this.maxHp = (int) data;
                break;

            case "SCORE_CHANGED":
                this.score = (int) data;
                break;

            case "WAVE_CHANGED":
                this.wave = (int) data;
                break;

            case "ENEMIES_CHANGED":
                this.enemies = (int) data;
                break;

            case "STATE_CHANGED":
                this.playerState = String.valueOf(data);
                break;

            case "TIME_CHANGED":
                this.survivalTime = (float) data;
                break;

            default:
                break;
        }
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        font.draw(batch, "SHADOW ARENA", 340, 460);

        font.draw(batch, "HP: " + hp + "/" + maxHp, 30, 440);
        font.draw(batch, "Score: " + score, 30, 415);
        font.draw(batch, "Wave: " + wave, 30, 390);
        font.draw(batch, "Enemies: " + enemies, 30, 365);
        font.draw(batch, "State: " + playerState, 30, 340);
        font.draw(batch, "Time: " + String.format("%.1f", survivalTime), 30, 315);

        font.draw(batch, "Controls", 610, 440);
        font.draw(batch, "WASD - Move", 610, 415);
        font.draw(batch, "SPACE - Attack", 610, 390);
        font.draw(batch, "J - Heal", 610, 365);
        font.draw(batch, "G - Game Over", 610, 340);
        font.draw(batch, "ESC - Menu", 610, 315);
    }
}
