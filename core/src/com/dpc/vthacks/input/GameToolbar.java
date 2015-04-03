package com.dpc.vthacks.input;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import java.sql.Time;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.dpc.vthacks.AndroidCamera;
import com.dpc.vthacks.Bank;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.screens.GameScreen;
import com.dpc.vthacks.weapons.Weapon;

public class GameToolbar {
    private Stage stage;
    private Image healthBarBackground, healthBar, playerIcon;
    private Image gunIcon;
    private Touchpad joystick;
    private Label remainingTime;
    private Label moneyLabel, healthLabel, ammoLabel, waveLabel;
    private int money;
    private static final int PADDING = 5;
    private GameScreen parent;
    private Label moneyToast;
    private final int KS_INTERVAL = 3; // If a sequential kill isn't done within this time, killstreak over
    private float killStreakTimer;
    private boolean killStreak, killCompleted;
    private int killstreakAmount;
    private TextureRegionDrawable gunIconDrawable; // Drawable for the gun icon
    private boolean active = true; // Is the stage recieving input events ?
    private boolean transitionDone = false; // Is the stage done fading out ?
    private static float cameraX = 0;
    private static float cameraY = 0;
    
    private Action moveGunComps, swap;
    
    public GameToolbar(final GameScreen parent, boolean campaignMode) {
        this.parent = parent;

        Skin skin = new Skin();
        skin.addRegions(Assets.skinAtlas);
        
        TouchpadStyle touchpadStyle = new TouchpadStyle();
        Drawable touchBackground = skin.getDrawable("touchBackground");
        Drawable touchKnob = skin.getDrawable("touchKnob");
        
        touchKnob.setMinWidth(AppData.TARGET_WIDTH / 25);
        touchKnob.setMinHeight(AppData.TARGET_WIDTH / 25);
        
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        
        joystick = new Touchpad(10, touchpadStyle);
        joystick.setBounds(15, 15, AppData.TARGET_WIDTH / 8, AppData.TARGET_WIDTH / 8);
        
        joystick.getColor().a = 0.75f;
        
        moneyToast = new Label("", Assets.aerialLabelStyle);
        moneyLabel = new Label("Money: 0", Assets.aerialLabelStyle);
        healthLabel = new Label("Health: 100", Assets.aerialLabelStyle);
        
        
        moneyToast.setColor(new Color(0, 0.5f, 0, 1));
        
        ammoLabel = new Label("", Assets.aerialLabelStyle);   

        
        moneyToast = new Label("", Assets.aerialLabelStyle);

        playerIcon = new Image(Assets.playerIcon);
        playerIcon.setSize(Assets.playerIcon.getRegionWidth() * 10,
                           Assets.playerIcon.getRegionHeight() * 10);

        playerIcon.setPosition(PADDING, (AppData.TARGET_HEIGHT - playerIcon.getHeight()) - PADDING);
        
        float h = (playerIcon.getHeight() * 0.5f);
        

        healthBarBackground = new Image(Assets.healthBarBackground);
        healthBarBackground.setPosition(playerIcon.getX() + playerIcon.getWidth(), 
                                        (playerIcon.getY() + h) - (healthBarBackground.getHeight() * 0.5f));
        
        healthBar = new Image(Assets.healthbar);
        healthBar.setSize(Assets.healthBarBackground.getRegionWidth() - (Assets.healthBarBackground.getRegionHeight() * 0.135f * 2) ,
                          Assets.healthBarBackground.getRegionHeight() * 0.73f);

        healthBar.setPosition(healthBarBackground.getX() + (Assets.healthBarBackground.getRegionHeight() * 0.135f),
                              healthBarBackground.getY() + (Assets.healthBarBackground.getRegionHeight() * 0.135f));
        
        stage = new Stage();
        
        cameraX = AppData.TARGET_WIDTH * 0.5f;
        cameraY = AppData.TARGET_HEIGHT * 0.5f;
        
        stage.getViewport().setCamera(new AndroidCamera(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT, cameraX, cameraY));
        
        joystick.setPosition(PADDING, PADDING);
        addMoney(0);

        moneyToast.setColor(1, 1, 1, 1);

        
        moneyLabel.setColor(new Color(0,   
                                      0.4f,
                                      0, 1));
        
        if(!campaignMode) {
            waveLabel = new Label("Wave 1", Assets.aerialLabelStyle);
            
            waveLabel.setPosition(healthBar.getX() + healthBar.getWidth() + (PADDING * 3), 
                                   AppData.TARGET_HEIGHT);
            
            waveLabel.addAction(moveTo(waveLabel.getX(), healthBar.getY(), 0.25f));
            
           
            stage.addActor(waveLabel);
        }
        else {
            remainingTime = new Label("Ready!", Assets.aerialLabelStyle);
            
            remainingTime.setPosition(healthBar.getX() + healthBar.getWidth() + (PADDING * 3), 
                                   AppData.TARGET_HEIGHT);
            
            remainingTime.addAction(sequence(moveTo(remainingTime.getX(), healthBar.getY(), 0.25f),
                                             delay(1),
                                             new Action() {

                                                @Override
                                                public boolean act(float delta) {
                                                    remainingTime.setText("Set!");
                                                    return true;
                                                }
                
                                             },
                                             delay(1),
                                             new Action() {
                                                @Override
                                                public boolean act(float delta) {
                                                    remainingTime.setText("Go!");
                                                    
                                                    return true;
                                                }
                                
                                             },
                                             delay(0.5f),
                                             new Action() {
                                                 
                                                 @Override
                                                public boolean act(float delta) {
                                                    parent.getLevel().setEnabled(true);   
                                                    return true;
                                                }
                                                 
                                             }));
            
            
            stage.addActor(remainingTime);
        }
        
        stage.addActor(joystick);
        stage.addActor(playerIcon);
        stage.addActor(healthBarBackground);
        stage.addActor(moneyToast);
        stage.addActor(moneyLabel);
        stage.addActor(healthBar);
        stage.addActor(ammoLabel);
        
       
        moveGunComps = new Action() {

            @Override
            public boolean act(float delta) {
                gunIcon.addAction(moveTo(PADDING * 2, gunIcon.getY(), 0.1f));
                
                ammoLabel.addAction(fadeIn(0));
                ammoLabel.addAction(moveTo(PADDING * 2 + gunIcon.getWidth(), gunIcon.getY(), 0.1f));
                
                return true;
            }
            
        };
        
        // Action to swap guns
        swap = new Action() {
            
            @Override
            public boolean act(float delta) {
             // Set the new drawable
                gunIconDrawable.setRegion(Assets.weaponIconAtlas
                        .findRegion(parent.getLevel().getPlayer()
                                .getCurrentWeapon().getIconPath()));
           
                gunIcon.setDrawable(gunIconDrawable);
                
                gunIcon.setWidth(gunIconDrawable.getRegion().getRegionWidth());
                gunIcon.setHeight(gunIconDrawable.getRegion().getRegionHeight());
                
                // Update the ammo text
                setAmmo(parent.getLevel().getPlayer().getCurrentWeapon().getAmmo());
                
                return true;
            }
            
        };
    }
   
