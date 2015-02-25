package com.dpc.vthacks.zombie;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dpc.vthacks.App;
import com.dpc.vthacks.Collidable;
import com.dpc.vthacks.LevelProperties;
import com.dpc.vthacks.SpriteAnimation;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.infantry.AnimatedUnit;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.properties.Properties;

public class Zombie extends AnimatedUnit {
    private Vector2 dest, targ, currentTarget; // Final destination, temporary target
    private Vector2 vel;
    
    public Zombie(AtlasRegion[] frames, Properties properties) {
        super(frames, new SpriteAnimation(Assets.getTankFrames(), 0.15f), properties, 0.25f);
        
        setSize(getWidth() * 3, getHeight() * 3);
        
        Rectangle rect = LevelProperties.enemySpawns.get(MathUtils.random(LevelProperties.enemySpawns.size - 1));
        
        float y = rect.x + (MathUtils.random() * (rect.height - getHeight()));
        float x = rect.y + (MathUtils.random() * (rect.width - getWidth()));

        properties.setPos(new Vector2(rect.x, rect.y));
        setX(rect.x);
        setY(rect.y);
        
        dest = new Vector2(0, 50);
        currentTarget = new Vector2(dest);
        targ = new Vector2();
        vel = new Vector2();
        
        setCurrentTarget(0, 50);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        
        //setRotation(MathUtils.radiansToDegrees * (float) Math.atan2(getY() - currentTarget.y, getX() - currentTarget.x));
        addPos(getProperties().getVelX(), getProperties().getVelY());
    }
    
    @Override
    public void onCollision(Collidable obj) {
    }

    @Override
    public void render() {
        draw(App.batch);
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
    
    public Vector2 getTarg() {
        return targ;
    }
    
    public Vector2 getDest() {
        return dest;
    }
    
    public Vector2 getCurrentTarget() {
        return currentTarget;
    }
    
    public void setCurrentTarget(float x, float y) {
        currentTarget.set(x, y);
        
        vel.set(currentTarget.x - getX(), currentTarget.y - getY());
        vel.nor(); 
        
        vel.x *= getProperties().getVelX(); 
        vel.y *= getProperties().getVelY();
    }
    
    public void setTarg(Vector2 targ) {
        this.targ = targ;
    }
    
    public void setDest(Vector2 dest) {
        this.dest = dest;
    }
}
