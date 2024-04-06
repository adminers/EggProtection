package com.rondeo.pixwarsspace.gamescreen.components.play;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.World;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.gamescreen.components.Scenery;

public class BubblesShip extends Scenery {

    World<Entity> world;
    Item<Entity> item;
    Rect rect;
    private ShapeRenderer shapeRenderer;

    private Texture circleTexture;
    int width = 100, height = 100;

    public BubblesShip(World<Entity> world) {
        this.world = world;
        item = new Item<Entity>( this );
        world.add( item, 0, 0, 0, 0 );
        shapeRenderer = new ShapeRenderer();
        this.circleTexture = new Texture("lib/lightning/Bubbles.png");
    }

    public void init(float positionX, float positionY) {
        world.update( item, positionX, positionY , width, height );
        setOrigin(getWidth() / 2f, getHeight() / 2f);
//        world.update( item, 0, 100, width, height );
        resolve();
    }

    @Override
    public void act(float delta) {
//        world.move( item, getX(), getY(), null );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw( circleTexture, getX() - 38, getY() / 2, width, height);
    }

    public void resolve() {
        rect = world.getRect( item );
        setBounds( rect.x, rect.y, rect.w, rect.h );
    }
}
