package com.dpc.vthacks.input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;

public class GameToolbar {
    private Stage stage;
    private Actor healthBar, experienceBar;
    private Drawable background;
    private Button bombButton, strafeButton, soldierButton, tankButton, towerButton,
                   tankUpgradeButton, towerUpgradeButton, soldierUpgradeButton;
    
    private final Color BATCH_COLOR;
    
    public GameToolbar() {
        Assets.loadSkins();
        
        Skin skin = new Skin();
        skin.addRegions(Assets.getSkins());
        
        background = skin.getDrawable("Bomb Icon");
        
        bombButton = new ImageButton(skin.getDrawable("Bomb Icon"), skin.getDrawable("Bomb Icon Hover"));   
        strafeButton = new ImageButton(skin.getDrawable("Bomb Icon"));
        soldierButton = new ImageButton(skin.getDrawable("Troop Button"), skin.getDrawable("Troop Button Hover"));
        towerButton = new ImageButton(skin.getDrawable("Bomb Icon"));
        tankButton = new ImageButton(skin.getDrawable("Tank Button"), skin.getDrawable("Tank Button Hover"));
        tankUpgradeButton = new ImageButton(skin.getDrawable("Tank Button +"), skin.getDrawable("Tank Button +Hover"));
        towerUpgradeButton = new ImageButton(skin.getDrawable("Bomb Icon"));
        soldierUpgradeButton = new ImageButton(skin.getDrawable("Soldier Button +"), skin.getDrawable("Soldier Button + Hover"));
        
        soldierUpgradeButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                soldierUpgradeButtonTouchDown();
                return true;
            }
        });
        
        towerUpgradeButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                towerUpgradeButtonTouchedDown();
                return true;
            }
            
        });
        
        tankUpgradeButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                tankUpgradeButtonTouchDown();
                return true;
            }
        });
        
        tankButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                tankButtonTouchDown();
                return true;
            }
        });
        
        towerButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                towerButtonTouchDown();
                return true;
            }
        });
        
        soldierButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                soldierButtonTouchDown();
                return true;
            }
        });
        
        bombButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bombButtonTouchDown();
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                bombButtonTouchUp();
            }
        });
        
        strafeButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                strafeButtonTouchDown();
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                strafeButtonTouchUp();
            }
        });

        healthBar = new Actor();
        healthBar.setWidth(Assets.healthbar.getRegionWidth());
        healthBar.setHeight(Assets.healthbar.getRegionHeight());
        healthBar.setBounds(0, 0, healthBar.getWidth(), healthBar.getHeight());
        
        experienceBar = new Actor();
        experienceBar.setWidth(Assets.healthbar.getRegionWidth());
        experienceBar.setHeight(Assets.healthbar.getRegionHeight());
        experienceBar.setBounds(0, 0, experienceBar.getWidth(), experienceBar.getHeight());
        
        stage = new Stage(new StretchViewport(AppData.width, AppData.height)) {
            @Override
            public void draw() {
                super.draw();
                
                getBatch().setProjectionMatrix(getCamera().combined);
                getBatch().begin();
                getBatch().draw(Assets.healthbar, healthBar.getX(), healthBar.getY(), healthBar.getWidth(), healthBar.getHeight());

                getBatch().setColor(Color.GRAY);
                getBatch().draw(Assets.healthbar, experienceBar.getX(), experienceBar.getY(), experienceBar.getWidth(), experienceBar.getHeight());
                getBatch().setColor(BATCH_COLOR);
                
                getBatch().end();
            }
        };

        // hGroup.addActor(soldierButton);
        // hGroup.addActor(tankButton);
        // hGroup.addActor(tankUpgradeButton);
        // hGroup.addActor(towerUpgradeButton);
        // hGroup.addActor(soldierUpgradeButton);
        // hGroup.addActor(vGroup);

        int padding = 5;
        
        bombButton.setPosition(0, padding);
        strafeButton.setPosition(bombButton.getWidth() + padding, padding);
        tankButton.setPosition(strafeButton.getWidth() + padding, padding);
        soldierButton.setPosition(tankButton.getX() + tankButton.getWidth() + padding, padding);
        tankUpgradeButton.setPosition(soldierButton.getX() + soldierButton.getWidth() + padding, padding);
        towerUpgradeButton.setPosition(tankUpgradeButton.getX() + tankUpgradeButton.getWidth() + padding, padding);
        soldierUpgradeButton.setPosition(towerUpgradeButton.getX() , padding);
        healthBar.setPosition(soldierUpgradeButton.getX() + soldierUpgradeButton.getWidth() + padding, (70 * 0.5f) - (healthBar.getHeight() * 0.75f) + (healthBar.getHeight() * 0.5f));
        experienceBar.setPosition(healthBar.getX(), healthBar.getY() - (healthBar.getHeight() * 0.75f) - (healthBar.getHeight() * 0.5f));
        
        healthBar.setWidth(AppData.width / 5);
        experienceBar.setWidth(1);
        //healthBar.setWidth(AppData.width - (soldierUpgradeButton.getX() + soldierUpgradeButton.getWidth() + padding));
        
        stage.addActor(bombButton);
        stage.addActor(strafeButton);
        stage.addActor(tankButton);
        stage.addActor(soldierButton);
        stage.addActor(tankUpgradeButton);
        //stage.addActor(towerUpgradeButton);
        stage.addActor(soldierUpgradeButton);
        
        BATCH_COLOR = stage.getBatch().getColor();
    }
    
    protected void tankUpgradeButtonTouchDown() {
    }

    protected void towerButtonTouchDown() {
    }

    protected void towerUpgradeButtonTouchedDown() {
    }

    protected void soldierUpgradeButtonTouchDown() {
    }

    public void draw() {
        //stage.draw();
//        stage.getBatch().begin();
//        background.draw(stage.getBatch(), 0, 0, AppData.width, 70);
//        stage.getBatch().end();
//        
        stage.draw();
    }
    
    public void onResize(int w, int h) {
        stage.getViewport().update(w, h, true);
        ((OrthographicCamera) stage.getCamera()).update();
    }
    
    public void dispose() {
        stage.dispose();
    }

    public void soldierButtonTouchDown() {
        
    }
    
    public void tankButtonTouchDown() {
        
    }
    
    public void bombButtonTouchUp() {
        
    }
    
    public void bombButtonTouchDown() {
        
    }
    
    public void strafeButtonTouchUp() {
        
    }
    
    public void strafeButtonTouchDown() {
        
    }
    
    public Stage getStage() {
        return stage;
    }
}
