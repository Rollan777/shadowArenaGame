package com.shadowarena.decorator;

import com.shadowarena.config.GameConfig;

public class BasePlayerStats implements PlayerStats {

    @Override
    public float getSpeed() {
        return GameConfig.PLAYER_SPEED;
    }

    @Override
    public int getDamage() {
        return GameConfig.PLAYER_DAMAGE;
    }

    @Override
    public int getMaxHp() {
        return GameConfig.PLAYER_MAX_HP;
    }

    @Override
    public String getDescription() {
        return "Base Stats";
    }
}