    public void update(float delta) {
        stage.act(delta);
       
       // Transition is complete
       if(transitionDone) {
           // Notify the level
           parent.getLevel().openGameOverDialog();
           
           transitionDone = false;
       }
       
       if(killStreak) {
           killStreakTimer += delta;
           
           if(killStreakTimer >= KS_INTERVAL) {
               if(!killCompleted) {
                   killStreakTimer = 0;
                   killCompleted = false;
                   killstreakAmount = 0;
                   killStreak = false;
                   moneyToast.getColor().set(1, 1, 1, 1);
                   
                   // Move off screen
                   moneyToast.addAction(parallel(
                                           fadeOut(0.25f),
                                           moveTo(AppData.TARGET_WIDTH + (PADDING * 2), 
                                                   moneyToast.getY(), 0.25f)));
               }
           }
       }
    }
    
    public void draw() {
        stage.draw();
    }
    
    public void onResize(int w, int h) {
        stage.getViewport().setCamera(new AndroidCamera(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT, cameraX, cameraY));
    }
    
    public void dispose() {
        stage.dispose();
    }
    
    public Stage getStage() {
        return stage;
    }
    
    public void setAmmo(int ammo) {
        ammoLabel.setText(parent.getLevel().getPlayer().getCurrentWeapon().getAmmo() + " / " +
                          parent.getLevel().getPlayer().getCurrentWeapon().getMaxAmmo());
        
        ammoLabel.addAction(repeat(2, parallel(sequence(moveBy(2, 0, 0.05f), moveBy(-2, 0, 0.05f)),
                                               sequence(moveBy(0, 2, 0.05f), moveBy(0, -2, 0.05f)))));
        
    //    shakeAmmo();
    }
    
    private void updateMoneyToast(int am) {    
        if(am > 0) {
            killStreak = true;
            killStreakTimer = 0;
            killstreakAmount += am;
            moneyToast.setText("+" + killstreakAmount);
            moneyToast.getColor().g -= 0.1f;
            moneyToast.getColor().b -= 0.1f;
        }
        
        float x = moneyLabel.getX();
        float y = moneyLabel.getY() - (lh(moneyToast) * 1.5f);
        
        moneyToast.setPosition(x, y);
     
        if(killStreak) {
            moneyToast.addAction(
                    sequence(     
                            parallel(sequence(
                                    moveBy(10, 0,0.02f),
                                    moveBy(-10, 0,0.02f),
                                    moveBy(0, 10,0.02f),
                                    moveBy(0, -10,0.02f),
                    fadeIn(0.5f)))));
        }
    }
    
