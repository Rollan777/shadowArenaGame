package com.shadowarena.strategy;

import com.badlogic.gdx.math.Vector2;
import com.shadowarena.entity.Player;
import com.shadowarena.entity.enemy.Enemy;

public class BossMovementStrategy implements MovementStrategy {

    private float timer;

    public BossMovementStrategy() {
        this.timer = 0f;
    }

    @Override
    public void move(Enemy enemy, Player player, float delta) {
        timer += delta;

        Vector2 direction = new Vector2(
            player.getX() - enemy.getCenterX(),
            player.getY() - enemy.getCenterY()
        );

        if (direction.len() == 0) {
            return;
        }

        direction.nor();

        float sideMovement = (float) Math.sin(timer * 2.5f) * 0.45f;

        float moveX = direction.x + (-direction.y * sideMovement);
        float moveY = direction.y + (direction.x * sideMovement);

        Vector2 movement = new Vector2(moveX, moveY);

        if (movement.len() > 0) {
            movement.nor();
        }

        enemy.moveBy(
            movement.x * enemy.getSpeed() * delta,
            movement.y * enemy.getSpeed() * delta
        );
    }
}
