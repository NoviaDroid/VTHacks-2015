package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Fonts {
    private static BitmapFont visitor1;
    
    public static void load() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/armalite.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        
        parameter.size = Gdx.graphics.getWidth() * 16 / 800;
        System.out.println(parameter.size);
        visitor1 = generator.generateFont(parameter); 
        generator.dispose(); 
    }
    
    public static BitmapFont getVisitor1() {
        return visitor1;
    }
    
    public static void unload() {
        visitor1.dispose();
    }
}
