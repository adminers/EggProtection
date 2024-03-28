package com.rondeo.pixwarsspace.gamescreen.cells;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.dongbat.jbump.World;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.gamescreen.components.entity.ArtilleryShip;
import com.rondeo.pixwarsspace.gamescreen.components.entity.SquareShip;
import com.rondeo.pixwarsspace.utils.Constants;

public class CenterImageButton extends ImageButton {

    public CenterImageButton(Drawable imageUp, int finalI, int finalJ, World<Entity> world, TextureAtlas assets, CellTable cellTable) {
        super(imageUp);

        this.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Constants.CREATE_CELL_TYPE.equals("ship")) {
                    System.out.println(finalI + "---" + finalJ);
                    SquareShip ship = new SquareShip(world);
                    ship.setRegions(assets.findRegion("ship"), assets.findRegion("wing"), assets.findRegion("ship_sketch"), assets.findRegion("wing_sketch"), assets.findRegions("thrusters"), assets.findRegion("effect"));
//                            BreathingEffect.applyBreathingEffect(ship, 1.2f);
                    cellTable.updateCell(finalI, finalJ, ship);
                } else if (Constants.CREATE_CELL_TYPE.equals("alien")) {

                    // 获取点击位置相对于ImageButton的坐标
                    float clickX = x;
                    float clickY = y;

                    // 获取ImageButton在舞台中的坐标
                    float stageX = CenterImageButton.this.getX();
                    float stageY = CenterImageButton.this.getY();

                    ArtilleryShip ship = new ArtilleryShip(world, stageX, stageY);
                    ship.setRegions(assets.findRegion( "bullet_blue" )/*Constants.DB.findRegion("fire")*/, assets.findRegion("wing"), assets.findRegion("ship_sketch"), assets.findRegion("wing_sketch"), assets.findRegions("thrusters"), assets.findRegion("effect"));
//                            BreathingEffect.applyBreathingEffect(ship, 1.2f);

                    cellTable.updateCell(finalI, finalJ, ship);




                    // 获取点击位置相对于舞台的坐标
                    float screenX = stageX + clickX;
                    float screenY = stageY + clickY;

                    // 打印点击位置信息
//                    System.out.println("点击位置相对于ImageButton的坐标：(" + clickX + ", " + clickY + ")");
//                    System.out.println("ImageButton在舞台中的坐标：(" + stageX + ", " + stageY + ")");
//                    System.out.println("点击位置相对于舞台的坐标：(" + screenX + ", " + screenY + ")");
                }
                return true;
            }
        });
    }
}
