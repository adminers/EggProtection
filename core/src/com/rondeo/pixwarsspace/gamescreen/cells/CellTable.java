package com.rondeo.pixwarsspace.gamescreen.cells;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.dongbat.jbump.World;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.gamescreen.components.entity.ArtilleryShip;
import com.rondeo.pixwarsspace.gamescreen.components.entity.SquareShip;
import com.rondeo.pixwarsspace.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class CellTable {

    private Table table;

    private Actor[][] grid;

    World<Entity> world;

    private TextureAtlas assets;

    List<ImageButton> imageButtonList = new ArrayList<>(4 * 9);

    ImageButton[][] imageButtons;

    public CellTable(World<Entity> world, TextureAtlas assets) {
        table = new Table();
        table.center();
        table.setFillParent(true);
//        table.setBounds(10, 10, table.getWidth(), getTable().getHeight());
//        table.setY(150);
//        table.setX(200);


        // 设置表格的纵向偏移，使其垂直居中
//        float verticalOffset = (Gdx.graphics.getHeight() - table.getPrefHeight()) / 4;
        table.setY(-Constants.CELL_HIGHT);
        grid = new Actor[4][10];
        imageButtons = new ImageButton[4][10];
        Texture cellTexture = new Texture(Gdx.files.internal("bg/cell.jpg"));
        TextureRegionDrawable buttonDrawable = new TextureRegionDrawable(cellTexture);

        // 在每个格子中创建或放置任意对象
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 9; j++) {
                ImageButton button = new CenterImageButton(buttonDrawable,  i,  j, world,  assets,  this);
                grid[i][j] = button;
                imageButtons[i][j] = button;

                // 创建或放置任意对象（这里只是示例）
                // 比如创建一个按钮
                table.add(button).size(20).pad(1);
//                button.addListener(new InputListener() {
//
//                    @Override
//                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                        if (Constants.CREATE_CELL_TYPE.equals("ship")) {
//                            System.out.println(finalI + "---" + finalJ);
//                            SquareShip ship = new SquareShip(world);
//                            ship.setRegions(assets.findRegion("ship"), assets.findRegion("wing"), assets.findRegion("ship_sketch"), assets.findRegion("wing_sketch"), assets.findRegions("thrusters"), assets.findRegion("effect"));
////                            BreathingEffect.applyBreathingEffect(ship, 1.2f);
//                            updateCell(finalI, finalJ, ship);
//                        } else if (Constants.CREATE_CELL_TYPE.equals("alien")) {ImageButton.this.getX();
//                            System.out.println(finalI + "---" + finalJ);
//                            ArtilleryShip ship = new ArtilleryShip(world);
//                            ship.setRegions(Constants.BARN.findRegion("head"), assets.findRegion("wing"), assets.findRegion("ship_sketch"), assets.findRegion("wing_sketch"), assets.findRegions("thrusters"), assets.findRegion("effect"));
////                            BreathingEffect.applyBreathingEffect(ship, 1.2f);
//                            System.out.println(x + "==点击==" + y);
//
//                            updateCell(finalI, finalJ, ship);
//                        }
//                        return true;
//                    }
//                });
            }
            table.row();
        }

//        table.setOrigin(table.getWidth()/2, table.getHeight()/2);
    }

    public void updateCell(int row, int col, Actor newActor) {
        Cell cell = table.getCell(grid[row][col]);
        grid[row][col] = newActor;
        if (cell != null) {
            cell.setActor(newActor);
        }
    }

    public Actor getCell(int row, int col) {

        return table.getCell(grid[row][col]).getActor();
    }

    public ImageButton getImageButton(int row, int col) {

        return this.imageButtons[row][col];
    }

    public Table getTable() {
        return table;
    }
}
