package com.dpc.vthacks;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.gameobject.GameObject;
import com.dpc.vthacks.objects.LayerManager;

public class Road extends GameObject {
    private TextureRegion region;
    private float texWidth, texHeight;

    public Road(float x, float y, float rectX, float rectY, float rectWidth, float rectHeight) {
        super(Assets.road, x, y);
        setBounds(x, y, rectWidth, rectHeight);
        
        texWidth = LevelProperties.WIDTH;
        texHeight = Assets.road.getRegionHeight() * 2;
        
        region = Assets.road;
    }

    public TextureRegion getRegion() {
        return region;
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
    }

    public float getTexWidth() {
        return texWidth;
    }

    public void setTexWidth(float texWidth) {
        this.texWidth = texWidth;
    }

    public float getTexHeight() {
        return texHeight;
    }

    public void setTexHeight(float texHeight) {
        this.texHeight = texHeight;
    }

    public void render() {
        App.batch.draw(region, getX(), getY(), texWidth, texHeight);
    }

    @Override
    public void update(float delta) {
    }
}
