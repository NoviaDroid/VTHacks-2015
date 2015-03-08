package com.dpc.vthacks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.App;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.data.Fonts;
import com.dpc.vthacks.objects.Weapon;
import com.dpc.vthacks.properties.WeaponManager;

public class WeaponSelectionScreen implements Screen {
    private Stage stage;
    private WeaponButton[] selectedWeapons; // The weapons that can be selected. 2 slots.
    private Skin weaponIconSkin;
    private WeaponSelectionDialog dialog;
    private LabelStyle labelStyle;
    private App context;
    private Table masterTable;
    private TextButton backButton;
    private TextButtonStyle buttonStyle;
    private int buttonContext; // Index of which button was pressed
    private static final int PADDING = 5;
    
    private final class WeaponButton {
        private Weapon weapon;
        private ImageButton button;
    }
    
    private final class WeaponSelectionDialog {
        private boolean open;
        private Image[] weaponIcons;
        private Label name;
        private Table table;
        private Weapon selectedWeapon;
        private TextButton okay;
        private Stage dStage;
        
        public WeaponSelectionDialog() {   
            dStage = new Stage(new StretchViewport(800, 480));
            table = new Table();
            
            int rowActors = 3;
            int columnActors = 4;

            okay = new TextButton("okay", buttonStyle);
            okay.setPosition(okay.getWidth() + PADDING, okay.getHeight() + PADDING);

            okay.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    open = false;

                    // Now swap the weapon out for the new one
                    selectedWeapons[buttonContext].weapon = selectedWeapon;
                    
                    // Change the textureregion of the icon
                    ((TextureRegionDrawable) selectedWeapons[buttonContext].button.getImage()
                            .getDrawable()).setRegion(Assets.weaponIconAtlas
                            .findRegion(selectedWeapon.getIconPath()));

                    
                    // Reassign the layout
                    selectedWeapons[buttonContext].button.invalidateHierarchy();
                    masterTable.invalidateHierarchy();
                    
                    Gdx.input.setInputProcessor(stage);
                    
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
            
            // Icon for every unlocked weapon
            weaponIcons = new Image[rowActors * columnActors];
            
            for(int i = 0; i < weaponIcons.length; i++) {
                if(i >= WeaponManager.getUnlockedWeapons().size) {
                    weaponIcons[i] = new Image();
                    continue;
                }
                
                weaponIcons[i] = new Image(new TextureRegionDrawable(
                        Assets.weaponIconAtlas.findRegion(WeaponManager
                                .getUnlockedWeapons().get(i).getIconPath())));
                
                final int dex = i;
                
                weaponIcons[i].addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        selectedWeapon = WeaponManager.getUnlockedWeapons().get(dex);
                        
                        return super.touchDown(event, x, y, pointer, button);
                    }
                });
            }
            
            for(int i = 0; i < rowActors; i++) {
                for(int j = 0; j < columnActors; j++) {
                    Image actor = weaponIcons[(i * columnActors) + j];
                    table.add(actor).width(actor.getWidth()).height(actor.getHeight()).pad(55);
                }
              
                table.row();
            }

            table.add(okay);
            
            table.setFillParent(true);
            
            name = new Label(WeaponManager.getUnlockedWeapons().get(0).getName(),
                             labelStyle);
            
            dStage.addActor(table);
        }

        private void updateAndRender(float delta) {
            dStage.act(delta);
            dStage.draw();
        }
    }

    public WeaponSelectionScreen(App context) {
        this.context = context;
    }
    
    @Override
    public void show() {
        stage = new Stage(new StretchViewport(AppData.width, AppData.height));

        buttonStyle = new TextButtonStyle();
        buttonStyle.font = Fonts.getZombieSmall();
        
        labelStyle = new LabelStyle();
        labelStyle.font = Fonts.getZombie();
        
        backButton = new TextButton("back", buttonStyle);
        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.setScreen(new MenuScreen(context));

                return super.touchDown(event, x, y, pointer, button);
            }
        });
        
        backButton.setPosition(PADDING, 
                               AppData.height - (buttonStyle.font.getBounds("back").height*2));
        
        weaponIconSkin = new Skin(Assets.weaponIconAtlas);
        
        dialog = new WeaponSelectionDialog();
        
        // Primary and secondary
        selectedWeapons = new WeaponButton[2];
        
        masterTable = new Table();
        
        for(int i = 0; i < selectedWeapons.length; i++) {
            
            selectedWeapons[i] = new WeaponButton();
            
            
            // Assign default weapon
            selectedWeapons[i].weapon = WeaponManager.getUnlockedWeapons().get(i);
            
            // Assign the button
            selectedWeapons[i].button = new ImageButton(
                    weaponIconSkin.getDrawable(selectedWeapons[i].weapon.getIconPath()));
            
            final int iter = i;
            
            selectedWeapons[i].button.addListener(new InputListener() {
                
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    // Save off the index of this button for when we figure out which weapon to replace
                    buttonContext = iter;

                    // Open the dialog to select a weapon for that slot
                    openSelectionDialog();
                    
                    return super.touchDown(event, x, y, pointer, button);
                }
                
            });
           
            
            // Add the actual button
            masterTable.add(selectedWeapons[i].button).fill().expand();
           
            
            stage.addActor(masterTable);
        }

        
        masterTable.addActor(backButton);
        
        stage.addActor(masterTable);
        
        masterTable.setFillParent(true);
        
        Gdx.input.setInputProcessor(stage);
    }

    private void openSelectionDialog() {
        dialog.open = true;
        Gdx.input.setInputProcessor(dialog.dStage);
    }
    
    @Override
    public void render(float delta) {
        if(dialog.open) {
            dialog.updateAndRender(delta);
        }
        else {
            stage.act(delta);
            stage.draw();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        stage.getCamera().update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        dialog.dStage.dispose();
    }
    
    
}
