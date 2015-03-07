package com.dpc.vthacks.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;

public class AmmoCrate extends GameSprite implements Poolable {
    
    public AmmoCrate(TextureRegion region, float x, float y) {
        super(region, x, y);
    }

    @Override
    public void reset() {
        setX(0);
        setY(0);
    }

}
