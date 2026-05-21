package com.shadowarena.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.shadowarena.config.GameConfig;
import com.shadowarena.decorator.BasePlayerStats;
import com.shadowarena.decorator.DamageBoostDecorator;
import com.shadowarena.decorator.HealthBoostDecorator;
import com.shadowarena.decorator.PlayerStats;
import com.shadowarena.decorator.SpeedBoostDecorator;
import com.shadowarena.state.IdleState;
import com.shadowarena.state.PlayerState;
import com.shadowarena.ui.UiTheme;

public class Player {

    private static final int MAX_UPGRADE_LEVEL = 3;

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

    private int damageBoostLevel;
    private int speedBoostLevel;
    private int healthBoostLevel;

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

        this.damageBoostLevel = 0;
        this.speedBoostLevel = 0;
        this.healthBoostLevel = 0;

        this.currentState = new IdleState();
        this.currentState.enter(this);
    }

    public void update(float delta) {
        moving = false;

        if (!isDead()) {
            handleInput(delta);
            updateAttack(delta);
            clampToArena();
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

    private void clampToArena() {
        float minX = GameConfig.ARENA_X + 8f;
        float maxX = GameConfig.ARENA_X + GameConfig.ARENA_WIDTH - width - 8f;
        float minY = GameConfig.ARENA_Y + 8f;
        float maxY = GameConfig.ARENA_Y + GameConfig.ARENA_HEIGHT - height - 8f;

        if (x < minX) x = minX;
        if (x > maxX) x = maxX;
        if (y < minY) y = minY;
        if (y > maxY) y = maxY;
    }

    private void updateBounds() {
        bounds.setPosition(x, y);
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(isDead() ? Color.DARK_GRAY : UiTheme.PLAYER);
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
        return new Rectangle(x - 24, y - 24, width + 48, height + 48);
    }

    public boolean applyDamageBoost() {
        if (damageBoostLevel >= MAX_UPGRADE_LEVEL) {
            return false;
        }

        stats = new DamageBoostDecorator(stats);
        damageBoostLevel++;
        return true;
    }

    public boolean applySpeedBoost() {
        if (speedBoostLevel >= MAX_UPGRADE_LEVEL) {
            return false;
        }

        stats = new SpeedBoostDecorator(stats);
        speedBoostLevel++;
        return true;
    }

    public boolean applyHealthBoost() {
        if (healthBoostLevel >= MAX_UPGRADE_LEVEL) {
            return false;
        }

        int oldMaxHp = stats.getMaxHp();

        stats = new HealthBoostDecorator(stats);
        healthBoostLevel++;

        int newMaxHp = stats.getMaxHp();
        hp += newMaxHp - oldMaxHp;

        if (hp > newMaxHp) {
            hp = newMaxHp;
        }

        return true;
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
        if (isDead()) return;

        hp -= amount;

        if (hp < 0) {
            hp = 0;
        }
    }

    public void heal(int amount) {
        if (isDead()) return;

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

    public int getDamageBoostLevel() {
        return damageBoostLevel;
    }

    public int getSpeedBoostLevel() {
        return speedBoostLevel;
    }

    public int getHealthBoostLevel() {
        return healthBoostLevel;
    }

    public int getMaxUpgradeLevel() {
        return MAX_UPGRADE_LEVEL;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
