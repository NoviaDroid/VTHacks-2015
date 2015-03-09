package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Fonts {
    private static BitmapFont zombie;
    private static BitmapFont zombieSmall;
    private static BitmapFont zombieXSmall;
    private static BitmapFont visitor;
    
    public static void load() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/RobotoCondensed-Bold.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        
        parameter.size = Gdx.graphics.getWidth() * 104 / 1200;
   //     parameter.borderColor = Color.BLACK;
     //   parameter.borderWidth = 3;
   //     parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
   
        zombie = generator.generateFont(parameter); 
        
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/RobotoCondensed-Bold.ttf"));
        
        parameter.size = Gdx.graphics.getWidth() * 54 / 1200;
        
        zombieSmall = generator.generateFont(parameter);
       
        parameter.size = Gdx.graphics.getWidth() * 36 / 1200;
        zombieXSmall = generator.generateFont(parameter);
        
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/RobotoCondensed-Bold.ttf"));

        visitor = generator.generateFont(parameter);
        
        generator.dispose();
    }
    
    public static BitmapFont getVisitor() {
        return visitor;
    }
    
    public static BitmapFont getZombie() {
        return zombie;
    }
    
    public static BitmapFont getZombieSmall() {
        return zombieSmall;
    }
    
    public static BitmapFont getZombieXSmall() {
        return zombieXSmall;
    }
    
    public static void unload() {
        zombie.dispose();
        zombieSmall.dispose();
        zombieXSmall.dispose();
        visitor.dispose();
    }
}
