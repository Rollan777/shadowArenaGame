package com.shadowarena.entity.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.shadowarena.strategy.MovementStrategy;

public class TankEnemy extends Enemy {

    public TankEnemy(float x, float y, MovementStrategy movementStrategy) {
        super(
            x,
            y,
            36f,
            36f,
            75f,
            100,
            15,
            35,
            movementStrategy
        );
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.PURPLE);
        shapeRenderer.rect(x, y, width, height);
    }
}
