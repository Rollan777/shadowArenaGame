package com.shadowarena.manager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.shadowarena.effect.EffectManager;
import com.shadowarena.entity.Player;
import com.shadowarena.entity.enemy.Enemy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnemyManager {

    private final List<Enemy> enemies;

    public EnemyManager() {
        enemies = new ArrayList<>();
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void update(float delta, Player player) {
        for (Enemy enemy : enemies) {
            enemy.update(delta, player);
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        for (Enemy enemy : enemies) {
            enemy.render(shapeRenderer);
        }
    }

    public boolean checkCollisionWithPlayer(Player player) {
        for (Enemy enemy : enemies) {
            if (enemy.getBounds().overlaps(player.getBounds())) {
                player.takeDamage(enemy.getDamage());
                return true;
            }
        }

        return false;
    }

    public int handlePlayerAttack(Player player, EffectManager effectManager) {
        int earnedScore = 0;

        if (!player.isAttacking()) {
            return earnedScore;
        }

        Iterator<Enemy> iterator = enemies.iterator();

        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();

            if (player.getAttackArea().overlaps(enemy.getBounds())) {
                enemy.takeDamage(player.getDamage());

                effectManager.showDamage(
                    enemy.getCenterX(),
                    enemy.getCenterY() + 18f,
                    player.getDamage()
                );

                if (enemy.isDead()) {
                    earnedScore += enemy.getReward();

                    effectManager.showScore(
                        enemy.getCenterX(),
                        enemy.getCenterY() + 38f,
                        enemy.getReward()
                    );

                    effectManager.spawnExplosion(
                        enemy.getCenterX(),
                        enemy.getCenterY(),
                        Color.CORAL
                    );

                    iterator.remove();
                }
            }
        }

        return earnedScore;
    }

    public boolean isEmpty() {
        return enemies.isEmpty();
    }

    public int getEnemyCount() {
        return enemies.size();
    }

    public void clear() {
        enemies.clear();
    }
}
