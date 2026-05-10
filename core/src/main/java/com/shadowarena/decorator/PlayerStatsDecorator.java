package com.shadowarena.decorator;

public abstract class PlayerStatsDecorator implements PlayerStats {

    protected final PlayerStats wrappedStats;

    public PlayerStatsDecorator(PlayerStats wrappedStats) {
        this.wrappedStats = wrappedStats;
    }

    @Override
    public float getSpeed() {
        return wrappedStats.getSpeed();
    }

    @Override
    public int getDamage() {
        return wrappedStats.getDamage();
    }

    @Override
    public int getMaxHp() {
        return wrappedStats.getMaxHp();
    }

    @Override
    public String getDescription() {
        return wrappedStats.getDescription();
    }
}
