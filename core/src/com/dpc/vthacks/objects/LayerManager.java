package com.dpc.vthacks.objects;

import com.badlogic.gdx.utils.Array;
import com.dpc.vthacks.GameCamera;
import com.dpc.vthacks.gameobject.GameObject;


public class LayerManager {
    private Array<Layer> layers;
    private static GameCamera camera;
    
    public static class Layer {
        private Array<GameObject> objects;
        private String name;
        private boolean scrolling;
        private float scrollFactor;
        private float scrollX, scrollY;
        
        public Layer(String name) {
            this(name, 0, 0, 0, false);
        }
        
        public Layer(String name, float scrollX, float scrollY, float scrollFactor, boolean scrolling) {
            this.name = name;
            this.scrollX = scrollX;
            this.scrollFactor = scrollFactor;
            this.scrolling = scrolling;
            this.scrollY = scrollY;
            objects = new Array<GameObject>();
        }
        
        public void update() {
            for(GameObject o : objects) {
                if(o.isScrollable()) {
                    o.setPosition(o.getX() + (scrollX * o.getScrollX()), o.getY() + (scrollY * o.getScrollY()));
                }
            }
        }
        
        public void render() {
            for(GameObject o : objects) {
                // Render only if visible
                if(camera.frustum.boundsInFrustum(o.getX(), o.getY(), 0, o.getWidth()*2, o.getHeight()*2, 0.5f)) {
                    o.render();
                }
            }
        }
        
        public void addObjects(Array<GameObject> go) {
            for(GameObject o : go) {
                objects.add(o);
            }
        }
        
        public float getScrollFactor() {
            return scrollFactor;
        }
        
        public void setScrollFactor(float scrollFactor) {
            this.scrollFactor = scrollFactor;
        }
        
        public Array<GameObject> getObjects() {
            return objects;
        }
        
        public void addObject(GameObject object) {
            objects.add(object);
        }
        
        public float getScrollX() {
            return scrollX;
        }
        
        public float getScrollY() {
            return scrollY;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public boolean isScrolling() {
            return scrolling;
        }
        
        public void setScrolling(boolean scrolling) {
            this.scrolling = scrolling;
        }

        public void setScrollX(float scrollX) {
            this.scrollX = scrollX;
        }

        public void setScrollY(float scrollY) {
            this.scrollY = scrollY;
        }
    }
    
    public LayerManager(int numbLayers) {
        layers = new Array<Layer>(numbLayers);
    }
    
    public void updateAndRender() {
        for(Layer layer : layers) {
            layer.update();
            layer.render();
        }
    }
       
    public Array<Layer> getLayers() {
        return layers;
    }
    
    public Layer getLayer(int index) {
        return layers.get(index);
    }
    
    public void removeLayer(int index) {
        layers.removeIndex(index);
    }
    
    public void removeLayer(Layer layer) {
        layers.removeValue(layer, false);
    }
    
    public static void setCamera(GameCamera camera) {
        LayerManager.camera = camera;
    }
    
    public void addLayer(Layer layer) {
        layers.add(layer);
    }
}
