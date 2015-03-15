package com.dpc.vthacks.input;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.Bank;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.data.Fonts;
import com.dpc.vthacks.objects.Weapon;
import com.dpc.vthacks.screens.GameScreen;

public class GameToolbar {
    private Stage stage;
    private Image leftBg, healthBarBackground, healthBar, playerIcon;
    private Image gunIcon;
    private Touchpad joystick;
    private Drawable background;
    private Button bombButton, strafeButton, soldierButton, tankButton, towerButton,
                   tankUpgradeButton, towerUpgradeButton, soldierUpgradeButton;
    private Label moneyLabel, experienceLabel, healthLabel, ammoLabel;
    private int money;
    private final Color BATCH_COLOR;
    private static final int PADDING = 5;
    private GameScreen parent;
    private Label moneyToast;
    private final int KS_INTERVAL = 3; // If a sequential kill isn't done within this time, killstreak over
    private float killStreakTimer;
    private boolean killStreak, killCompleted;
    private int killstreakAmount;
    private TextureRegionDrawable gunIconDrawable; // Drawable for the gun icon
    private float origHealthBarWidth; // Orig width of the health bar
    private boolean active = true; // Is the stage recieving input events ?
    private boolean transitionDone = false; // Is the stage done fading out ?
    
    public GameToolbar(final GameScreen parent) {
        this.parent = parent;
        
        Assets.loadSkins();
        
        Skin skin = new Skin();
        skin.addRegions(Assets.skinAtlas);
        
        background = skin.getDrawable("Bomb Icon");
        

        bombButton = new ImageButton(skin.getDrawable("Bomb Icon"), skin.getDrawable("Bomb Icon Hover"));   
        strafeButton = new ImageButton(skin.getDrawable("Bomb Icon"), skin.getDrawable("Bomb Icon Hover"));
        soldierButton = new ImageButton(skin.getDrawable("Troop Button"), skin.getDrawable("Troop Button Hover"));
        towerButton = new ImageButton(skin.getDrawable("Bomb Icon"));
        tankButton = new ImageButton(skin.getDrawable("Tank Button"), skin.getDrawable("Tank Button Hover"));
        tankUpgradeButton = new ImageButton(skin.getDrawable("Tank Button +"), skin.getDrawable("Tank Button +Hover"));
        towerUpgradeButton = new ImageButton(skin.getDrawable("Bomb Icon"));
        soldierUpgradeButton = new ImageButton(skin.getDrawable("Soldier Button +"), skin.getDrawable("Soldier Button + Hover"));
        
        
        TouchpadStyle touchpadStyle = new TouchpadStyle();
        Drawable touchBackground = skin.getDrawable("touchBackground");
        Drawable touchKnob = skin.getDrawable("touchKnob");
        
        touchKnob.setMinWidth(AppData.width / 25);
        touchKnob.setMinHeight(AppData.width / 25);
        
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        
        joystick = new Touchpad(10, touchpadStyle);
        joystick.setBounds(15, 15, AppData.width / 8, AppData.width / 8);
        
        joystick.getColor().a = 0.75f;
        soldierUpgradeButton.getColor().a = 0.75f;
        towerUpgradeButton.getColor().a = 0.75f;
        tankUpgradeButton.getColor().a = 0.75f;
        towerButton.getColor().a = 0.75f;
        soldierButton.getColor().a = 0.75f;
        strafeButton.getColor().a = 0.75f;
        tankButton.getColor().a = 0.75f;
        bombButton.getColor().a = 0.75f;
        
        LabelStyle style = new LabelStyle();
        style.font = Fonts.getZombie();
        
        moneyToast = new Label("", style);
        moneyLabel = new Label("Money: 0", style);
        experienceLabel = new Label("Experience: ", style);
        healthLabel = new Label("Health: 100", style);
        
        moneyToast.setColor(new Color(0, 0.5f, 0, 1));
        
        style = new LabelStyle();
        style.font = Fonts.getZombieSmall();
        
        ammoLabel = new Label("", style);   
        
        style = new LabelStyle();
        style.font = Fonts.getZombie();

        style = new LabelStyle();
        style.font = Fonts.getVisitor();
        
        moneyToast = new Label("", style);
        
        addButtonListeners();

        playerIcon = new Image(Assets.playerIcon);
        playerIcon.setSize(Assets.playerIcon.getRegionWidth() * 10,
                           Assets.playerIcon.getRegionHeight() * 10);

        playerIcon.setPosition(PADDING, (AppData.height - playerIcon.getHeight()) - PADDING);
        
        float h = (playerIcon.getHeight() * 0.5f);
        

        healthBarBackground = new Image(Assets.healthBarBackground);
        healthBarBackground.setPosition(playerIcon.getX() + playerIcon.getWidth(), 
                                        (playerIcon.getY() + h) - (healthBarBackground.getHeight() * 0.5f));
        
        healthBar = new Image(Assets.healthbar);
        healthBar.setSize(Assets.healthBarBackground.getRegionWidth() - (Assets.healthBarBackground.getRegionHeight() * 0.135f * 2) ,
                          Assets.healthBarBackground.getRegionHeight() * 0.73f);
        
        origHealthBarWidth = healthBar.getWidth();
        
        healthBar.setPosition(healthBarBackground.getX() + (Assets.healthBarBackground.getRegionHeight() * 0.135f),
                              healthBarBackground.getY() + (Assets.healthBarBackground.getRegionHeight() * 0.135f));
        
        experienceLabel.setPosition(soldierUpgradeButton.getX() + soldierUpgradeButton.getWidth() + PADDING, getTop() - experienceLabel.getHeight());
        
        
        stage = new Stage(new StretchViewport(AppData.width, AppData.height));

        joystick.setPosition(PADDING, PADDING);
        bombButton.setPosition(AppData.width - bombButton.getWidth(), PADDING);
        strafeButton.setPosition(bombButton.getX() - strafeButton.getWidth() - PADDING, PADDING);
        tankButton.setPosition(strafeButton.getX() - tankButton.getWidth() - PADDING, PADDING);
        soldierButton.setPosition(tankButton.getX() - soldierButton.getWidth() - PADDING, PADDING);
        tankUpgradeButton.setPosition(soldierButton.getX() - tankUpgradeButton.getWidth() - PADDING, PADDING);
        towerUpgradeButton.setPosition(tankUpgradeButton.getX() - towerUpgradeButton.getWidth() - PADDING, PADDING);
        soldierUpgradeButton.setPosition(towerUpgradeButton.getX() - soldierUpgradeButton.getWidth() - PADDING, PADDING);
        healthLabel.setPosition(soldierUpgradeButton.getX() + soldierUpgradeButton.getWidth() + PADDING, experienceLabel.getY() - healthLabel.getHeight());
        addMoney(0);

        moneyToast.setColor(1, 1, 1, 1);

        
        moneyLabel.setColor(new Color(0,   
                                      0.4f,
                                      0, 1));
        
        stage.addActor(joystick);
        stage.addActor(playerIcon);
        stage.addActor(healthBarBackground);
        stage.addActor(moneyToast);
        stage.addActor(moneyLabel);
        stage.addActor(healthBar);
        stage.addActor(ammoLabel);
        
        
        BATCH_COLOR = stage.getBatch().getColor();
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
                   
                   // Move off screen
                   moneyToast.addAction(parallel(
                                           fadeOut(0.25f),
                                           moveTo(AppData.width + (PADDING * 2), 
                                                   moneyToast.getY(), 0.25f)));
               }
           }
       }
       else {
           
       }
    }
    
    private void addButtonListeners() {
        soldierUpgradeButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                soldierUpgradeButtonTouchDown();
                Assets.playPressDown();
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Assets.playPressUp();
            }
        });
        
        towerUpgradeButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                towerUpgradeButtonTouchedDown();
                Assets.playPressDown();
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Assets.playPressUp();
            }
        });
        
        tankUpgradeButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                tankUpgradeButtonTouchDown();
                Assets.playPressDown();
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Assets.playPressUp();
            }
        });
        
        tankButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                tankButtonTouchDown();
                Assets.playPressDown();
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Assets.playPressUp();
            }
        });
        
        towerButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                towerButtonTouchDown();
                Assets.playPressDown();
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Assets.playPressUp();
            }
        });
        
        soldierButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                soldierButtonTouchDown();
                Assets.playPressDown();
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Assets.playPressUp();
            }
        });
        
        bombButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bombButtonTouchDown();
                Assets.playPressDown();
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                bombButtonTouchUp();
                Assets.playPressUp();
            }
        });
        
        strafeButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                strafeButtonTouchDown();
                Assets.playPressDown();
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                strafeButtonTouchUp();
            }
        });
    }
    
    public void strafeButtonTouchUp() {
        Assets.playPressUp();
    }
    
    public void towerButtonTouchDown() {
    }

    public void towerUpgradeButtonTouchedDown() {
    }
    
    public void soldierUpgradeButtonTouchDown() {
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
    
    public void setAmmo(int ammo) {
        ammoLabel.setText(parent.getLevel().getPlayer().getCurrentWeapon().getAmmo() + " / " +
                          parent.getLevel().getPlayer().getCurrentWeapon().getMaxAmmo());
        
    //    shakeAmmo();
    }
    
    private void updateMoneyToast(int am) {    
        if(am > 0) {
            killStreak = true;
            killStreakTimer = 0;
            killstreakAmount += am;
            moneyToast.setText("+" + killstreakAmount);
        }
        
        float x = moneyLabel.getX();
        float y = moneyLabel.getY() - moneyToast.getStyle().font.getBounds(moneyToast.getText()).height;
        
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
        
        float t = (Fonts.getZombie().getBounds(moneyLabel.getText()).height * 2);
        
        // Reposition the money text
        moneyLabel.setPosition(AppData.width - (Fonts.getZombie().getBounds(moneyLabel.getText()).width) - (t * 0.5f), 
                               AppData.height - t);
     
        moneyLabel.addAction(sequence(
                moveBy(10, 0,0.02f),
                moveBy(-10, 0,0.02f),
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

                    // Set the new drawable
                    gunIconDrawable.setRegion(Assets.weaponIconAtlas
                            .findRegion(parent.getLevel().getPlayer()
                                    .getCurrentWeapon().getIconPath()));
               
                    gunIcon.setDrawable(gunIconDrawable);
                    
                    gunIcon.setWidth(gunIconDrawable.getRegion().getRegionWidth());
                    gunIcon.setHeight(gunIconDrawable.getRegion().getRegionHeight());
                    
                    // Update the ammo text
                    setAmmo(parent.getLevel().getPlayer().getCurrentWeapon().getAmmo());
                    
                    ammoLabel.setX(gunIcon.getX() + gunIcon.getWidth());
                    ammoLabel.setY(gunIcon.getY());
                    
                    return true;
                }
            });
            
            
            stage.addActor(gunIcon);
        }
        
        // Position directly under player icon
        gunIcon.setPosition(playerIcon.getX(), 
                            playerIcon.getY() - (gunIcon.getHeight() * 2));
        
        // Position ammo label to the right of the gun icon
        ammoLabel.setPosition(gunIcon.getX() + gunIcon.getWidth() + PADDING, 
                              gunIcon.getY());
    }
    
    public void setHealth(float f) {
        if(Float.isInfinite(f) || Float.isNaN(f) || f < 0) {
            return;
        }
   
        healthLabel.setText("Health: " + f);

        // Calculate the exp bar width
        healthBar.setSize(origHealthBarWidth * (f / parent.getLevel().getPlayer().getProperties().getMaxHealth()),
                          healthBar.getHeight());
        
        healthLabel.setPosition((AppData.width - healthLabel.getWidth() * 4), healthLabel.getY());
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
    
    public boolean isActive() {
        return active;
    }

    public void setMoney(int i) {
        moneyLabel.setText("$" + i);
    }
}
