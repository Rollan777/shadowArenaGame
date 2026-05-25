package com.shadowarena.entity.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.shadowarena.strategy.MovementStrategy;

public class RangedEnemy extends Enemy {

    public RangedEnemy(float x, float y, MovementStrategy movementStrategy) {
        super(
            x,
            y,
            28f,
            28f,
            105f,
            55,
            10,
            25,
            movementStrategy
        );
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.triangle(
            getCenterX(), y + height + 3f,
            x - 3f, y - 3f,
            x + width + 3f, y - 3f
        );

        shapeRenderer.setColor(Color.ORANGE);
        shapeRenderer.triangle(
            getCenterX(), y + height,
            x, y,
            x + width, y
        );

        renderHealthBar(shapeRenderer);
    }
}
