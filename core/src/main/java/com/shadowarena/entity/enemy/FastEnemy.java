package com.shadowarena.entity.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.shadowarena.strategy.MovementStrategy;

public class FastEnemy extends Enemy {

    public FastEnemy(float x, float y, MovementStrategy movementStrategy) {
        super(
            x,
            y,
            24f,
            24f,
            145f,
            35,
            8,
            15,
            movementStrategy
        );
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(x, y, width, height);
    }
}
