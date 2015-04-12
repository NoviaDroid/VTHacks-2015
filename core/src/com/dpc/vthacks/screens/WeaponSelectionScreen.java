package com.dpc.vthacks.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.App;
import com.dpc.vthacks.MyActions;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.weapons.Weapon;
import com.dpc.vthacks.weapons.WeaponManager;

public class WeaponSelectionScreen implements Screen {
    private WeaponButton[] selectedWeapons;
    private Stage stage;
    private WeaponButton[] primarySelectableWeapons; // The weapons that can be selected. 2 slots.
    private WeaponButton[] secondarySelectableWeapons;
    private Skin weaponIconSkin;
    private App context;
    private Table currentSelectionTable;
    private int buttonContext;
    private Table selectionBoxTable;
    private Table primarySelectionTable;
    private Table secondarySelectionTable;
    private Label backButton;
    private Label title;
    private Label next;
    private Display display;
    private Label okayButton;
    private static final int PADDING = 5;
    
    private final class Display {
        private Image weaponIcon;
        private Label name;
        private Label equip;
        private Label cancel;
        private Label upgrade;
        private Label desc;
        private Weapon currentWeapon;
        private Table displayTable;
        
        private Display() {
            desc = new Label("", Assets.storeLabelStyle);
            desc.setColor(Color.GRAY);
            
            name = new Label("", Assets.storeLabelStyle);
            name.setColor(Assets.RED);
            
            cancel = new Label("Cancel", Assets.storeLabelStyle);
            cancel.setColor(Color.RED);
            
            cancel.addListener(new InputListener() {
               
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    displayTable.addAction(sequence(fadeOut(0.15f), new Action() {
                        
                        @Override
                        public boolean act(float delta) {
                            displayTable.remove();

                            stage.addActor(currentSelectionTable);
                            
                            currentSelectionTable.addAction(fadeIn(0.15f));
                            
                            return true;
                        }
                        
                    }));
                    return true;
                }
                
            });
            
            equip = new Label("Equip", Assets.storeLabelStyle);
            equip.setColor(Assets.GREEN);

            equip.addListener(new InputListener() {
                
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(equip.getText().toString().equals("Equip")) {
                        equip.addAction(sequence(alpha(0.75f, 0.05f), alpha(1, 0.05f), new Action() {
                            
                            @Override
                            public boolean act(float delta) { 
                                equip.setText("Equipped");
                                
                                // Change selected weapon
                                selectedWeapons[buttonContext].weapon = currentWeapon;
                                
                                selectedWeapons[buttonContext].button.getStyle().up = weaponIconSkin.getDrawable(currentWeapon.getIconPath());
                                selectedWeapons[buttonContext].button.getStyle().down = weaponIconSkin.getDrawable(currentWeapon.getIconPath());
                                
                      
                                selectedWeapons[buttonContext].button.setSize(Assets.weaponIconAtlas.findRegion(currentWeapon.getIconPath()).getRegionWidth(),
                                                                              Assets.weaponIconAtlas.findRegion(currentWeapon.getIconPath()).getRegionHeight());
                                
                                
                                selectedWeapons[buttonContext].button.invalidate();
                                
                                return true;
                            }
                            
                        }));
                    }
                    else {
                        equip.addAction(MyActions.backAndForth());
                    }
                    
                    return true;
                }
                
            });
            
            weaponIcon = new Image();
            
            displayTable = new Table();
            
            displayTable.add(weaponIcon).row();
            displayTable.add(name).row();
            displayTable.add(desc).row();
            
            HorizontalGroup hg = new HorizontalGroup();

            hg.addActor(cancel);
            
            hg.space(AppData.width * 0.05f);
            
            hg.addActor(equip);

