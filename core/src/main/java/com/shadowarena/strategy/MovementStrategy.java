package com.shadowarena.strategy;

import com.shadowarena.entity.Player;
import com.shadowarena.entity.enemy.Enemy;

public interface MovementStrategy {

    void move(Enemy enemy, Player player, float delta);
}
