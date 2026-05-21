package com.shadowarena.manager;

import com.badlogic.gdx.math.MathUtils;
import com.shadowarena.config.GameConfig;
import com.shadowarena.entity.enemy.Enemy;
import com.shadowarena.factory.EnemyFactory;
import com.shadowarena.factory.EnemyType;

public class WaveManager {

    private int currentWave;
    private final EnemyFactory enemyFactory;

    public WaveManager() {
        this.currentWave = 0;
        this.enemyFactory = new EnemyFactory();
    }

    public void startNextWave(EnemyManager enemyManager) {
        currentWave++;

        int enemyCount = 2 + currentWave;

        for (int i = 0; i < enemyCount; i++) {
            EnemyType type = chooseEnemyType(i);
            float x = getSpawnX();
            float y = getSpawnY();

            Enemy enemy = enemyFactory.createEnemy(type, x, y);
            enemyManager.addEnemy(enemy);
        }
    }

    private EnemyType chooseEnemyType(int index) {
        if (currentWave >= 3 && index % 4 == 0) {
            return EnemyType.TANK;
        }

        if (currentWave >= 2 && index % 3 == 0) {
            return EnemyType.RANGED;
        }

        return EnemyType.FAST;
    }

    private float getSpawnX() {
        boolean leftSide = MathUtils.randomBoolean();

        if (leftSide) {
            return MathUtils.random(GameConfig.ARENA_X + 20f, GameConfig.ARENA_X + 90f);
        }

        return MathUtils.random(
            GameConfig.ARENA_X + GameConfig.ARENA_WIDTH - 110f,
            GameConfig.ARENA_X + GameConfig.ARENA_WIDTH - 40f
        );
    }

    private float getSpawnY() {
        return MathUtils.random(
            GameConfig.ARENA_Y + 50f,
            GameConfig.ARENA_Y + GameConfig.ARENA_HEIGHT - 80f
        );
    }

    public int getCurrentWave() {
        return currentWave;
    }
}
