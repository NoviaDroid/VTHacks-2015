package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;


public class Sounds {
    public static Sound explosion;
    
    public static void load() {
        explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav")); 
    }
    
    public static void dispose() {
        explosion.dispose();
    }
}
