package com.shadowarena.strategy;

import com.badlogic.gdx.math.Vector2;
import com.shadowarena.entity.Player;
import com.shadowarena.entity.enemy.Enemy;

public class TacticalRangedStrategy implements MovementStrategy {

    private static final float MIN_DISTANCE = 170f;
    private static final float MAX_DISTANCE = 240f;

    private float orbitTimer;

    public TacticalRangedStrategy() {
        this.orbitTimer = 0f;
    }

    @Override
    public void move(Enemy enemy, Player player, float delta) {
        orbitTimer += delta;

        Vector2 directionToPlayer = new Vector2(
            player.getX() - enemy.getCenterX(),
            player.getY() - enemy.getCenterY()
        );

        float distance = directionToPlayer.len();

        if (distance == 0) {
            return;
        }

        directionToPlayer.nor();

        float moveX = 0f;
        float moveY = 0f;

        if (distance < MIN_DISTANCE) {
            moveX -= directionToPlayer.x;
            moveY -= directionToPlayer.y;
        } else if (distance > MAX_DISTANCE) {
            moveX += directionToPlayer.x;
            moveY += directionToPlayer.y;
        }

        float orbitStrength = 0.65f;
        moveX += -directionToPlayer.y * orbitStrength;
        moveY += directionToPlayer.x * orbitStrength;

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
