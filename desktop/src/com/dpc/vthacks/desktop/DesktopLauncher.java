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
    }
}
