package com.rondeo.pixwarsspace.gamescreen.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.rondeo.pixwarsspace.gamescreen.components.Controllers;
import com.rondeo.pixwarsspace.gamescreen.components.HudManager;
import com.rondeo.pixwarsspace.gamescreen.components.LevelManager;
import com.rondeo.pixwarsspace.utils.Constants;

public class ComplexDialog extends Dialog {

    private HudManager hudManager;

    private Array<TextButton> buttons;

    private Image nextImage;
    private Skin skin;

    private static final float BLINK_DURATION = 0.5f;
    private static final int BLINK_TIMES = 3;

    /*public ComplexDialog(HudManager hudManager, String title, Skin skin) {
        super(title, skin);
        this.hudManager = hudManager;
        skin = new Skin(Gdx.files.internal("lib/ui/NextLevel/shadow-walker-ui.json")) {
            //Override json loader to process FreeType fonts from skin JSON
            @Override
            protected Json getJsonLoader(final FileHandle skinFile) {
                Json json = super.getJsonLoader(skinFile);
                final Skin skin = this;

                json.setSerializer(FreeTypeFontGenerator.class, new Json.ReadOnlySerializer<FreeTypeFontGenerator>() {
                    @Override
                    public FreeTypeFontGenerator read(Json json,
                                                      JsonValue jsonData, Class type) {
                        String path = json.readValue("font", String.class, jsonData);
                        jsonData.remove("font");

                        Hinting hinting = Hinting.valueOf(json.readValue("hinting",
                                String.class, "AutoMedium", jsonData));
                        jsonData.remove("hinting");

                        TextureFilter minFilter = TextureFilter.valueOf(
                                json.readValue("minFilter", String.class, "Nearest", jsonData));
                        jsonData.remove("minFilter");

                        TextureFilter magFilter = TextureFilter.valueOf(
                                json.readValue("magFilter", String.class, "Nearest", jsonData));
                        jsonData.remove("magFilter");

                        FreeTypeFontParameter parameter = json.readValue(FreeTypeFontParameter.class, jsonData);
                        parameter.hinting = hinting;
                        parameter.minFilter = minFilter;
                        parameter.magFilter = magFilter;
                        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(skinFile.parent().child(path));
                        BitmapFont font = generator.generateFont(parameter);
                        skin.add(jsonData.name, font);
                        if (parameter.incremental) {
                            generator.dispose();
                            return null;
                        } else {
                            return generator;
                        }
                    }
                });

                return json;
            }
        };

        final Table root = new Table();
        root.setTouchable(Touchable.enabled);
        root.setFillParent(true);

        Stack stack = new Stack();
        root.add(stack);

        Image image = new Image(skin, "bg");
        image.setScaling(Scaling.none);
        image.setTouchable(Touchable.disabled);
        stack.add(image);

        Table table = new Table();
        stack.add(table);

        Label label = new Label("Shadow Walker UI", skin, "bg");
        label.setTouchable(Touchable.disabled);
        label.setAlignment(Align.center);
        table.add(label).growX();

        buttons = new Array<TextButton>();

        table.row();
        Table subTable = new Table();
        table.add(subTable);

        subTable.defaults().fillX();
        TextButton textButton = new TextButton("Start Game", skin);
        subTable.add(textButton);
        buttons.add(textButton);

        subTable.row();
        textButton = new TextButton("Multiplayer", skin);
        subTable.add(textButton);
        buttons.add(textButton);

        subTable.row();
        textButton = new TextButton("Options", skin);
        subTable.add(textButton);
        buttons.add(textButton);

        subTable.row();
        textButton = new TextButton("Quit", skin);
        subTable.add(textButton);
        buttons.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        table.row();
        subTable = new Table();
        table.add(subTable).expandY().top();

        label = new Label("There is no knowledge\nthat is not power", skin);
        label.setAlignment(Align.center);
        label.setTouchable(Touchable.disabled);
        subTable.add(label);

        subTable.row();
        label = new Label("-Ralph Waldo Emerson", skin, "small");
        label.setTouchable(Touchable.disabled);
        subTable.add(label).right();

        getContentTable().add(root).center();
    }*/

