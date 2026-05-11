package com.shadowarena.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.shadowarena.config.GameConfig;
import com.shadowarena.decorator.BasePlayerStats;
import com.shadowarena.decorator.PlayerStats;
import com.shadowarena.state.IdleState;
import com.shadowarena.state.PlayerState;
import com.shadowarena.ui.UiTheme;

public class Player {

    private float x;
    private float y;
    private final float width;
    private final float height;

    private int hp;

    private final Rectangle bounds;

    private boolean moving;
    private boolean attacking;

    private float attackTimer;
    private final float attackCooldown;
    private final float attackVisibleTime;

    private PlayerState currentState;
    private PlayerStats stats;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;

        this.width = GameConfig.PLAYER_WIDTH;
        this.height = GameConfig.PLAYER_HEIGHT;

        this.stats = new BasePlayerStats();
        this.hp = stats.getMaxHp();

        this.bounds = new Rectangle(x, y, width, height);

        this.moving = false;
        this.attacking = false;

        this.attackTimer = 0f;
        this.attackCooldown = 0.35f;
        this.attackVisibleTime = 0.12f;

        this.currentState = new IdleState();
        this.currentState.enter(this);
    }

    public void update(float delta) {
        moving = false;

        if (!isDead()) {
            handleInput(delta);
            updateAttack(delta);
            clampToScreen();
        }

        updateBounds();
        currentState.update(this, delta);
    }

    private void handleInput(float delta) {
        float currentSpeed = stats.getSpeed();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += currentSpeed * delta;
            moving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= currentSpeed * delta;
            moving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= currentSpeed * delta;
            moving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += currentSpeed * delta;
            moving = true;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && attackTimer <= 0) {
            attacking = true;
            attackTimer = attackCooldown;
        }
    }

    private void updateAttack(float delta) {
        if (attackTimer > 0) {
            attackTimer -= delta;
        }

        if (attackTimer <= attackCooldown - attackVisibleTime) {
            attacking = false;
        }
    }

    private void clampToScreen() {
        float padding = 22f;

        if (x < padding) {
            x = padding;
        }

        if (x > Gdx.graphics.getWidth() - width - padding) {
            x = Gdx.graphics.getWidth() - width - padding;
        }

        if (y < padding) {
            y = padding;
        }

        if (y > Gdx.graphics.getHeight() - height - padding) {
            y = Gdx.graphics.getHeight() - height - padding;
        }
    }

    private void updateBounds() {
        bounds.setPosition(x, y);
    }

    public void render(ShapeRenderer shapeRenderer) {
        if (isDead()) {
            shapeRenderer.setColor(Color.DARK_GRAY);
        } else {
            shapeRenderer.setColor(UiTheme.PLAYER);
        }

        shapeRenderer.rect(x, y, width, height);

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rectLine(x, y, x + width, y, 2f);
        shapeRenderer.rectLine(x, y + height, x + width, y + height, 2f);
        shapeRenderer.rectLine(x, y, x, y + height, 2f);
        shapeRenderer.rectLine(x + width, y, x + width, y + height, 2f);

        if (attacking) {
            Rectangle attackArea = getAttackArea();
            shapeRenderer.setColor(UiTheme.PLAYER_ATTACK);
            shapeRenderer.rect(attackArea.x, attackArea.y, attackArea.width, attackArea.height);
        }
    }

    public Rectangle getAttackArea() {
        return new Rectangle(
            x - 24,
            y - 24,
            width + 48,
            height + 48
        );
    }

    public void applyStats(PlayerStats newStats) {
        int oldMaxHp = stats.getMaxHp();

        this.stats = newStats;

        int newMaxHp = stats.getMaxHp();

        if (newMaxHp > oldMaxHp) {
            hp += newMaxHp - oldMaxHp;
        }

        if (hp > newMaxHp) {
            hp = newMaxHp;
        }
    }

    public PlayerStats getStats() {
        return stats;
    }

    public String getStatsDescription() {
        return stats.getDescription();
    }

    public void changeState(PlayerState newState) {
        if (currentState.getClass() == newState.getClass()) {
            return;
        }

        currentState = newState;
        currentState.enter(this);
    }

    public String getStateName() {
        return currentState.getName();
    }

    public boolean isMoving() {
        return moving;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void takeDamage(int amount) {
        if (isDead()) {
            return;
        }

        hp -= amount;

        if (hp < 0) {
            hp = 0;
        }
    }

    public void heal(int amount) {
        if (isDead()) {
            return;
        }

        hp += amount;

        if (hp > stats.getMaxHp()) {
            hp = stats.getMaxHp();
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
        return stats.getMaxHp();
    }

    public int getDamage() {
        return stats.getDamage();
    }

    public float getSpeed() {
        return stats.getSpeed();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