    public void addMoney(int am) {
        this.money += am;

        // Deposit money into the player's bank
        Bank.deposit(am);
        
        moneyLabel.setText("$" + money);
        
        float t = (moneyLabel.getStyle().font.getBounds(moneyLabel.getText()).height * 2);
        
        // Reposition the money text
        moneyLabel.setPosition(AppData.TARGET_WIDTH - (moneyLabel.getStyle().font.getBounds(moneyLabel.getText()).width) - (t * 0.5f), 
                               AppData.TARGET_HEIGHT - t);
     
        moneyLabel.addAction(sequence(
                moveBy(30, 0,0.02f),
                moveBy(-30, 0,0.02f),
                moveBy(0, 10,0.02f),
                moveBy(0, -10,0.02f)));
        
        updateMoneyToast(am);
    }
    
    public void shakeAmmo() {
        ammoLabel.addAction(repeat(2, sequence(
                moveBy(20, 0, 0.1f),
                moveBy(-20, 0, 0.1f))));
    }
    
    public Touchpad getJoystick() {
        return joystick;
    }
   
    private float lh(Label l) {
        return l.getStyle().font.getBounds(l.getText()).height;
    }
    
    private float lw(Label l) {
        return l.getStyle().font.getBounds(l.getText()).width;
    }
    
    public void setWave(int wave) {
        float oldY = waveLabel.getY();
        
        waveLabel.addAction(sequence(moveTo(waveLabel.getX(), AppData.TARGET_HEIGHT + lh(waveLabel), 0.1f),
                                     moveTo(waveLabel.getX(), oldY, 0.1f),
                                     repeat(5, sequence(moveBy(5, 0, 0.05f), moveBy(-5, 0, 0.05f)))));
        waveLabel.addAction(repeat(5, sequence(alpha(0.5f, 0.1f), alpha(1, 0.1f))));
        
        waveLabel.setText("Wave " + wave);
    }
    
    public void setGunIcon(Weapon gun) {
        if(gunIcon == null) {
            gunIcon = new Image(Assets.weaponIconAtlas.findRegion(gun.getIconPath()));

            gunIconDrawable = new TextureRegionDrawable(
                    Assets.weaponIconAtlas.findRegion(parent.getLevel()
                            .getPlayer().getCurrentWeapon()
                            .getIconPath())); 
                            
            gunIcon.addListener(new InputListener() {
                
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    // Swap weapons
                    parent.getLevel().getPlayer().swapWeapon();

                    // Hide the ammo label
                    ammoLabel.addAction(fadeOut(0));
                    
                    gunIcon.addAction(sequence(
                                        moveTo(-gunIcon.getWidth(), gunIcon.getY(), 0.1f),
                                        swap,
                                        moveGunComps));

                    return true;
                }
            });
            
            
            stage.addActor(gunIcon);
        }
        
        // Position directly under player icon
        gunIcon.setPosition(playerIcon.getX(), 
                            playerIcon.getY() - (gunIcon.getHeight() * 2.85f));
        
        // Position ammo label to the right of the gun icon
        ammoLabel.setPosition(gunIcon.getX() + gunIcon.getWidth() + PADDING, 
                              gunIcon.getY());
    }
    
    public void setHealth(float f) {
        if(Float.isInfinite(f) || Float.isNaN(f) || f < 0) {
            return;
        }
   
        healthLabel.setText("Health: " + f);

        healthBar.clearActions();
        
        // Calculate the exp bar width
        healthBar.addAction(parallel(scaleTo((f / parent.getLevel().getPlayer().getProperties().getMaxHealth()),
                1, 0.15f), sequence(alpha(0.5f, 0.075f), alpha(1, 0.075f))));
        
        
        healthLabel.setPosition((AppData.TARGET_WIDTH - healthLabel.getWidth() * 4), healthLabel.getY());
    }
    
    public void setActive(boolean active) {
        this.active = active;

        if(!active) {
            Action clear = new Action(){
                public boolean act( float delta ) { 
                    transitionDone = true;
                    return true;
                }
            };
            
            stage.addAction(sequence(fadeOut(1), clear));
        }
        else {
            stage.addAction(fadeIn(1));
        }
    }
    
    public void setRemainingTime(int time) {
        // Parse from seconds to a displayable time
        remainingTime.setText(String.format("%02d:%02d", time / 60, time % 60));
    }
    
    public boolean isActive() {
        return active;
    }

    public void setMoney(int i) {
        moneyLabel.setText("$" + i);
        money = 0;
    }
    
}
