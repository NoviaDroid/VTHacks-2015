package com.dpc.vthacks.properties;

import com.badlogic.gdx.utils.ObjectMap;
import com.dpc.vthacks.animation.SpriteAnimation;
import com.dpc.vthacks.zombie.ZombieSegment;

public class ZombieProperties extends AnimatedUnitProperties<SpriteAnimation> {
    private int minKillMoney, maxKillMoney; // How much money played awarded when killed
    private ZombieSegment[] segments;
    private float attackSpeed;
    
    public ZombieProperties() {
        super();
    }
    
    public ZombieProperties(ZombieProperties cpy) {
        super(cpy);
        
        ObjectMap<String, SpriteAnimation> map = new ObjectMap<String, SpriteAnimation>();
        
        for(String key : cpy.getStateAnimations().keys()) {
            SpriteAnimation value = cpy.getStateAnimations().get(key);
            
            SpriteAnimation cpyAnim = new SpriteAnimation(value);

            cpyAnim.getAnimation().setPlayMode(value.getAnimation().getPlayMode());

            map.put(new String(key), cpyAnim);
        }

        stateAnimations(map);
        
        segments = new ZombieSegment[cpy.getSegments().length];
        
        for(int i = 0; i < cpy.getSegments().length; i++) {
            segments[i] = new ZombieSegment(cpy.getSegments()[i]);
        }
        
        minKillMoney = cpy.minKillMoney;
        maxKillMoney = cpy.maxKillMoney;
        
        attackSpeed = cpy.attackSpeed;
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               String.format("%s,\n%s,\n%s,\n%s,\n", 
                             "Min Kill Money: " + minKillMoney,
                             "Max Kill Money: " + maxKillMoney,
                             "Segments: " + segments,
                             "Attack Speed: " + attackSpeed);
    }
    
    @Override
    public ZombieProperties scaleX(float scaleX) {
        super.scaleX(scaleX);
        
        return this;
    }
    
    @Override
    public ZombieProperties scaleY(float scaleY) {
        super.scaleY(scaleY);
        
        return this;
    }
    
    public ZombieProperties attackSpeed(float speed) {
        this.attackSpeed = speed;
        return this;
    }
    
    public float getAttackSpeed() {
        return attackSpeed;
    }
    
    @Override
    public ZombieProperties frameTime(float frameTime) {
        super.frameTime(frameTime);
        
        return this;
    }

    @Override
    public ZombieProperties cost(int cost) {
        super.cost(cost);
        
        return this;
    }

    @Override
    public ZombieProperties health(float health) {
        super.health(health);
        
        return this;
    }

    @Override
    public ZombieProperties maxDamage(float maxDamage) {
        super.maxDamage(maxDamage);
        
        return this;
    }

    @Override
    public ZombieProperties minDamage(float minDamage) {
        super.minDamage(minDamage);
        
        return this;
    }

    @Override
    public ZombieProperties range(float range) {
        super.range(range);
        
        return this;
    }

    @Override
    public ZombieProperties vel(float vx, float vy) {
        super.vel(vx, vy);
        
        return this;
    }

    @Override
    public ZombieProperties minVel(float vx, float vy) {
        super.minVel(vx, vy);
        
        return this;
    }

    @Override
    public ZombieProperties maxVel(float vx, float vy) {
        super.maxVel(vx, vy);
        
        return this;
    }

    @Override
    public ZombieProperties maxHealth(float maxHealth) {
        super.maxHealth(maxHealth);
        
        return this;
    }

    @Override
    public ZombieProperties build() {
        super.build();
        
        return this;
    }

    public ZombieProperties minKillMoney(int minKillMoney) {
        this.minKillMoney = minKillMoney;
        
        return this;
    }
    
    public ZombieProperties maxKillMoney(int maxKillMoney) {
        this.maxKillMoney = maxKillMoney;
        
        return this;
    }
    
    @Override
    public ZombieProperties stateAnimations(
            ObjectMap<String, SpriteAnimation> stateAnimations) {
        super.stateAnimations(stateAnimations);
        
        return this;
    }
    
    public void segments(ZombieSegment[] segments) {
        this.segments = segments;
    }
    
    public ZombieSegment[] getSegments() {
        return segments;
    }
    
    public int getMinKillMoney() {
        return minKillMoney;
    }
    
    public int getMaxKillMoney() {
        return maxKillMoney;
    }
}
