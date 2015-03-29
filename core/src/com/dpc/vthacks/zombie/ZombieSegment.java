package com.dpc.vthacks.zombie;

import com.badlogic.gdx.math.Rectangle;
import com.dpc.vthacks.properties.Segment;

public class ZombieSegment extends Segment {
    public float damageFactor; // How much extra damage for hitting this segment?

    public ZombieSegment() {
    
    }
    
    public ZombieSegment(ZombieSegment cpy) {
        this.damageFactor = cpy.damageFactor;
        this.bounds = new Rectangle(cpy.bounds);
        this.offsetX = cpy.offsetX;
        this.offsetY = cpy.offsetY;
    }
}
