package com.rondeo.pixwarsspace.gamescreen.components;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * @Title: Enemy
 * @Description: Enemy
 * @Company: www.qiaweidata.com
 * @author: shenshilong
 * @date: 2024-03-20
 * @version: V1.0
 */
public class Enemy extends Actor implements Entity, Disposable, Poolable {

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
}
