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
import com.rondeo.pixwarsspace.gamescreen.components.Controllers;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.gamescreen.components.HudManager;
import com.rondeo.pixwarsspace.gamescreen.components.LevelManager;
import com.rondeo.pixwarsspace.gamescreen.components.entity.MonsterShip;
import com.rondeo.pixwarsspace.gamescreen.components.play.YunShip;
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
            return new MonsterShip(world, hudManager);

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

    /**
     * 是否被攻击(首次加载血条,直接用怪物数量不行,因为刚加载是延迟加载,一进来,血条=0.过几秒才变满)
     */
    private boolean isAttacked;

    private int attackEnemyNum = 0;

    private HudManager hudManager;

    private Map<String, TextureAtlas> textureAtlasMap = new HashMap<>();

    public MonsterFactoryController(World<Entity> world) {
        this.world = world;
    }

    public MonsterFactoryController setHudManager(HudManager hudManager) {
        this.hudManager = hudManager;
        return this;
    }

    public void deploy(Stage stage, int index, DistributionMap distributionMap) {

        String dName = distributionMap.getName();
        MonsterAttr monsterAttr = Constants.MONSTER_ATTR.get(dName);

        // Deploy first
        MonsterShip monsterShip = monsterShipPool.obtain();

        // 设置目标点
        monsterShip.setTargetName(distributionMap.getTargetName());
        monsterShip.init(monsterAttr);
        monsterShip.setDebug(true);
        stage.addActor(monsterShip);
        TextureAtlas monsterTextureAtlas;
        if (textureAtlasMap.containsKey(monsterAttr.getTextureRegion())) {
            monsterTextureAtlas = textureAtlasMap.get(monsterAttr.getTextureRegion());
        } else {
            monsterTextureAtlas = new TextureAtlas(Gdx.files.internal(monsterAttr.getTextureRegion()));
            textureAtlasMap.put(monsterAttr.getTextureRegion(), monsterTextureAtlas);
        }
        Array<AtlasRegion> commonRegion = monsterTextureAtlas.findRegions(monsterAttr.getFall());
        monsterShip.setRegions(commonRegion);

        String name = index +"_" + dName;
        monsterShip.setName(name);
//        monsterShip.setZIndex(3);
        this.visibleMonster.put(name, monsterShip);
        Constants.ACTIVE_ENEMIES.add( monsterShip );
        Integer monsterCount = Constants.LEVEL_MONSTER_COUNT.get(LevelManager.getCurrentIndexLevel());
        Constants.LEVEL_MONSTER_COUNT.set(LevelManager.getCurrentIndexLevel(), monsterCount + 1);
    }

    public SequenceAction deployShips() {

        deploySequence = new SequenceAction();
//        deploySequence.addAction(Actions.delay(0.0f));
        deploySequence.addAction(new Action() {

            @Override
            public boolean act(float delta) {
                List<DistributionMap> distributions = Constants.DISTRIBUTION_MAP.get(LevelManager.getCurrentIndexLevel());
                Integer totalCount = Constants.LEVEL_MONSTER_COUNT.get(LevelManager.getCurrentIndexLevel());
                int index = totalCount % distributions.size();
                DistributionMap distributionMap = distributions.get(index);
                deploy(getStage(), index, distributionMap);
                return true;
            }
        });

        // 添加一个 RunnableAction 作为最后一个动作，用于执行其他代码
        RunnableAction finishAction = Actions.run(() -> {
            // 在这里执行其他代码，表示前面的所有延迟动作都已完成
            System.out.println("工厂创建");
        });
//        deploySequence.addAction(finishAction);
        return deploySequence;
    }

    @Override
    public void act(float delta) {

        super.act(delta);
        if (Controllers.getInstance().junction) {
            return;
        }
        boolean endLevel = LevelManager.isEndLevel();
        Integer monsterCount = Constants.LEVEL_MONSTER_COUNT.get(LevelManager.getCurrentIndexLevel());

        // 画面中敌人少于2个,则部署
        if (Constants.ACTIVE_ENEMIES.size < 2) {

            // 如果到了结束,则停止
            if (monsterCount >= Constants.LEVEL_MONSTER_MAX.get(LevelManager.getCurrentIndexLevel())) {
                return;
            }
            if (System.currentTimeMillis() > time + 1000) {
                YunShip.isCreatePlayer = true;
                addAction(deployShips());
                time = System.currentTimeMillis();
            }
        }
        if (visibleMonster.isEmpty()) {
//            attackEnemyNum = Constants.LEVEL_MONSTER_MAX.get(LevelManager.getCurrentIndexLevel());

        } else {
//            System.out.println(1);
        }
    }

    public void forceFree(MonsterShip item) {

        isAttacked = true;
        monsterShipPool.free(item);
        visibleMonster.remove(item.getName());
        item.dispose();
        Constants.ACTIVE_ENEMIES.removeValue( item, false);
        attackEnemyNum--;

        Integer monsterCount = Constants.LEVEL_MONSTER_COUNT.get(LevelManager.getCurrentIndexLevel());

        // 没有敌人,并且是最大关头时,再弹出来
        if (Constants.ACTIVE_ENEMIES.isEmpty() && monsterCount == Constants.LEVEL_MONSTER_MAX.get(LevelManager.getCurrentIndexLevel())) {
            isLevelComplete = true;
            Controllers.getInstance().junction = true;
            LevelManager.goToNextLevel();
            isAttacked = false;
        }
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

    public Map<String, MonsterShip> getVisibleMonster() {
        return visibleMonster;
    }

    public boolean isAttacked() {
        return isAttacked;
    }

    public MonsterFactoryController setAttacked(boolean attacked) {
        isAttacked = attacked;
        return this;
    }

    public int getAttackEnemyNum() {
        return attackEnemyNum;
    }

    public MonsterFactoryController setAttackEnemyNum(int attackEnemyNum) {
        this.attackEnemyNum = attackEnemyNum;
        return this;
    }
}
