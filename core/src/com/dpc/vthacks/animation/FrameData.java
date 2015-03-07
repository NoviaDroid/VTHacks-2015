package com.dpc.vthacks.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FrameData {
    private TextureRegion region;
    private float speed;
    private float anchorOffsetX, anchorOffsetY; // Offset for an anchor in the image

    public FrameData(FrameData cpy) {
        this.region = new TextureRegion(cpy.getRegion());
        this.speed = cpy.getSpeed();
        this.anchorOffsetX = cpy.getAnchorOffsetX();
        this.anchorOffsetY = cpy.getAnchorOffsetY();
    }
    
    public FrameData(TextureRegion region, float speed, float anchorOffsetX, float anchorOffsetY) {
        this.region = region;
        this.speed = speed;
        this.anchorOffsetX = anchorOffsetX;
        this.anchorOffsetY = anchorOffsetY;
    }

    public float getSpeed() {
        return speed;
    }
    
    public void setSpeed(float speed) {
        this.speed = speed;
    }
    
    public TextureRegion getRegion() {
        return region;
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
    }

    public float getAnchorOffsetX() {
        return anchorOffsetX;
    }

    public void setAnchorOffsetX(float anchorOffsetX) {
        this.anchorOffsetX = anchorOffsetX;
    }

    public float getAnchorOffsetY() {
        return anchorOffsetY;
    }

    public void setAnchorOffsetY(float anchorOffsetY) {
        this.anchorOffsetY = anchorOffsetY;
    }
}
