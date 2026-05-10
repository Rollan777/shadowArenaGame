package com.shadowarena.decorator;

public class SpeedBoostDecorator extends PlayerStatsDecorator {

    public SpeedBoostDecorator(PlayerStats wrappedStats) {
        super(wrappedStats);
    }

    @Override
    public float getSpeed() {
        return wrappedStats.getSpeed() + 45f;
    }

    @Override
    public String getDescription() {
        return wrappedStats.getDescription() + " + Speed Boost";
    }
}
