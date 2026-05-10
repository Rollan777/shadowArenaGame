package com.shadowarena.decorator;

public class DamageBoostDecorator extends PlayerStatsDecorator {

    public DamageBoostDecorator(PlayerStats wrappedStats) {
        super(wrappedStats);
    }

    @Override
    public int getDamage() {
        return wrappedStats.getDamage() + 15;
    }

    @Override
    public String getDescription() {
        return wrappedStats.getDescription() + " + Damage Boost";
    }
}
