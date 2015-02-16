package com.dpc.vthacks.input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.data.Fonts;
import com.dpc.vthacks.data.Sounds;
import com.dpc.vthacks.plane.Plane;
import com.dpc.vthacks.screens.GameScreen;

public class GameToolbar {
    private Stage stage;
    private static Actor healthBar;
    private static Actor experienceBar;
    private Drawable background;
    private Button bombButton, strafeButton, soldierButton, tankButton, towerButton,
                   tankUpgradeButton, towerUpgradeButton, soldierUpgradeButton;
    private static Label moneyLabel, experienceLabel, healthLabel;
    
    private final Color BATCH_COLOR;
    private static final int PADDING = 5;
    
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
        
        LabelStyle style = new LabelStyle();
        style.font = Fonts.getVisitor1();
        
        moneyLabel = new Label("Money: 0", style);
        experienceLabel = new Label("Experience: ", style);
        healthLabel = new Label("Health: 100", style);

        soldierUpgradeButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                soldierUpgradeButtonTouchDown();
                Sounds.playPressDown();
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Sounds.playPressUp();
            }
        });
        
        towerUpgradeButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                towerUpgradeButtonTouchedDown();
                Sounds.playPressDown();
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Sounds.playPressUp();
            }
        });
        
        tankUpgradeButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                tankUpgradeButtonTouchDown();
                Sounds.playPressDown();
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Sounds.playPressUp();
            }
        });
        
        tankButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                tankButtonTouchDown();
                Sounds.playPressDown();
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Sounds.playPressUp();
            }
        });
        
        towerButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                towerButtonTouchDown();
                Sounds.playPressDown();
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Sounds.playPressUp();
            }
        });
        
        soldierButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                soldierButtonTouchDown();
                Sounds.playPressDown();
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Sounds.playPressUp();
            }
        });
        
        bombButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bombButtonTouchDown();
                Sounds.playPressDown();
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                bombButtonTouchUp();
                Sounds.playPressUp();
            }
        });
        
        strafeButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                strafeButtonTouchDown();
                Sounds.playPressDown();
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                strafeButtonTouchUp();
            }
        });

        healthBar = new Actor();
        healthBar.setWidth(Assets.healthbar.getRegionWidth() * 0.5f);
        healthBar.setHeight(Assets.healthbar.getRegionHeight() * 0.5f);
        healthBar.setBounds(0, 0, healthBar.getWidth(), healthBar.getHeight());
        
        experienceBar = new Actor();
        experienceBar.setWidth(0);
        experienceBar.setHeight(Assets.healthbar.getRegionHeight() * 0.5f);
        experienceBar.setBounds(0, 0, experienceBar.getWidth(), experienceBar.getHeight());
        
        stage = new Stage(new StretchViewport(AppData.width, AppData.height)) {
            @Override
            public void draw() {
                super.draw();
       
//                experienceLabel.setPosition(experienceBar.getX() + experienceBar.getWidth() + PADDING, experienceLabel.getY());
//               
//                healthLabel.setPosition(healthBar.getX() + healthBar.getWidth() + PADDING, healthLabel.getY());
                
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

        bombButton.setPosition(0, PADDING);
        strafeButton.setPosition(bombButton.getWidth() + PADDING, PADDING);
        tankButton.setPosition(strafeButton.getWidth() + PADDING, PADDING);
        soldierButton.setPosition(tankButton.getX() + tankButton.getWidth() + PADDING, PADDING);
        tankUpgradeButton.setPosition(soldierButton.getX() + soldierButton.getWidth() + PADDING, PADDING);
        towerUpgradeButton.setPosition(tankUpgradeButton.getX() + tankUpgradeButton.getWidth() + PADDING, PADDING);
        soldierUpgradeButton.setPosition(towerUpgradeButton.getX() , PADDING);
        healthBar.setPosition(soldierUpgradeButton.getX() + soldierUpgradeButton.getWidth() + PADDING, (70 * 0.5f) - (healthBar.getHeight() * 0.75f) + (healthBar.getHeight() * 0.5f));
        experienceBar.setPosition(healthBar.getX(), healthBar.getY() - (healthBar.getHeight() * 0.75f) - (healthBar.getHeight() * 0.5f));
        experienceLabel.setPosition(soldierUpgradeButton.getX() + soldierUpgradeButton.getWidth() + PADDING, getTop() - experienceLabel.getHeight());
        healthLabel.setPosition(soldierUpgradeButton.getX() + soldierUpgradeButton.getWidth() + PADDING, experienceLabel.getY() - healthLabel.getHeight());
        moneyLabel.setPosition(soldierUpgradeButton.getX() + soldierUpgradeButton.getWidth() + PADDING, healthLabel.getY() - moneyLabel.getHeight());

        //healthBar.setWidth(AppData.width - (soldierUpgradeButton.getX() + soldierUpgradeButton.getWidth() + PADDING));
        
        stage.addActor(bombButton);
        stage.addActor(strafeButton);
        stage.addActor(tankButton);
        stage.addActor(soldierButton);
        stage.addActor(tankUpgradeButton);
        //stage.addActor(towerUpgradeButton);
        stage.addActor(soldierUpgradeButton);
        stage.addActor(moneyLabel);
        //stage.addActor(experienceLabel);
        //stage.addActor(healthLabel);
        
        BATCH_COLOR = stage.getBatch().getColor();
    }
   
    protected void strafeButtonTouchUp() {
        
    }
    
    protected void towerButtonTouchDown() {
    }

    protected void towerUpgradeButtonTouchedDown() {
    }
    
    protected void soldierUpgradeButtonTouchDown() {
    }

    public float getTop() {
        if(bombButton.getY() + bombButton.getHeight() >= healthLabel.getY() + healthLabel.getHeight()) {
            return bombButton.getY() + bombButton.getHeight() + PADDING;
        }
        else {
            return experienceLabel.getHeight() + moneyLabel.getHeight() + healthLabel.getHeight();
        }
    }
    
    public void draw() {
        stage.draw();
    }
    
    public void onResize(int w, int h) {
        stage.getViewport().update(w, h, true);
        ((OrthographicCamera) stage.getCamera()).update();
    }
    
    public void dispose() {
        stage.dispose();
    
    }
    
    public void tankUpgradeButtonTouchDown() {
        
    }
    
    public void soldierButtonTouchDown() {
        
    }
    
    public void tankButtonTouchDown() {
        
    }
    
    public void bombButtonTouchUp() {
        
    }
    
    public void bombButtonTouchDown() {
        
    }
   
    public void strafeButtonTouchDown() {
        
    }
    
    public Stage getStage() {
        return stage;
    }
    
    public static void setMoney(int money) {
        moneyLabel.setText("Money: " + money);
    }
    
    public static void setExperience(int exp) {
        experienceLabel.setText("Experience: " + exp);
        
        // Calculate the exp bar width
        experienceBar.setWidth((GameScreen.battle.getPlayer().getExperience() / 
                               (float) GameScreen.battle.getPlayer().getGoalExp()) * 100f);
        
        experienceLabel.setPosition(experienceLabel.getX(), experienceLabel.getY());
    }
    
    public static void setHealth(float f) {
        healthLabel.setText("Health: " + f);
        
        // Calculate the exp bar width
        healthBar.setWidth((GameScreen.battle.getPlayer().getHealth() / 
                               (float) GameScreen.battle.getPlayer().getMaxHealth()) * 100f);
        
        healthLabel.setPosition((AppData.width - healthLabel.getWidth() * 4), healthLabel.getY());
    }
}
