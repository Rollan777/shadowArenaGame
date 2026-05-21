package com.shadowarena.factory;

import com.shadowarena.entity.enemy.BossEnemy;
import com.shadowarena.entity.enemy.Enemy;
import com.shadowarena.entity.enemy.FastEnemy;
import com.shadowarena.entity.enemy.RangedEnemy;
import com.shadowarena.entity.enemy.TankEnemy;
import com.shadowarena.strategy.ChasePlayerStrategy;
import com.shadowarena.strategy.KeepDistanceStrategy;
import com.shadowarena.strategy.RandomMoveStrategy;

public class EnemyFactory {

    public Enemy createEnemy(EnemyType type, float x, float y) {
        switch (type) {
            case FAST:
                return new FastEnemy(x, y, new ChasePlayerStrategy());

            case TANK:
                return new TankEnemy(x, y, new ChasePlayerStrategy());

            case RANGED:
                return new RangedEnemy(x, y, new KeepDistanceStrategy());

            case BOSS:
                return new BossEnemy(x, y, new ChasePlayerStrategy());

            default:
                return new FastEnemy(x, y, new RandomMoveStrategy());
        }
    }
}
