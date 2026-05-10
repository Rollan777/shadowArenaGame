package com.shadowarena.state;

import com.shadowarena.entity.Player;

public class AttackingState implements PlayerState {

    @Override
    public void enter(Player player) {
        // Attacking state is activated when the player presses SPACE.
    }

    @Override
    public void update(Player player, float delta) {
        if (player.isDead()) {
            player.changeState(new DeadState());
            return;
        }

        if (!player.isAttacking()) {
            if (player.isMoving()) {
                player.changeState(new MovingState());
            } else {
                player.changeState(new IdleState());
            }
        }
    }

    @Override
    public String getName() {
        return "ATTACKING";
    }
}
