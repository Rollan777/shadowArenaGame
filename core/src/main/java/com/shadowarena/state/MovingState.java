package com.shadowarena.state;

import com.shadowarena.entity.Player;

public class MovingState implements PlayerState {

    @Override
    public void enter(Player player) {
        // Moving state is used while the player moves with WASD.
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

        if (!player.isMoving()) {
            player.changeState(new IdleState());
        }
    }

    @Override
    public String getName() {
        return "MOVING";
    }
}
