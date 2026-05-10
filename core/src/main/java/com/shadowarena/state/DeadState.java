package com.shadowarena.state;

import com.shadowarena.entity.Player;

public class DeadState implements PlayerState {

    @Override
    public void enter(Player player) {
        // Dead state is final for the current game session.
    }

    @Override
    public void update(Player player, float delta) {
        // No movement or actions are allowed in the dead state.
    }

    @Override
    public String getName() {
        return "DEAD";
    }
}
