package com.dpc.vthacks.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dpc.vthacks.Player;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.factories.Factory;

public class AmmoCrate extends GameSprite implements Poolable {
    
    public AmmoCrate(TextureRegion region, float x, float y) {
        super(region, x, y);
    }

    @Override
    public void reset() {
        setX(0);
        setY(0);
    }
    
    public void onPickedUp(Player p) {
        p.refillAmmo();
        Factory.ammoCratePool.free(this);
        Assets.playSound(Assets.OUT_OF_AMMO);
    }

}
