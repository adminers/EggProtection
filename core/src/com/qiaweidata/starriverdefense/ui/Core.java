package com.qiaweidata.starriverdefense.ui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.Hinting;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Core extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    public static DesktopWorker desktopWorker;
    private int dragStartX, dragStartY;
    private int windowStartX, windowStartY;
    private int focusIndex;
    private Array<TextButton> buttons;

    @Override
    public void create() {
        focusIndex = -1;
        
        skin = new Skin(Gdx.files.internal("lib/UIExample/shadow-walker-ui.json")) {
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
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        final Table root = new Table();
        root.setTouchable(Touchable.enabled);
        root.setFillParent(true);
        stage.addActor(root);
        
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
        
        stage.addListener(new DragListener() {
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                if (event.getTarget() == root) {
                    desktopWorker.dragWindow(windowStartX + (int) x - dragStartX, windowStartY + dragStartY - (int) y);
                    windowStartX = desktopWorker.getWindowX();
                    windowStartY = desktopWorker.getWindowY();
                }
            }

            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                dragStartX = (int) x;
                dragStartY = (int) y;
                windowStartX = desktopWorker.getWindowX();
                windowStartY = desktopWorker.getWindowY();
            }
        });
        
        stage.addListener(new InputListener() {
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                removeFocus();
                return super.mouseMoved(event, x, y);
            }
            
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Keys.TAB:
                        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT)) {
                            focusIndex--;
                        } else {
                            focusIndex++;
                        }

                        updateFocus();
                        break;
                        
                    case Keys.UP:
                        focusIndex--;
                        updateFocus();
                        break;
                        
                    case Keys.DOWN:
                        focusIndex++;
                        updateFocus();
                        break;
                        
                    case Keys.SPACE:
                    case Keys.ENTER:
                        if (focusIndex >=0 && focusIndex < buttons.size) {
                            InputEvent touchEvent = new InputEvent();
                            touchEvent.setType(InputEvent.Type.touchDown);
                            buttons.get(focusIndex).fire(touchEvent);
                            
                            touchEvent = new InputEvent();
                            touchEvent.setType(InputEvent.Type.touchUp);
                            buttons.get(focusIndex).fire(touchEvent);
                        }
                        break;
                }
                
                return super.keyDown(event, keycode);
            }
            
        });
    }
    
    private void updateFocus() {
        if (focusIndex < 0) {
            focusIndex = buttons.size - 1;
        } else if (focusIndex >= buttons.size) {
            focusIndex = 0;
        }
        
        stage.setKeyboardFocus(buttons.get(focusIndex));
    }
    
    private void removeFocus() {
        focusIndex = -1;
        stage.setKeyboardFocus(null);
    }

    @Override
    public void render() {
        //clear the screen with complete transparency
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //fix incorrect blending
        stage.getBatch().setBlendFunction(-1, -1);
        Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
        
        //render the scene
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }
}
