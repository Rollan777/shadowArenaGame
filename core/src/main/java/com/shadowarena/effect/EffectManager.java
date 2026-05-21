package com.shadowarena.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EffectManager {

    private final List<FloatingText> floatingTexts;
    private final List<Particle> particles;

    public EffectManager() {
        this.floatingTexts = new ArrayList<>();
        this.particles = new ArrayList<>();
    }

    public void update(float delta) {
        Iterator<FloatingText> textIterator = floatingTexts.iterator();

        while (textIterator.hasNext()) {
            FloatingText text = textIterator.next();
            text.update(delta);

            if (text.isFinished()) {
                textIterator.remove();
            }
        }

        Iterator<Particle> particleIterator = particles.iterator();

        while (particleIterator.hasNext()) {
            Particle particle = particleIterator.next();
            particle.update(delta);

            if (particle.isFinished()) {
                particleIterator.remove();
            }
        }
    }

    public void renderParticles(ShapeRenderer shapeRenderer) {
        for (Particle particle : particles) {
            particle.render(shapeRenderer);
        }
    }

    public void renderTexts(SpriteBatch batch, BitmapFont font) {
        for (FloatingText text : floatingTexts) {
            text.render(batch, font);
        }
    }

    public void showDamage(float x, float y, int damage) {
        floatingTexts.add(new FloatingText("-" + damage, x, y, Color.GOLD));
    }

    public void showScore(float x, float y, int score) {
        if (score <= 0) {
            return;
        }

        floatingTexts.add(new FloatingText("+" + score, x, y, Color.GREEN));
    }

    public void showWave(float x, float y, int wave) {
        floatingTexts.add(new FloatingText("WAVE " + wave, x, y, Color.CYAN));
    }

    public void spawnExplosion(float x, float y, Color color) {
        for (int i = 0; i < 18; i++) {
            float angle = MathUtils.random(0f, 360f);
            float speed = MathUtils.random(60f, 180f);

            float velocityX = MathUtils.cosDeg(angle) * speed;
            float velocityY = MathUtils.sinDeg(angle) * speed;

            particles.add(
                new Particle(
                    x,
                    y,
                    velocityX,
                    velocityY,
                    MathUtils.random(3f, 7f),
                    MathUtils.random(0.35f, 0.75f),
                    color
                )
            );
        }
    }

    public void clear() {
        floatingTexts.clear();
        particles.clear();
    }
}
