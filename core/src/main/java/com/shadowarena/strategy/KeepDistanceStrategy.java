package com.shadowarena.strategy;

import com.badlogic.gdx.math.Vector2;
import com.shadowarena.entity.Player;
import com.shadowarena.entity.enemy.Enemy;

public class KeepDistanceStrategy implements MovementStrategy {

    private static final float MIN_DISTANCE = 150f;

    @Override
    public void move(Enemy enemy, Player player, float delta) {
        Vector2 direction = new Vector2(
            player.getX() - enemy.getCenterX(),
            player.getY() - enemy.getCenterY()
        );

        float distance = direction.len();

        if (distance == 0) {
            return;
        }

        direction.nor();

        if (distance > MIN_DISTANCE) {
            enemy.moveBy(
                direction.x * enemy.getSpeed() * delta,
                direction.y * enemy.getSpeed() * delta
            );
        } else if (distance < MIN_DISTANCE - 40f) {
            enemy.moveBy(
                -direction.x * enemy.getSpeed() * delta,
                -direction.y * enemy.getSpeed() * delta
            );
        }
    }
}
