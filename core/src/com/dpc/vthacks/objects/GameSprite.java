package com.dpc.vthacks.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.App;
import com.dpc.vthacks.gameobject.GameObject;
import com.dpc.vthacks.properties.Properties;

public class GameSprite extends GameObject {

    public GameSprite(TextureRegion region, float x, float y) {
        super(region, x, y);
    }

    public GameSprite(TextureRegion textureRegion, Properties props, float x, float y) {
        super(textureRegion, x, y);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render() {
        draw(App.batch);
    }

}
