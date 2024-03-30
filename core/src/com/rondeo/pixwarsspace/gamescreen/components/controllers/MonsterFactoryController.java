package com.rondeo.pixwarsspace.gamescreen.components.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.dongbat.jbump.World;
import com.rondeo.pixwarsspace.gamescreen.cells.po.Axis;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.gamescreen.components.LevelManager;
import com.rondeo.pixwarsspace.gamescreen.components.entity.MonsterShip;
import com.rondeo.pixwarsspace.monster.MonsterAttr;
import com.rondeo.pixwarsspace.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class MonsterFactoryController extends Actor implements Entity, Disposable {

    World<Entity> world;

    private final Pool<MonsterShip> monsterShipPool = new Pool<MonsterShip>() {
        @Override
        protected MonsterShip newObject() {
            return new MonsterShip(world);

        }
    };
    SequenceAction deploySequence = new SequenceAction();

    /**
     * 地图可见的怪物
     */
    Map<String, MonsterShip> visibleMonster = new HashMap<>();

    long time = System.currentTimeMillis();

    /**
     * 是否创建
     */
    private boolean isGenerate;

    public MonsterFactoryController(World<Entity> world) {
        this.world = world;
    }

    public void deploy(Stage stage, int c) {

        MonsterAttr monsterAttr = Constants.MONSTER_ATTR.get("slug");
        Axis level = monsterAttr.getTargetPoint();

        // Deploy first
        MonsterShip monsterShip = monsterShipPool.obtain();
        stage.addActor(monsterShip);

        TextureAtlas monsterTextureAtlas = new TextureAtlas(Gdx.files.internal(monsterAttr.getTextureRegion()));
        Array<AtlasRegion> commonRegion = monsterTextureAtlas.findRegions(monsterAttr.getFall());
        monsterShip.setRegions(commonRegion);
        monsterShip.init(monsterAttr);
        String name = String.valueOf(c);
        monsterShip.setName(name);
//        monsterShip.setZIndex(3);
        this.visibleMonster.put(name, monsterShip);
    }

    public SequenceAction deployShips() {

        deploySequence = new SequenceAction();
        deploySequence.addAction(Actions.delay(0.0f));
        deploySequence.addAction(new Action() {

            @Override
            public boolean act(float delta) {
                deploy(getStage(), 1);
                return true;
            }
        });

        // 添加一个 RunnableAction 作为最后一个动作，用于执行其他代码
        RunnableAction finishAction = Actions.run(() -> {
            // 在这里执行其他代码，表示前面的所有延迟动作都已完成
            System.out.println("工厂创建");
        });
        deploySequence.addAction(finishAction);
        return deploySequence;
    }

    @Override
    public void act(float delta) {

        super.act(delta);
        boolean endLevel = LevelManager.isEndLevel();
        if (visibleMonster.isEmpty()) {
            if (System.currentTimeMillis() > time + 3000) {
                addAction(deployShips());
                time = System.currentTimeMillis();
            }
        }
    }

    public void forceFree(MonsterShip item) {
        visibleMonster.remove(item.getName());
        monsterShipPool.free(item);
        item.dispose();
    }


    @Override
    public void dispose() {
        visibleMonster.clear();
    }

}
