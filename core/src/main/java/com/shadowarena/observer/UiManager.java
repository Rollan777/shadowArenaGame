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
        font.draw(batch, "SHADOW ARENA", 345, 462);

        font.draw(batch, "HP: " + hp + "/" + maxHp, 35, 438);
        font.draw(batch, "Score: " + score, 35, 413);
        font.draw(batch, "Wave: " + wave, 35, 388);
        font.draw(batch, "Enemies: " + enemies, 35, 363);
        font.draw(batch, "State: " + playerState, 35, 338);
        font.draw(batch, "Damage: " + damage, 35, 313);
        font.draw(batch, "Speed: " + (int) speed, 35, 288);
        font.draw(batch, "Time: " + String.format("%.1f", survivalTime), 35, 263);

        font.draw(batch, "Upgrades:", 35, 228);
        font.draw(batch, upgrades, 35, 203);

        font.draw(batch, "CONTROLS", 600, 438);
        font.draw(batch, "WASD - Move", 600, 413);
        font.draw(batch, "SPACE - Attack", 600, 388);
        font.draw(batch, "J - Heal", 600, 363);
        font.draw(batch, "1 - Damage Boost", 600, 338);
        font.draw(batch, "2 - Speed Boost", 600, 313);
        font.draw(batch, "3 - Health Boost", 600, 288);
        font.draw(batch, "P - Pause", 600, 263);
        font.draw(batch, "ESC - Menu", 600, 238);
    }
}
