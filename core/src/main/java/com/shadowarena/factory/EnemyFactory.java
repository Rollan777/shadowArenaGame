package com.shadowarena.factory;

import com.shadowarena.entity.enemy.BossEnemy;
import com.shadowarena.entity.enemy.Enemy;
import com.shadowarena.entity.enemy.FastEnemy;
import com.shadowarena.entity.enemy.RangedEnemy;
import com.shadowarena.entity.enemy.TankEnemy;
import com.shadowarena.strategy.BossMovementStrategy;
import com.shadowarena.strategy.ChasePlayerStrategy;
import com.shadowarena.strategy.RandomMoveStrategy;
import com.shadowarena.strategy.TacticalRangedStrategy;
import com.shadowarena.strategy.ZigZagChaseStrategy;

public class EnemyFactory {

    public Enemy createEnemy(EnemyType type, float x, float y) {
        switch (type) {
            case FAST:
                return new FastEnemy(x, y, new ZigZagChaseStrategy());

            case TANK:
                return new TankEnemy(x, y, new ChasePlayerStrategy());

            case RANGED:
                return new RangedEnemy(x, y, new TacticalRangedStrategy());

            case BOSS:
                return new BossEnemy(x, y, new BossMovementStrategy());

            default:
                return new FastEnemy(x, y, new RandomMoveStrategy());
        }
    }
}
