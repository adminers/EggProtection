package com.qiaweidata.starriverdefense;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.qiaweidata.starriverdefense.menuscreen.ShenMenuScreen;

public class ShenMain extends Game {
    public int width = 200;
    public int height = 400;
    Pixmap curPixmap;
        
    @Override
    public void create () {
        curPixmap = new Pixmap( Gdx.files.internal( "input/cursor.png" ) );
        Gdx.graphics.setCursor( Gdx.graphics.newCursor( curPixmap, 0, 0 ) );
        setScreen( new ShenMenuScreen( this ) );
    }

    @Override
    public void dispose() {
        super.dispose();
        curPixmap.dispose();
    }

}
