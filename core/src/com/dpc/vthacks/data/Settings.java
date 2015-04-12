package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.dpc.vthacks.App;


public class Settings {
    private boolean scanLineShaderEnabled;
    private boolean audioEnabled;
    private boolean dynamicLightingEnabled;
    private Preferences prefs;
    
    public Settings() {
        prefs = Gdx.app.getPreferences("appsettings");
        
        scanLineShaderEnabled = prefs.getBoolean("scanLineShaderEnabled", false);
        audioEnabled = prefs.getBoolean("audioEnabled", false);
        dynamicLightingEnabled = prefs.getBoolean("dynamicLightingEnabled", false);
    }
    
    public void setAudioEnabled(boolean audioEnabled) {
        this.audioEnabled = audioEnabled;
        prefs.putBoolean("audioEnabled", audioEnabled);
        prefs.flush();
    }
    
    public boolean isAudioEnabled() {
        return audioEnabled;
    }
    
    public void setScanLineShaderEnabled(boolean scanLineShaderEnabled) {
        this.scanLineShaderEnabled = scanLineShaderEnabled;
        prefs.putBoolean("scanLineShaderEnabled", scanLineShaderEnabled);
        prefs.flush();
        
        if(scanLineShaderEnabled) {
            App.batch.setShader(App.crtShader);
        }
        else {
            App.batch.setShader(App.defaultShader);
        }
    }
    
    public boolean isScanLineShaderEnabled() {
        return scanLineShaderEnabled;
    }
    
    public void setDynamicLightingEnabled(boolean dynamicLightingEnabled) {
        this.dynamicLightingEnabled = dynamicLightingEnabled;
        
        prefs.putBoolean("dynamicLightingEnabled", dynamicLightingEnabled);
        prefs.flush();
    }
    
    public boolean isDynamicLightingEnabled() {
        return dynamicLightingEnabled;
    }
}
