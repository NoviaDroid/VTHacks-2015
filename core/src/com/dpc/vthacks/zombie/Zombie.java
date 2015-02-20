package com.dpc.vthacks.zombie;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.Collidable;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.properties.Properties;

public class Zombie extends Unit {

    public Zombie(TextureRegion region, Properties properties) {
        super(region, properties);
    }

    @Override
    public void onCollision(Collidable obj) {
    }

    @Override
    public void render() {
    }

    @Override
    public void onDeath() {
    }

    @Override
    public void attack(Unit enemy) {
    }

    @Override
    public void onDamageTaken(float amount) {
    }
    
}
