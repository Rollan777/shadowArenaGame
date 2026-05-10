package com.shadowarena.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.shadowarena.config.GameConfig;

public class Player {

    private float x;
    private float y;
    private final float width;
    private final float height;
    private final float speed;

    private int hp;
    private final int maxHp;
    private final int damage;

    private final Rectangle bounds;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.width = GameConfig.PLAYER_WIDTH;
        this.height = GameConfig.PLAYER_HEIGHT;
        this.speed = GameConfig.PLAYER_SPEED;

        this.maxHp = GameConfig.PLAYER_MAX_HP;
        this.hp = maxHp;
        this.damage = GameConfig.PLAYER_DAMAGE;

        this.bounds = new Rectangle(x, y, width, height);
    }

    public void update(float delta) {
        handleInput(delta);
        clampToScreen();
        updateBounds();
    }

    private void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += speed * delta;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= speed * delta;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= speed * delta;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += speed * delta;
        }
    }

    private void clampToScreen() {
        if (x < 0) {
            x = 0;
        }

        if (x > Gdx.graphics.getWidth() - width) {
            x = Gdx.graphics.getWidth() - width;
        }

        if (y < 0) {
            y = 0;
        }

        if (y > Gdx.graphics.getHeight() - height) {
            y = Gdx.graphics.getHeight() - height;
        }
    }

    private void updateBounds() {
        bounds.setPosition(x, y);
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.CYAN);
        shapeRenderer.rect(x, y, width, height);
    }

    public void takeDamage(int amount) {
        hp -= amount;

        if (hp < 0) {
            hp = 0;
        }
    }

    public void heal(int amount) {
        hp += amount;

        if (hp > maxHp) {
            hp = maxHp;
        }
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getDamage() {
        return damage;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
