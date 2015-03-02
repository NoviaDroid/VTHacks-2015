package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Fonts {
    private static BitmapFont zombie;
    private static BitmapFont zombieSmall;
    
    public static void load() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ZOMBIE.TTF"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        
        parameter.size = Gdx.graphics.getWidth() * 84 / 1200;

        zombie = generator.generateFont(parameter); 
        
        parameter.size = Gdx.graphics.getWidth() * 54 / 1200;
        
        zombieSmall = generator.generateFont(parameter);
        
        generator.dispose(); 
    }
    
    public static BitmapFont getZombie() {
        return zombie;
    }
    
    public static BitmapFont getZombieSmall() {
        return zombieSmall;
    }
    
    public static void unload() {
        zombie.dispose();
    }
}
