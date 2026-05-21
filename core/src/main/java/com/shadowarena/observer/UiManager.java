package com.shadowarena.observer;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.shadowarena.config.GameConfig;

public class UiManager implements GameObserver {

    private int hp;
    private int maxHp;
    private int score;
    private int wave;
    private int enemies;
    private String playerState;
    private float speed;
    private int damage;
    private float survivalTime;

    private int damageBoostLevel;
    private int speedBoostLevel;
    private int healthBoostLevel;
    private int maxUpgradeLevel;

    public UiManager() {
        this.hp = 100;
        this.maxHp = 100;
        this.score = 0;
        this.wave = 1;
        this.enemies = 0;
        this.playerState = "IDLE";
        this.speed = 250f;
        this.damage = 25;
        this.survivalTime = 0f;

        this.damageBoostLevel = 0;
        this.speedBoostLevel = 0;
        this.healthBoostLevel = 0;
        this.maxUpgradeLevel = 3;
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

            case "SPEED_CHANGED":
                this.speed = (float) data;
                break;

            case "DAMAGE_CHANGED":
                this.damage = (int) data;
                break;

            case "TIME_CHANGED":
                this.survivalTime = (float) data;
                break;

            case "DAMAGE_BOOST_CHANGED":
                this.damageBoostLevel = (int) data;
                break;

            case "SPEED_BOOST_CHANGED":
                this.speedBoostLevel = (int) data;
                break;

            case "HEALTH_BOOST_CHANGED":
                this.healthBoostLevel = (int) data;
                break;

            case "MAX_UPGRADE_CHANGED":
                this.maxUpgradeLevel = (int) data;
                break;

            default:
                break;
        }
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        float x = GameConfig.SIDEBAR_X + 22f;
        float y = GameConfig.SIDEBAR_Y + GameConfig.SIDEBAR_HEIGHT - 30f;

        font.draw(batch, "SHADOW ARENA", x + 34f, y);
        font.draw(batch, "NEON SURVIVAL", x + 38f, y - 24f);

        font.draw(batch, "PLAYER", x, y - 65f);
        font.draw(batch, "HP: " + hp + "/" + maxHp, x, y - 93f);
        font.draw(batch, "State: " + playerState, x, y - 116f);

        font.draw(batch, "GAME", x, y - 152f);
        font.draw(batch, "Score: " + score, x, y - 178f);
        font.draw(batch, "Wave: " + wave, x, y - 201f);
        font.draw(batch, "Enemies: " + enemies, x, y - 224f);
        font.draw(batch, "Time: " + String.format("%.1f", survivalTime), x, y - 247f);

        font.draw(batch, "STATS", x, y - 283f);
        font.draw(batch, "Damage: " + damage, x, y - 309f);
        font.draw(batch, "Speed: " + (int) speed, x, y - 332f);

        font.draw(batch, "UPGRADES", x, y - 368f);
        font.draw(batch, "Damage: " + damageBoostLevel + "/" + maxUpgradeLevel, x, y - 394f);
        font.draw(batch, "Speed: " + speedBoostLevel + "/" + maxUpgradeLevel, x, y - 417f);
        font.draw(batch, "Health: " + healthBoostLevel + "/" + maxUpgradeLevel, x, y - 440f);

        font.draw(batch, "CONTROLS", x, y - 476f);
        font.draw(batch, "WASD Move", x, y - 501f);
        font.draw(batch, "SPACE Attack", x, y - 524f);
        font.draw(batch, "1/2/3 Upgrade", x, y - 547f);
        font.draw(batch, "P Pause", x, y - 570f);
    }
}
