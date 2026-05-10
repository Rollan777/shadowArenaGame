package com.shadowarena.strategy;

import com.badlogic.gdx.math.Vector2;
import com.shadowarena.entity.Player;
import com.shadowarena.entity.enemy.Enemy;

public class ChasePlayerStrategy implements MovementStrategy {

    @Override
    public void move(Enemy enemy, Player player, float delta) {
        Vector2 direction = new Vector2(
            player.getX() - enemy.getCenterX(),
            player.getY() - enemy.getCenterY()
        );

        if (direction.len() > 0) {
            direction.nor();

            enemy.moveBy(
                direction.x * enemy.getSpeed() * delta,
                direction.y * enemy.getSpeed() * delta
            );
        }
    }
}
