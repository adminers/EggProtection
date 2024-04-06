package com.rondeo.pixwarsspace.gamescreen.components;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;

public class Scenery extends Actor implements Entity, Disposable, Pool.Poolable {

    private Player parentPlayer;

    /**
     * Releases all resources of this object.
     */
    @Override
    public void dispose() {

    }

    /**
     * Resets the object for reuse. Object references should be nulled and fields may be set to default values.
     */
    @Override
    public void reset() {

    }

    public Player getParentPlayer() {
        return parentPlayer;
    }

    public Scenery setParentPlayer(Player parentPlayer) {
        this.parentPlayer = parentPlayer;
        return this;
    }
}
