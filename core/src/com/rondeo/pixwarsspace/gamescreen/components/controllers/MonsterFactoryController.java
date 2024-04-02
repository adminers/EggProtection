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
import com.rondeo.pixwarsspace.gamescreen.pojo.CenterPoint;
import com.rondeo.pixwarsspace.monster.DistributionMap;
import com.rondeo.pixwarsspace.monster.MonsterAttr;
import com.rondeo.pixwarsspace.utils.Constants;

import java.util.HashMap;
import java.util.List;
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

    /**
     * 本关是否创建完成
     */
    private boolean isLevelComplete;

    public MonsterFactoryController(World<Entity> world) {
        this.world = world;
    }

    public void deploy(Stage stage, int index, DistributionMap distributionMap) {

        String dName = distributionMap.getName();
        MonsterAttr monsterAttr = Constants.MONSTER_ATTR.get(dName);

        // Deploy first
        MonsterShip monsterShip = monsterShipPool.obtain();

        // 设置目标点
        monsterShip.setTargetName(distributionMap.getTargetName());
        monsterShip.init(monsterAttr);
        stage.addActor(monsterShip);

        TextureAtlas monsterTextureAtlas = new TextureAtlas(Gdx.files.internal(monsterAttr.getTextureRegion()));
        Array<AtlasRegion> commonRegion = monsterTextureAtlas.findRegions(monsterAttr.getFall());
        monsterShip.setRegions(commonRegion);

        String name = index +"_" + dName;
        monsterShip.setName(name);
//        monsterShip.setZIndex(3);
        this.visibleMonster.put(name, monsterShip);
        Constants.ACTIVE_ENEMIES.add( monsterShip );
    }

    public SequenceAction deployShips() {

        deploySequence = new SequenceAction();
        List<DistributionMap> distributionMaps = Constants.DISTRIBUTION_MAP.get(LevelManager.getCurrentLevel());
        for (int i = 0; i < distributionMaps.size(); i++) {
            DistributionMap distributionMap = distributionMaps.get(i);
            deploySequence.addAction(Actions.delay(1.0f));
            int finalI = i;
            deploySequence.addAction(new Action() {

                @Override
                public boolean act(float delta) {
                    deploy(getStage(), finalI, distributionMap);
                    return true;
                }
            });
        }

        // 添加一个 RunnableAction 作为最后一个动作，用于执行其他代码
        RunnableAction finishAction = Actions.run(() -> {
            // 在这里执行其他代码，表示前面的所有延迟动作都已完成
            System.out.println("工厂创建");
            isLevelComplete = true;
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
        } else {
//            System.out.println(1);
        }
    }

    public void forceFree(MonsterShip item) {

        monsterShipPool.free(item);
        visibleMonster.remove(item.getName());
        item.dispose();
        Constants.ACTIVE_ENEMIES.removeValue( item, false);
    }


    @Override
    public void dispose() {
        visibleMonster.clear();
    }

    public boolean isLevelComplete() {
        return isLevelComplete;
    }

    public MonsterFactoryController setLevelComplete(boolean levelComplete) {
        isLevelComplete = levelComplete;
        return this;
    }
}
