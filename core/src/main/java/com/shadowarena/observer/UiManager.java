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
    private String upgrades;
    private float speed;
    private int damage;
    private float survivalTime;

    public UiManager() {
        this.hp = 100;
        this.maxHp = 100;
        this.score = 0;
        this.wave = 1;
        this.enemies = 0;
        this.playerState = "IDLE";
        this.upgrades = "Base Stats";
        this.speed = 250f;
        this.damage = 25;
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

            case "UPGRADES_CHANGED":
                this.upgrades = String.valueOf(data);
                break;

            case "SPEED_CHANGED":
                this.speed = (float) data;
                break;

            case "DAMAGE_CHANGED":
                this.damage = (int) data;
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
        font.draw(batch, "Damage: " + damage, 30, 315);
        font.draw(batch, "Speed: " + (int) speed, 30, 290);
        font.draw(batch, "Time: " + String.format("%.1f", survivalTime), 30, 265);

        font.draw(batch, "Upgrades:", 30, 230);
        font.draw(batch, upgrades, 30, 205);

        font.draw(batch, "Controls", 590, 440);
        font.draw(batch, "WASD - Move", 590, 415);
        font.draw(batch, "SPACE - Attack", 590, 390);
        font.draw(batch, "J - Heal", 590, 365);
        font.draw(batch, "1 - Damage Boost", 590, 340);
        font.draw(batch, "2 - Speed Boost", 590, 315);
        font.draw(batch, "3 - Health Boost", 590, 290);
        font.draw(batch, "G - Game Over", 590, 265);
        font.draw(batch, "ESC - Menu", 590, 240);
    }
}
