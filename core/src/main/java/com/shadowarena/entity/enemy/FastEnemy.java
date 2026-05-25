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
            155f,
            35,
            8,
            15,
            movementStrategy
        );
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.SCARLET);
        shapeRenderer.circle(getCenterX(), getCenterY(), width / 2f);

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(getCenterX(), getCenterY(), width / 2f + 2f);

        shapeRenderer.setColor(Color.SCARLET);
        shapeRenderer.circle(getCenterX(), getCenterY(), width / 2f);

        renderHealthBar(shapeRenderer);
    }
}
