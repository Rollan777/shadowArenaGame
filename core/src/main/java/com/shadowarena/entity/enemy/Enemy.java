package com.shadowarena.entity.enemy;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.shadowarena.entity.Player;
import com.shadowarena.strategy.MovementStrategy;

public abstract class Enemy {

    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float speed;

    protected int hp;
    protected int damage;
    protected int reward;

    protected Rectangle bounds;
    protected MovementStrategy movementStrategy;

    public Enemy(
        float x,
        float y,
        float width,
        float height,
        float speed,
        int hp,
        int damage,
        int reward,
        MovementStrategy movementStrategy
    ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.hp = hp;
        this.damage = damage;
        this.reward = reward;
        this.movementStrategy = movementStrategy;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void update(float delta, Player player) {
        movementStrategy.move(this, player, delta);
        updateBounds();
    }

    public abstract void render(ShapeRenderer shapeRenderer);

    public void moveBy(float dx, float dy) {
        x += dx;
        y += dy;
    }

    protected void updateBounds() {
        bounds.setPosition(x, y);
    }

    public void takeDamage(int amount) {
        hp -= amount;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getCenterX() {
        return x + width / 2f;
    }

    public float getCenterY() {
        return y + height / 2f;
    }

    public float getSpeed() {
        return speed;
    }

    public int getDamage() {
        return damage;
    }

    public int getReward() {
        return reward;
    }
}
