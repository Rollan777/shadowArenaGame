package com.shadowarena.entity.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.shadowarena.entity.Player;
import com.shadowarena.strategy.MovementStrategy;

public class BossEnemy extends Enemy {

    private float pulseTimer;

    public BossEnemy(float x, float y, MovementStrategy movementStrategy) {
        super(
            x,
            y,
            64f,
            64f,
            55f,
            350,
            25,
            150,
            movementStrategy
        );

        this.pulseTimer = 0f;
    }

    @Override
    public void update(float delta, Player player) {
        super.update(delta, player);
        pulseTimer += delta;
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        float pulse = 4f + (float) Math.sin(pulseTimer * 5f) * 3f;

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(getCenterX(), getCenterY(), width / 2f + pulse);

        shapeRenderer.setColor(Color.MAROON);
        shapeRenderer.rect(x, y, width, height);

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rectLine(x, y, x + width, y, 3f);
        shapeRenderer.rectLine(x, y + height, x + width, y + height, 3f);
        shapeRenderer.rectLine(x, y, x, y + height, 3f);
        shapeRenderer.rectLine(x + width, y, x + width, y + height, 3f);

        renderHealthBar(shapeRenderer);
    }
}