            displayTable.add(hg);
            displayTable.setFillParent(true);
            displayTable.center();
        }
        
        private void open(Weapon weapon) {
            this.currentWeapon = weapon;
        
            
            currentSelectionTable.addAction(sequence(fadeOut(0.15f), new Action() {
                
                @Override
                public boolean act(float delta) {
                    currentSelectionTable.remove();
                    stage.addActor(displayTable);
                    
                    displayTable.addAction(sequence(fadeIn(0.15f)));
                    
                    return true;
                }
                
            }));
            
            currentSelectionTable.setFillParent(true);

            boolean matches = false;
            
            for(WeaponButton w : selectedWeapons) {
                if(weapon.equals(w.weapon)) {
                    equip.setText("Equipped");
                    matches = true;
                }
            }
            
            if(!matches) {
                equip.setText("Equip");
            }
            
            weaponIcon.setDrawable(new TextureRegionDrawable(Assets.weaponIconAtlas.findRegion(weapon.getIconPath())));
            name.setText(weapon.getName());
            desc.setText(weapon.getDescription());
        }
    }
    
    private final class WeaponButton {
        private Weapon weapon;
        private ImageButton button;
    }
    
    public WeaponSelectionScreen(App context) {
        this.context = context;
    }
    
    @Override
    public void show() {
        Assets.allocateWeaponSelectionScreen();
        
        stage = new Stage(new StretchViewport(AppData.width, AppData.height), App.batch);

        display = new Display();
        
        next = new Label("Next", Assets.aerialLabelStyle);
        
        next.addListener(new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Save off these for the player
                Factory.setPrimaryGun(selectedWeapons[0].weapon);
                Factory.setSecondaryGun(selectedWeapons[1].weapon);
        
                context.setScreen(new ModeSelectionScreen(context));
                
                return super.touchDown(event, x, y, pointer, button);
            }
            
        });
        
        next.setColor(Assets.RED);
        
        title = new Label("Select your weapons", Assets.aerialLabelStyle);
    
        title.setColor(Color.GRAY);
        
        backButton = new Label("Back", Assets.aerialLabelStyle);
        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.setScreen(new MenuScreen(context));

                return super.touchDown(event, x, y, pointer, button);
            }
        });
        
        backButton.setColor(Assets.RED);
        
        backButton.setX(backButton.getWidth() * 0.2f);
        backButton.setY(AppData.height - backButton.getHeight());
        
        weaponIconSkin = new Skin(Assets.weaponIconAtlas);
        
        // Primary
        primarySelectableWeapons = new WeaponButton[WeaponManager.getPrimaryWeapons().size];
        secondarySelectableWeapons = new WeaponButton[WeaponManager.getSecondaryWeapons().size];
        
        currentSelectionTable = new Table();
        primarySelectionTable = new Table();
        secondarySelectionTable = new Table();
        
        Image bg = new Image(Assets.menuBackground);
        bg.setWidth(AppData.width);
        bg.setHeight(AppData.height);
        bg.addAction(Actions.alpha(0.5f, 0.5f));
        
        stage.addActor(bg);
        
        createSelectionOptionTable(true, primarySelectionTable, primarySelectableWeapons);
        createSelectionOptionTable(false, secondarySelectionTable, secondarySelectableWeapons);
        
        next.setPosition(AppData.width - next.getWidth() - PADDING, 
                         PADDING);
        
        
        float cX = AppData.width * 0.5f;
        float cY = AppData.height * 0.5f;
      
        title.setX(cX - (title.getWidth() * 0.5f));
        title.setY(cY + (title.getHeight() * 2));
        
        stage.addActor(next);
       
        stage.addActor(title);
        
        selectionBoxTable = new Table();

        selectedWeapons = new WeaponButton[2];
        
        for(int i = 0; i < selectedWeapons.length; i++) {
            WeaponButton img = new WeaponButton();
            
            if(i == 0) {
                img.weapon = WeaponManager.getUnlockedPrimaryWeapons().get(0);
                img.button = new ImageButton(new TextureRegionDrawable(Assets.weaponIconAtlas.findRegion(WeaponManager.getUnlockedPrimaryWeapons().get(0).getIconPath())));
            }
            else {
                img.weapon = WeaponManager.getUnlockedSecondaryWeapons().get(0);
                img.button = new ImageButton(new TextureRegionDrawable(Assets.weaponIconAtlas.findRegion(WeaponManager.getUnlockedSecondaryWeapons().get(0).getIconPath())));
            }
            
            final int iter = i;
            
            img.button.addListener(new InputListener () {
                
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    buttonContext = iter;
                    
                    selectionBoxTable.addAction(sequence(fadeOut(0.15f), new Action() {

                        @Override
                        public boolean act(float delta) {
                            selectionBoxTable.remove();
    
                            // Find out which box was pressed
                            
                            if(buttonContext == 0) {
                                currentSelectionTable = primarySelectionTable;
                            }
                            else {
                                currentSelectionTable = secondarySelectionTable;
                            }
                            
                            currentSelectionTable.addAction(fadeIn(0.15f));
                            
                            stage.addActor(currentSelectionTable);

                            return true;
                        }
                        
                    }));

                    return true;
                }
                
            });
            
            selectedWeapons[i] = img;
            
            selectionBoxTable.add(img.button).width(img.button.getWidth() * 2)
                                      .height(img.button.getHeight() * 2).fill().expand();
        }
        
        selectionBoxTable.setFillParent(true);
        
        stage.addActor(selectionBoxTable);
        
        Gdx.input.setInputProcessor(stage);
    }

    private void createSelectionOptionTable(boolean primary, final Table out, final WeaponButton[] array) {
        final Table hgroup = new Table();
        
        for(int i = 0; i < array.length; i++) {
            
            array[i] = new WeaponButton();
            
            
            if(primary) {
                array[i].weapon = WeaponManager.getPrimaryWeapons().get(i);
            }
            else {
                array[i].weapon = WeaponManager.getSecondaryWeapons().get(i);
            }
            
            // Assign the button
            array[i].button = new ImageButton(
                    weaponIconSkin.getDrawable(array[i].weapon.getIconPath()));
            
            final int iter = i;
            
            array[i].button.addListener(new InputListener() {
                
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    
                    currentSelectionTable = out;
                    
                    if(!WeaponManager.isUnlocked(array[iter].weapon.getId())) {
                        array[iter].button.addAction(MyActions.backAndForth());
                    }
                    else {
                        display.open(array[iter].weapon);
                    }
                    
                    
                    return super.touchDown(event, x, y, pointer, button);
                }
                
            });        
            
         
            if(!WeaponManager.isUnlocked(array[i].weapon.getId())) {
                array[i].button.isDisabled();
                array[i].button.addAction(Actions.alpha(0.25f, 0));
            }
            
            hgroup.pad(title.getHeight());
            
            // Add the actual button
            hgroup.add(array[i].button)
                    .width(array[i].button.getWidth() * 2)
                    .height(array[i].button.getHeight() * 2);
            
            if((i + 1) % 3 == 0) {
                hgroup.row();
            }
        }

        out.add(hgroup);

        stage.addActor(backButton);
        
        okayButton = new Label("Okay", Assets.storeLabelStyle);
        
        okayButton.setColor(Assets.RED);
        
        out.row();
        out.add(okayButton);
        
        out.setFillParent(true);

        out.setSize(AppData.width, AppData.height * 0.5f);

        out.bottom();
        out.center();

        
        okayButton.addListener(new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               
                out.addAction(sequence(fadeOut(0.15f), new Action() {

                    @Override
                    public boolean act(float delta) {
                        out.remove();
                        
                        stage.addActor(selectionBoxTable);
                        
                        selectionBoxTable.addAction(fadeIn(0.15f));
                        //parent.addAction(fadeIn(0));
                        
                        return true;
                    }
                    
                }));
                
                return true;
            }
            
        });    
    }
    
    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
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
    }
}
