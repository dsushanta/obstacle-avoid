package com.bravo.johny.entity;

import com.bravo.johny.config.GameConfig;

public class Player extends GameObjectBase{

    public Player() {
        super(GameConfig.PLAYER_BOUND_RADIUS);
        setSize(GameConfig.PLAYER_SIZE, GameConfig.PLAYER_SIZE);
    }
}
