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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.App;
import com.dpc.vthacks.Bank;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.data.Fonts;
import com.dpc.vthacks.objects.Weapon;
import com.dpc.vthacks.properties.WeaponManager;

public class StoreScreen implements Screen {
    private Stage stage;
    private TextButton backButton;
    private ImageButton[] weaponButtons;
    private WeaponInfo weaponInfo; // Info on selected weapon
    private Skin weaponIcons;
    private App context;
    private static LabelStyle labelStyle;
    private static final int PADDING = 5;
    
    private static class WeaponInfo {
        private Label name = new Label("", labelStyle);
        private Label description = new Label("", labelStyle);
        private Label damage = new Label("", labelStyle);
        private Label ammo = new Label("", labelStyle);
        private Image icon;
        
        private void add(Stage stage) {
            stage.addActor(name);
            stage.addActor(description);
            stage.addActor(damage);
            stage.addActor(ammo);
            stage.addActor(icon);
        }
    }
    
    public StoreScreen(App context) {
        this.context = context;
    }
    
    @Override
    public void show() {
        stage = new Stage(new StretchViewport(AppData.width, AppData.height));
        
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = Fonts.getZombieXSmall();
        
        backButton = new TextButton("back", textButtonStyle);
        
        backButton.setPosition(PADDING, 
                AppData.height - 2 * textButtonStyle.font.getBounds(backButton.getText()).height);
        
        
        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.setScreen(new MenuScreen(context));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        
        stage.addActor(backButton);
        
        labelStyle = new LabelStyle();
        labelStyle.font = Fonts.getZombieSmall();
        
        Skin skin = new Skin(Assets.storeAtlas);
        weaponIcons = new Skin(Assets.weaponIconAtlas);
        
        weaponButtons = new ImageButton[WeaponManager.NUMBER_OF_WEAPONS];
        
        for(int i = 0; i < weaponButtons.length; i++) {
            final Weapon weapon = WeaponManager.getWeapons().get(i);
            
            ImageButton button = new ImageButton(weaponIcons.getDrawable(weapon.getIconPath()));
            button.setPosition(i * button.getWidth(), 0);
            
            weaponButtons[i] = button;
            
            weaponButtons[i].addListener(new InputListener() {
                
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    StoreScreen.this.display(weapon);
                    
                    return super.touchDown(event, x, y, pointer, button);
                }
                
            });
            
            stage.addActor(button);
        }
        
        weaponInfo = new WeaponInfo();
       
        display(WeaponManager.getWeapons().get(0));
        positionElements();
        
        Gdx.input.setInputProcessor(stage);
    }

    public void positionElements() {
        VerticalGroup v = new VerticalGroup();
        v.setFillParent(true);
        
        v.addActor(weaponInfo.icon);
        v.addActor(weaponInfo.name);
        v.addActor(weaponInfo.ammo);
        v.addActor(weaponInfo.damage);
        v.addActor(weaponInfo.description);
        
        stage.addActor(v);
    }
    
    public void display(Weapon weapon) {
        weaponInfo.icon = new Image(weaponIcons.getDrawable(weapon.getIconPath()));
        weaponInfo.name.setText("name - " + weapon.getName());
        weaponInfo.ammo.setText("ammo - " + weapon.getAmmo());
        weaponInfo.damage.setText("damage - " + weapon.getMinDamage() + " to " + weapon.getMaxDamage());
        weaponInfo.description.setText("description - " + weapon.getDescription());
    }
    
    public void update(float delta) {
        stage.act(delta);
    }
    
    @Override
    public void render(float delta) {
        update(delta);
        
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
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
