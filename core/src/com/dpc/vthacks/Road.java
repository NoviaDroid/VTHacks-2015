package com.dpc.vthacks;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.dpc.vthacks.data.Assets;

public class Road extends Rectangle {
    private TextureRegion region;
    private float texWidth, texHeight;
    
    public Road(float rectX, float rectY, float rectWidth, float rectHeight) {
        super(rectX, rectY, rectWidth, rectHeight);
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
        App.batch.draw(region, x, y, texWidth, texHeight);
    }
}
