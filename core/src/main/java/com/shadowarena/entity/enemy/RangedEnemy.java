package com.shadowarena.entity.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.shadowarena.strategy.MovementStrategy;

public class RangedEnemy extends Enemy {

    public RangedEnemy(float x, float y, MovementStrategy movementStrategy) {
        super(
            x,
            y,
            26f,
            26f,
            95f,
            55,
            10,
            25,
            movementStrategy
        );
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.ORANGE);
        shapeRenderer.circle(getCenterX(), getCenterY(), width / 2f);
    }
}
