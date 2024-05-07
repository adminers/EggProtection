package com.rondeo.pixwarsspace.utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Null;
import com.rondeo.pixwarsspace.gamescreen.components.Controllers;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;

public class Background extends Actor implements Disposable {
    private final Array<AtlasRegion> explosionRegions;
    Texture bg;
    TextureRegion bgRegion0, bgRegion1, bgRegion2;
    Entity.Wrapper ship;

    Animation<AtlasRegion> explosionAnimation;

    Animation<AtlasRegion> bgAnimation;

    TextureRegion backgroundRegion;

    TextureRegion backgroundRegion1;
    TextureRegion backgroundRegion2;
    float deltaTime;

    private float backgroundX1;
    private float backgroundX2;
    private float backgroundSpeed = 2f;

    public void setShip( @Null Entity.Wrapper ship ) {
        this.ship = ship;
    }
    float backgroundX = 0;


//    @Override
//    public void act(float delta) {
//        super.act(delta);
////        bgRegion0.scroll(0, -(delta/50f));
//
////        bgRegion2.scroll( 0, -(delta/2f) );
////        bgRegion1.scroll( 0, -(delta/40f) );
//
////        deltaTime += delta;
//    }

    public Background( int width, int height, @Null Entity.Wrapper ship ) {
//        bg = new Texture( Gdx.files.internal( "lib/t_map/bg.png" ) );
//        bg.setWrap( TextureWrap.ClampToEdge, TextureWrap.Repeat );

//        bgRegion0 = new TextureRegion( bg, 0, 0, 200, 400 );

        /*bgRegion0 = new TextureRegion( bg, 0, 0, 200, 400 );
        bgRegion1 = new TextureRegion( bg, 200, 0, 200, 400 );
        bgRegion2 = new TextureRegion( bg, 400, 0, 200, 400 );*/

        TextureAtlas BARN = new TextureAtlas(Gdx.files.internal("lib/t_map/yun/yun.atlas"));
//        this.explosionRegions = BARN.findRegions( "fudong" );
        explosionRegions = new Array<>();
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0001.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0002.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0003.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0004.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0005.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0006.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0007.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0008.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0009.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0010.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0011.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0012.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0013.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0014.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0015.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0016.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0017.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0018.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0019.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0020.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0021.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0022.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0023.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0024.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0025.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0026.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0027.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0028.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0029.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0030.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0031.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0032.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0033.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0034.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0035.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0036.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0037.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0038.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0039.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0040.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0041.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0042.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0043.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0044.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0045.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0046.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0047.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0048.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0049.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0050.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0051.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0052.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0053.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0054.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0055.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0056.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0057.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0058.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0059.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0060.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0061.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0062.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0063.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0064.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0065.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0066.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0067.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0068.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0069.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0070.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0071.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0072.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0073.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0074.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0075.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0076.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0077.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0078.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0079.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0080.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0081.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0082.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0083.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0084.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0085.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0086.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0087.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0088.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0089.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0090.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0091.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0092.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0093.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0094.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0095.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0096.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0097.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0098.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0099.png"))));
        explosionRegions.add(new AtlasRegion(new TextureRegion(new Texture("lib/background/0100.png"))));

//        bg = new Texture( Gdx.files.internal( "bg/bg.png" ) );
//        bg.setWrap( TextureWrap.ClampToEdge, TextureWrap.Repeat );

//        bgRegion0 = new TextureRegion( bg, 0, 0, 200, 400 );
//        bgRegion1 = new TextureRegion( bg, 200, 0, 200, 400 );
//        bgRegion2 = new TextureRegion( bg, 400, 0, 200, 400 );

        this.ship = ship;
        explosionAnimation = new Animation<>(1.31f, explosionRegions);
        explosionAnimation.setPlayMode(PlayMode.LOOP);


        AtlasRegion bgRegion = new AtlasRegion(new TextureRegion(new Texture("lib/t_map/testYan/Purple_Nebula_08-1024x1024.png")));
        Array<AtlasRegion> bgRegions = new Array<>();
        bgRegions.add(bgRegion);
        bgAnimation = new Animation<>(.31f, bgRegions);
        bgAnimation.setPlayMode(PlayMode.LOOP);

        Texture backgroundTexture = new Texture("lib/t_map/testYan/Purple_Nebula_08-1024x1024.png");
        backgroundRegion = new TextureRegion(backgroundTexture, 0, 0, backgroundTexture.getWidth() / 2, backgroundTexture.getHeight());


        backgroundRegion1 = new TextureRegion(backgroundTexture, 0, 0, backgroundTexture.getWidth() / 2, backgroundTexture.getHeight());
        backgroundRegion2 = new TextureRegion(backgroundTexture, backgroundTexture.getWidth() / 2, 0, backgroundTexture.getWidth() / 2, backgroundTexture.getHeight());

        backgroundX1 = 0;
        backgroundX2 = Gdx.graphics.getWidth();

    }

    @Override
    public void draw( Batch batch, float parentAlpha ) {

        deltaTime += parentAlpha;
        super.draw( batch, parentAlpha );

//        batch.draw( bgRegion0, 0, 0/*80 - 12*/, getStage().getWidth(), getStage().getHeight() );

//        batch.draw( bgRegion0, 0, 0, getStage().getWidth(), getStage().getHeight() );
//        batch.draw( bgRegion1, 0, 0, getStage().getWidth(), getStage().getHeight() );
//        batch.draw( bgRegion2, 0, 0, getStage().getWidth(), getStage().getHeight() );
//        batch.draw( bgRegion2, 0, 0, getStage().getWidth(), getStage().getHeight() );

        /*batch.draw(backgroundRegion1, backgroundX, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
        batch.draw(backgroundRegion2, backgroundX + Gdx.graphics.getWidth() / 2, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
        */

         // 绘制第一张背景图片
        batch.draw(backgroundRegion1, backgroundX1, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // 绘制第二张背景图片
        batch.draw(backgroundRegion2, backgroundX2, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        backgroundX1 -= backgroundSpeed;
        backgroundX2 -= backgroundSpeed;

        if (backgroundX1 + Gdx.graphics.getWidth() <= 0) {
            backgroundX1 = backgroundX2 + Gdx.graphics.getWidth();
        }

        if (backgroundX2 + Gdx.graphics.getWidth() <= 0) {
            backgroundX2 = backgroundX1 + Gdx.graphics.getWidth();
        }



//        batch.draw(backgroundRegion, backgroundX, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
//        batch.draw( bgAnimation.getKeyFrame( deltaTime ), 0, 0, getStage().getWidth(), getStage().getHeight() );
        batch.draw(explosionAnimation.getKeyFrame(deltaTime), 0, 0, getStage().getWidth(), getStage().getHeight());

        backgroundX -= 1.1f; // 修改滚动速度
//        if (backgroundX <= -Gdx.graphics.getWidth() / 2) {
//            backgroundX = 0;
//        }
        if( ship !=null ) {
            if( ship.isHit > System.currentTimeMillis() || Controllers.getInstance().gameOver ) {
                batch.setColor( 1, 0, 0, .6f );
                //System.out.println( Gdx.graphics.getFramesPerSecond() );
            } else {
                batch.setColor( Color.WHITE );
            }
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
    }
    
}
