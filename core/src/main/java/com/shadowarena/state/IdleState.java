package com.shadowarena.state;

import com.shadowarena.entity.Player;

public class IdleState implements PlayerState {

    @Override
    public void enter(Player player) {
        // Idle state is used when the player is alive but not moving or attacking.
    }

    @Override
    public void update(Player player, float delta) {
        if (player.isDead()) {
            player.changeState(new DeadState());
            return;
        }

        if (player.isAttacking()) {
            player.changeState(new AttackingState());
            return;
        }

        if (player.isMoving()) {
            player.changeState(new MovingState());
        }
    }

    @Override
    public String getName() {
        return "IDLE";
    }
}
