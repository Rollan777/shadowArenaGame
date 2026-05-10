package com.shadowarena.state;

import com.shadowarena.entity.Player;

public interface PlayerState {

    void enter(Player player);

    void update(Player player, float delta);

    String getName();
}
