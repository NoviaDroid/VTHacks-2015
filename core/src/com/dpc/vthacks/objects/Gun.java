package com.dpc.vthacks.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.App;
import com.dpc.vthacks.animation.FrameData;
import com.dpc.vthacks.gameobject.GameObject;

public class Gun extends GameObject {
    private float minDamage, maxDamage;
    private int ammo;
    private final int MAX_AMMO;
    private FrameData data;
    private TextureRegion region;
    
    public Gun(Gun cpy) {
        this(cpy.minDamage, cpy.maxDamage, cpy.MAX_AMMO, cpy.data);
    }
    
    public Gun(float minDamage, float maxDamage, int maxAmmo, FrameData data) {
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.MAX_AMMO = maxAmmo;
        this.ammo = MAX_AMMO;
        this.data = data;
        region = data.getRegion();
        
        setRegion(data.getRegion());
        setSize(data.getRegion().getRegionWidth() * 2, data.getRegion().getRegionHeight() * 2);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render() {
        draw(App.batch);
    }
    
    public float getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(float minDamage) {
        this.minDamage = minDamage;
    }

    public float getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(float maxDamage) {
        this.maxDamage = maxDamage;
    }

    public FrameData getData() {
        return data;
    }

    public int getAmmo() {
        return ammo;
    }
    
    public void decAmmo(int dec) {
        ammo -= dec;
    }
    
    public TextureRegion getRegion() {
        return region;
    }
    
    public int getMaxAmmo() {
        return MAX_AMMO;
    }
    
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
    
    public void setData(FrameData data) {
        this.data = data;
    }
}
