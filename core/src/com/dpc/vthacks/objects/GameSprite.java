package com.dpc.vthacks.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.App;
import com.dpc.vthacks.gameobject.GameObject;

public class GameSprite extends GameObject {

    public GameSprite(TextureRegion region, float x, float y) {
        super(region, x, y);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render() {
        draw(App.batch);
    }

}
