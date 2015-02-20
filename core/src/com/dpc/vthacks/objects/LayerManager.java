package com.dpc.vthacks.objects;

import com.badlogic.gdx.utils.Array;
import com.dpc.vthacks.gameobject.GameObject;


public class LayerManager {
    private Array<Layer> layers;
    
    public static class Layer {
        private Array<GameObject> objects;
        private boolean scrolling;
        private float scrollX, scrollY;
        
        public Layer() {
            objects = new Array<GameObject>();
        }
        
        public Layer(float scrollX, float scrollY, boolean scrolling) {
            this.scrollX = scrollX;
            this.scrolling = scrolling;
            this.scrollY = scrollY;
        }
        
        public void render() {
            for(GameObject o : objects) {
                o.render();
            }
        }
        
        public void addObjects(Array<GameObject> go) {
            for(GameObject o : go) {
                objects.add(o);
            }
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
            //layer.setPosition(layer.getX() + layer.scrollX, layer.getY() + layer.scrollY);
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
    
    public void addLayer(Layer layer) {
        layers.add(layer);
    }
}
