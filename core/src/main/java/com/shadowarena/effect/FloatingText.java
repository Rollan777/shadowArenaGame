package com.shadowarena.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FloatingText {

    private final String text;
    private float x;
    private float y;
    private float lifetime;
    private final float maxLifetime;
    private final Color color;

    public FloatingText(String text, float x, float y, Color color) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = new Color(color);
        this.maxLifetime = 1.0f;
        this.lifetime = maxLifetime;
    }

    public void update(float delta) {
        y += 35f * delta;
        lifetime -= delta;
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        float alpha = Math.max(0f, lifetime / maxLifetime);

        font.setColor(color.r, color.g, color.b, alpha);
        font.draw(batch, text, x, y);
        font.setColor(Color.WHITE);
    }

    public boolean isFinished() {
        return lifetime <= 0;
    }
}
