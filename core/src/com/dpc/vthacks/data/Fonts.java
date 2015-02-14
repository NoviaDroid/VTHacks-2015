package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Fonts {
    private static BitmapFont armalite;
    
    public static void load() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/armalite.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = (int) (16 * Gdx.graphics.getDensity());
        armalite = generator.generateFont(parameter); 
        generator.dispose(); 
    }
    
    public static BitmapFont getArmalite() {
        return armalite;
    }
    
    public static void unload() {
        armalite.dispose();
    }
}