    public ComplexDialog(HudManager hudManager, String title, Skin skin) {
        super(title, skin);
        this.hudManager = hudManager;
        this.skin = skin;
        nextImage = new Image(new Texture(Gdx.files.internal("lib/font/next.png")));
        createTable();
    }


    public void createTable() {

        // 将table添加到一个新的Table中
        Table centerTable = new Table();

        // 创建一个白色纯色纹理
        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(new Color(0.3294f, 0.3294f, 0.3294f, 1f));
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        // 创建一个NinePatchDrawable，用白色纹理作为背景
        NinePatchDrawable background = new NinePatchDrawable(new NinePatch(texture));

        // 设置Table的背景为白色
        centerTable.setBackground(background);

        // 创建一个Table来管理布局
        Table table = new Table();
        //        table.setDebug(true);
        //        table.defaults().pad(10);

        // 添加按钮
        TextButton button = new TextButton("YES", skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Controllers.getInstance().pause = false;
                Controllers.getInstance().junction = false;
                Controllers.getInstance().getMonsterFactoryController().setAttacked(false);
                hide();
                LevelManager.goToNextShowLevel();

                // 重新设置血条
                hudManager.getHealthBarActor().rest(
                    new HealthBar(Constants.LEVEL_MONSTER_MAX.get(LevelManager.getCurrentIndexLevel()))
                );
                Controllers.getInstance().getMonsterFactoryController().setAttackEnemyNum(Constants.LEVEL_MONSTER_MAX.get(LevelManager.getCurrentIndexLevel()));
            }
        });
        nextImage.setScale(1f);
        table.add(nextImage).row();
        centerTable.add(table).center(); // 设置Table居中对齐并添加一些间距

        // 将Table添加到对话框中
        getContentTable().add(centerTable).center();

        // 创建一个淡入动作，从透明到完全可见，持续1秒
        AlphaAction fadeInAction = Actions.alpha(1.0f, 0.5f);

        // 创建一个淡出动作，从完全可见到透明，持续1秒
        AlphaAction fadeOutAction = Actions.alpha(0.0f, 0.5f);

        // 获取需要淡入淡出的图像

        // 添加淡入动作到图像上
        nextImage.addAction(Actions.sequence(
                Actions.alpha(0.0f), // 设置初始透明度为0
                fadeInAction,
                Actions.delay(0.5f), // 延迟2秒
                fadeOutAction,
                Actions.run(() -> nextLevel()),
                Actions.removeActor() // 移除弹出框
        ));

//        addAction(Actions.delay(0.5f, Actions.run(() -> flashAndCreatePlayer())));
    }

    // 闪动几下后执行createPlayer方法
    public void flashAndCreatePlayer() {

        // 添加闪烁动画效果
        addAction(Actions.sequence(
            Actions.repeat(3, Actions.sequence(
                Actions.alpha(0), // 透明
                Actions.delay(0.5f), // 延迟0.5秒
                Actions.alpha(1) // 不透明
            )),
            Actions.delay(0.5f), // 延迟0.5秒
            Actions.run(() -> createPlayer()), // 执行createPlayer方法
            Actions.removeActor() // 移除弹出框
        ));
    }

    // 创建玩家的方法
    private void createPlayer() {

        // 在这里执行创建玩家的逻辑
        System.out.println("Creating player...");
    }

    public Image getNextImage() {
        return this.nextImage;
    }

    private void nextLevel() {
        Controllers.getInstance().pause = false;
        Controllers.getInstance().junction = false;
        Controllers.getInstance().getMonsterFactoryController().setAttacked(false);
        hide();
        LevelManager.goToNextShowLevel();

        // 重新设置血条
        hudManager.getHealthBarActor().rest(
            new HealthBar(Constants.LEVEL_MONSTER_MAX.get(LevelManager.getCurrentIndexLevel()))
        );
        Controllers.getInstance().getMonsterFactoryController().setAttackEnemyNum(Constants.LEVEL_MONSTER_MAX.get(LevelManager.getCurrentIndexLevel()));
    }
}