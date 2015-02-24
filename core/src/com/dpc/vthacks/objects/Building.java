package com.dpc.vthacks.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.App;
import com.dpc.vthacks.Collidable;
import com.dpc.vthacks.properties.Properties;

public class Building extends Collidable {

    public Building(TextureRegion region, Properties properties) {
        super(region, properties);
        
        setSize(getWidth() * 5, getHeight() * 5);
    }

    @Override
    public void onCollision(Collidable object) {
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render() {
        draw(App.batch);
    }
}
