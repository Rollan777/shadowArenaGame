package com.shadowarena.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Particle {

    private float x;
    private float y;
    private final float velocityX;
    private final float velocityY;
    private float size;
    private float lifetime;
    private final float maxLifetime;
    private final Color color;

    public Particle(
        float x,
        float y,
        float velocityX,
        float velocityY,
        float size,
        float lifetime,
        Color color
    ) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.size = size;
        this.lifetime = lifetime;
        this.maxLifetime = lifetime;
        this.color = new Color(color);
    }

    public void update(float delta) {
        x += velocityX * delta;
        y += velocityY * delta;
        size -= 12f * delta;
        lifetime -= delta;

        if (size < 0f) {
            size = 0f;
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        float alpha = Math.max(0f, lifetime / maxLifetime);
        shapeRenderer.setColor(color.r, color.g, color.b, alpha);
        shapeRenderer.circle(x, y, size);
    }

    public boolean isFinished() {
        return lifetime <= 0 || size <= 0;
    }
}
