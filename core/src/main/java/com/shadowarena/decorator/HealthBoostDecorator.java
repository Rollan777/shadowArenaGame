package com.shadowarena.decorator;

public class HealthBoostDecorator extends PlayerStatsDecorator {

    public HealthBoostDecorator(PlayerStats wrappedStats) {
        super(wrappedStats);
    }

    @Override
    public int getMaxHp() {
        return wrappedStats.getMaxHp() + 30;
    }

    @Override
    public String getDescription() {
        return wrappedStats.getDescription() + " + Health Boost";
    }
}
