package com.dpc.vthacks.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dpc.vthacks.App;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Metro Z v 0.0.0";
        config.width = 1024;
        config.height = 768;
        new LwjglApplication(new App(), config);

        
//        ObjectMap<String, ObjectMap<String, Upgrade>> u = new ObjectMap<String, ObjectMap<String, Upgrade>>();
// 
//        ObjectMap<String, Upgrade> s = new ObjectMap<String, Upgrade>();
//        
//        s.put("ammo1", new Upgrade(50,0 50));
//        s.put("ammo2", new Upgrade(100, 100));
//        
//        u.put("handgun", s);
//        
//        Json j = new Json();
//        String as = j.toJson(u, ObjectMap.class);
//        System.err.println(as);
    }

    private static class Upgrade {
        public float cost;
        public float benefit;
        public Upgrade(float cost, float benefit) {
            super();
 
            this.cost = cost;
            this.benefit = benefit;
        }
        
        
    }
}
