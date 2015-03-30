package com.dpc.vthacks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.App;
import com.dpc.vthacks.PagedScrollPane;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.weapons.Weapon;
import com.dpc.vthacks.weapons.WeaponManager;

public class WeaponSelectionScreen implements Screen {
    private Stage stage;
    private WeaponButton[] selectedWeapons; // The weapons that can be selected. 2 slots.
    private Skin weaponIconSkin;
    private WeaponSelectionDialog dialog;
    private App context;
    private Table weaponSelectionTable;
    private Table masterTable;
    private TextButton selectLevelButton;
    private TextButton backButton;
    private Label title;
    private TextButton next;
    private Weapon selectedWeapon;
    private Image background;
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
        private PagedScrollPane scrollPane;
        private TextButton okay;
        private Stage dStage;
        private Label weaponName, weaponDamage, weaponAmmo, weaponDesc;
        
        public WeaponSelectionDialog() {   
            dStage = new Stage(new StretchViewport(AppData.width, AppData.height));
            
            background = new Image(Assets.menuBackground);
            background.setWidth(AppData.width);
            background.setHeight(AppData.height);
            background.addAction(alpha(0.5f, 1));
            
            stage.addActor(background);
            
            Image background1 = new Image(Assets.menuBackground);
            background1.setWidth(AppData.width);
            background1.setHeight(AppData.height);
            background1.addAction(alpha(0.5f));
            
            dStage.addActor(background1);
            
            
            table = new Table();

            okay = new TextButton("Okay", Assets.uiSkin);
            okay.setPosition(okay.getWidth() + PADDING, okay.getHeight() + PADDING);

            okay.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    open = false;

                    if(selectedWeapon != null) {
                        // Now swap the weapon out for the new one
                        selectedWeapons[buttonContext].weapon = selectedWeapon;
                        
                        // Change the textureregion of the icon
                        ((TextureRegionDrawable) selectedWeapons[buttonContext].button.getImage()
                                .getDrawable()).setRegion(Assets.weaponIconAtlas
                                .findRegion(selectedWeapon.getIconPath()));
    
                        
                        // Reassign the layout
                        selectedWeapons[buttonContext].button.invalidateHierarchy();
                        weaponSelectionTable.invalidateHierarchy();
                    }
                    
                    Gdx.input.setInputProcessor(stage);
                    
                    return super.touchDown(event, x, y, pointer, button);
                }
            });

            weaponIcons = new Image[WeaponManager.getUnlockedWeapons().size];
            
            PagedScrollPane scroll = new PagedScrollPane();
            scroll.setFlingTime(0.1f);

            scroll.setPageSpacing(AppData.width / 6);
            
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
                        
                        displaySelectedWeapon();
                        
                        return super.touchDown(event, x, y, pointer, button);
                    }
                });
                
                scroll.addPage(weaponIcons[i]);
            }

            weaponName = new Label("", Assets.labelStyle);
            weaponDamage = new Label("", Assets.labelStyle);
            weaponAmmo = new Label("", Assets.labelStyle);
            weaponDesc = new Label(" ", Assets.labelStyle);
            
      
            VerticalGroup vgroup = new VerticalGroup();
            vgroup.addActor(weaponName);
      
      
            table.add(vgroup);
            table.row();
            table.row();
            table.row();
            table.add(scroll);
            table.row();
            table.add(okay);
            
            table.setFillParent(true);
            
            name = new Label(WeaponManager.getUnlockedWeapons().get(0).getName(),
                    Assets.labelStyle);
            
            dStage.addActor(table);
            
            stage.getViewport().update(AppData.width, AppData.height);
            stage.getCamera().update();
        }

        public void displaySelectedWeapon() {
            weaponName.setText("" + selectedWeapon.getName());
            weaponDamage.setText("damage: " + selectedWeapon.getMinDamage() + " to " + selectedWeapon.getMaxDamage());
            weaponAmmo.setText("ammo: " + selectedWeapon.getAmmo());
            weaponDesc.setText("description: " + selectedWeapon.getDescription());
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
        Assets.allocateWeaponSelectionScreen();
        
        stage = new Stage(new StretchViewport(AppData.width, AppData.height));

        next = new TextButton("Next", Assets.uiSkin);
        
        next.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Save off these for the player
                Factory.setPrimaryGun(selectedWeapons[0].weapon);
                Factory.setSecondaryGun(selectedWeapons[1].weapon);
        
                context.setScreen(new LevelSelectionScreen(context));
                
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        
        title = new Label("Select your weapons", Assets.labelStyle);
        
        selectLevelButton = new TextButton("Select a level", Assets.textButtonStyle);
        
        selectLevelButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.setScreen(new LevelSelectionScreen(context));
                
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        
        backButton = new TextButton("Back", Assets.uiSkin);
        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.setScreen(new MenuScreen(context));

                return super.touchDown(event, x, y, pointer, button);
            }
        });
        
        backButton.setPosition(PADDING, 
                               AppData.height - (Assets.textButtonStyle.font.getBounds("Back").height*2));
        
        weaponIconSkin = new Skin(Assets.weaponIconAtlas);
        
        dialog = new WeaponSelectionDialog();
        
        // Primary and secondary
        selectedWeapons = new WeaponButton[2];
        
        masterTable = new Table();
        weaponSelectionTable = new Table();
        
        VerticalGroup vgroup = new VerticalGroup();
        Table hgroup = new Table();
        
        vgroup.addActor(title);

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

                    // Reset the selected weapon
                    selectedWeapon = selectedWeapons[iter].weapon;
                    
                    // Update the display
                    dialog.displaySelectedWeapon();
                    
                    // Open the dialog to select a weapon for that slot
                    openSelectionDialog();
                    
                    return super.touchDown(event, x, y, pointer, button);
                }
                
            });        
            
            hgroup.pad(title.getHeight());
            
            // Add the actual button
            hgroup.add(selectedWeapons[i].button).pad(15).fill().expand();
        }

        vgroup.addActor(hgroup);
        
        weaponSelectionTable.add(vgroup);
        
        weaponSelectionTable.setPosition(0, AppData.height - weaponSelectionTable.getHeight());
        
        weaponSelectionTable.row();
        weaponSelectionTable.add(next);
        
        masterTable.add(weaponSelectionTable);

        masterTable.addActor(backButton);
        

        masterTable.setFillParent(true);
        
        Table parent = new Table();
        parent.setSize(AppData.width, AppData.height * 0.5f);
    
        parent.addActor(masterTable);

        parent.setFillParent(true);
        
        stage.addActor(parent);
        
        
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
        dispose();
    }

    @Override
    public void dispose() {
        Assets.deallocateWeaponSelectionScreen();
        
        stage.dispose();
        dialog.dStage.dispose();
    }
}