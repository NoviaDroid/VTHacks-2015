package com.dpc.vthacks.input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.Bank;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.data.Fonts;
import com.dpc.vthacks.objects.Weapon;
import com.dpc.vthacks.screens.GameScreen;

public class GameToolbar {
    private Stage stage;
    private static Sprite leftBg, healthBarBackground, healthBar, playerIcon, gunIcon;
    private Touchpad joystick;
    private Drawable background;
    private Button bombButton, strafeButton, soldierButton, tankButton, towerButton,
                   tankUpgradeButton, towerUpgradeButton, soldierUpgradeButton;
    private static Label moneyLabel, experienceLabel, healthLabel, ammoLabel;
    private int money;
    private final Color BATCH_COLOR;
    private static final int PADDING = 5;
    private GameScreen parent;
    private Label moneyToast;
    private final int KS_INTERVAL = 3; // If a sequential kill isn't done within this time, killstreak over
    private float killStreakTimer;
    private boolean killStreak, killCompleted;
    private int killstreakAmount;
    
    public GameToolbar(GameScreen parent) {
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

        playerIcon = new Sprite(Assets.playerIcon);
        playerIcon.setSize(Assets.playerIcon.getRegionWidth() * 10,
                           Assets.playerIcon.getRegionHeight() * 10);

        playerIcon.setPosition(PADDING, (AppData.height - playerIcon.getHeight()) - PADDING);
        
        float h = (playerIcon.getHeight() * 0.5f);
        

        healthBarBackground = new Sprite(Assets.healthBarBackground);
        healthBarBackground.setPosition(playerIcon.getX() + playerIcon.getWidth(), 
                                        (playerIcon.getY() + h) - (healthBarBackground.getHeight() * 0.5f));
        
        healthBar = new Sprite(Assets.healthbar);
        healthBar.setSize(Assets.healthBarBackground.getRegionWidth() - (Assets.healthBarBackground.getRegionHeight() * 0.135f * 2) ,
                          Assets.healthBarBackground.getRegionHeight() * 0.73f);
        
        healthBar.setPosition(healthBarBackground.getX() + (Assets.healthBarBackground.getRegionHeight() * 0.135f),
                              healthBarBackground.getY() + (Assets.healthBarBackground.getRegionHeight() * 0.135f));
        
//        experienceBar = new Sprite(Assets.healthbar);
//        experienceBar.setSize(Assets.healthbar.getRegionWidth(), h);
//        experienceBar.setPosition(healthBar.getX(), playerIcon.getY());
        
        experienceLabel.setPosition(soldierUpgradeButton.getX() + soldierUpgradeButton.getWidth() + PADDING, getTop() - experienceLabel.getHeight());
        
        
        stage = new Stage(new StretchViewport(AppData.width, AppData.height)) {
            
            @Override
            public void draw() {
                super.draw();
       
                //experienceLabel.setPosition(experienceBar.getX() + experienceBar.getWidth() + PADDING, experienceLabel.getY());
               
                //healthLabel.setPosition(healthBar.getX() + healthBar.getWidth() + PADDING, healthLabel.getY());
                
                getBatch().setProjectionMatrix(getCamera().combined);
                getBatch().setColor(BATCH_COLOR.r, BATCH_COLOR.g, BATCH_COLOR.b, 1);
                getBatch().begin();
                
                playerIcon.draw(getBatch());
                gunIcon.draw(getBatch());
                healthBarBackground.draw(getBatch());
                healthBar.draw(getBatch());

                getBatch().setColor(Color.GRAY);
                //experienceBar.draw(getBatch());
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
        
        
        
        //healthBar.setWidth(AppData.width - (soldierUpgradeButton.getX() + soldierUpgradeButton.getWidth() + PADDING));
       
        stage.addActor(joystick);
//        stage.addActor(bombButton);
//        stage.addActor(strafeButton);
  //      stage.addActor(tankButton);
  //      stage.addActor(soldierButton);
        stage.addActor(moneyToast);
//        stage.addActor(tankUpgradeButton);
//        stage.addActor(towerUpgradeButton);
//        stage.addActor(soldierUpgradeButton);
        stage.addActor(moneyLabel);
        stage.addActor(ammoLabel);
//          stage.addActor(experienceLabel);
//          stage.addActor(healthLabel);
//          stage.addActor(playerIcon);
        
        BATCH_COLOR = stage.getBatch().getColor();
    }
   
    public void update(float delta) {
       stage.act(delta);
      
       if(killStreak) {
           killStreakTimer += delta;
           
           if(killStreakTimer >= KS_INTERVAL) {
               if(!killCompleted) {
                   killStreakTimer = 0;
                   killCompleted = false;
                   killstreakAmount = 0;
                   killStreak = false;
                   
                   // Move off screen
                   moneyToast.addAction(Actions.parallel(
                                           Actions.fadeOut(0.25f),
                                           Actions.moveTo(AppData.width + (PADDING * 2), 
                                                   moneyToast.getY(), 0.25f)));
               }
           }
       }
       else {
           
       }
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
                    Actions.sequence(     
                            Actions.parallel(Actions.sequence(
                                    Actions.moveBy(10, 0,0.02f),
                                    Actions.moveBy(-10, 0,0.02f),
                                    Actions.moveBy(0, 10,0.02f),
                                    Actions.moveBy(0, -10,0.02f),
                    Actions.fadeIn(0.5f)))));
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
     
        moneyLabel.addAction(Actions.sequence(
                Actions.moveBy(10, 0,0.02f),
                Actions.moveBy(-10, 0,0.02f),
                Actions.moveBy(0, 10,0.02f),
                Actions.moveBy(0, -10,0.02f)));
        
        updateMoneyToast(am);
    }
    
    public void shakeAmmo() {
        ammoLabel.addAction(Actions.repeat(2, Actions.sequence(
                Actions.moveBy(20, 0, 0.1f),
                Actions.moveBy(-20, 0, 0.1f))));
    }
    
    public Touchpad getJoystick() {
        return joystick;
    }
    
    public void setGunIcon(Weapon gun) {
        if(gunIcon == null) {
            gunIcon = new Sprite(Assets.weaponIconAtlas.findRegion(gun.getIconPath()));
        }
        
        // Position directly under player icon
        gunIcon.setPosition(playerIcon.getX(), 
                            playerIcon.getY() - (gunIcon.getHeight() * 2));
        
        // Position ammo label to the right of the gun icon
        ammoLabel.setPosition(gunIcon.getX() + gunIcon.getWidth() + PADDING, 
                              gunIcon.getY());
    }
    
    public void setHealth(float f) {
        healthLabel.setText("Health: " + f);
System.err.println(healthBar.getWidth() * (f / 100));
        // Calculate the exp bar width
        healthBar.setSize(healthBar.getWidth() * (f / 100),
                          healthBar.getHeight());
        
        healthLabel.setPosition((AppData.width - healthLabel.getWidth() * 4), healthLabel.getY());
    }
}
