package com.shadowarena.strategy;

import com.badlogic.gdx.math.MathUtils;
import com.shadowarena.entity.Player;
import com.shadowarena.entity.enemy.Enemy;

public class RandomMoveStrategy implements MovementStrategy {

    private float timer = 0f;
    private float directionX = 1f;
    private float directionY = 0f;

    @Override
    public void move(Enemy enemy, Player player, float delta) {
        timer -= delta;

        if (timer <= 0) {
            directionX = MathUtils.random(-1f, 1f);
            directionY = MathUtils.random(-1f, 1f);
            timer = 0.8f;
        }

        enemy.moveBy(
            directionX * enemy.getSpeed() * delta,
            directionY * enemy.getSpeed() * delta
        );
    }
}
