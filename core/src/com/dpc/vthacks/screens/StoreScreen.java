package com.dpc.vthacks.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.App;
import com.dpc.vthacks.Bank;
import com.dpc.vthacks.PagedScrollPane;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.weapons.Weapon;
import com.dpc.vthacks.weapons.WeaponManager;

public class StoreScreen implements Screen {
    private Stage stage;
    private Table scrollTable;
    private Label backButton;
    private Label moneyLabel;
    private ImageButton[] weaponButtons;
    private WeaponInfo weaponInfo; // Info on selected weapon
    private Skin weaponIcons;
    private App context;
    private Image background;
    private static final int PADDING = 5;
    
    private static class WeaponInfo {
        private Label name;
        private Label description;
        private Label damage;
        private Label ammo;
        private Label cost;
        private int id;
        private Image icon;
        private Label purchase;
        private Label upgrade;
        
        private WeaponInfo() {
            Assets.labelStyle.font = Assets.zombieXSmallFont;
            
            cost = new Label("", Assets.labelStyle);
            description = new Label("", Assets.labelStyle);
            name = new Label("", Assets.labelStyle);
            
            Assets.labelStyle.font = Assets.zombieFont;
        }
        
        private void add(Stage stage) {
            stage.addActor(name);
            stage.addActor(description);
            stage.addActor(icon);
            stage.addActor(purchase);
        }
    }
    
    public StoreScreen(App context) {
        this.context = context;
    }
    
    @Override
    public void show() {
        Assets.allocateStoreScreen();
        
        stage = new Stage(new StretchViewport(AppData.width, AppData.height));

        background = new Image(Assets.menuBackground);
        background.setWidth(AppData.width);
        background.setHeight(AppData.height);
        background.addAction(alpha(0.5f, 1));
        
        stage.addActor(background);
        
        weaponInfo = new WeaponInfo();
        
        weaponInfo.purchase = new Label("Purchase", Assets.labelStyle);
        
        weaponInfo.purchase.setColor(Assets.RED);
        
        weaponInfo.purchase.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int cost = Integer.parseInt(weaponInfo.cost.getText()
                        .toString().replace("Cost: ", ""));
                
                if(Bank.getBalance() >= cost && !WeaponManager.isUnlocked(weaponInfo.id)) {
                    if(WeaponManager.isUnlocked(weaponInfo.id)) return false;
                    
                    // Unlock the weapon
                    WeaponManager.unlock(weaponInfo.id);
                    
                    // Withdrawal money
                    Bank.withdrawal(cost);
                    
                    // Update display
                    moneyLabel.setText("You have $" + Bank.getBalance());
                    weaponInfo.purchase.setText("Purchased");
                }
                
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        
        moneyLabel = new Label("You have $" + Bank.getBalance(), Assets.labelStyle);
        
        moneyLabel.setColor(0, 0.4f, 0, 1);
        
        moneyLabel.setPosition(AppData.width-
                               (moneyLabel.getStyle().font.getBounds(moneyLabel.getText()).width),
                               AppData.height -
                               (moneyLabel.getStyle().font.getBounds(moneyLabel.getText()).height*2));
                               
        
    //    stage.addActor(moneyLabel);
        
        backButton = new Label("Back", Assets.labelStyle);
        
        backButton.setPosition(PADDING, 
                AppData.height - 2 * Assets.textButtonStyle.font.getBounds(backButton.getText()).height);
        
        
        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.setScreen(new MenuScreen(context));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        
        backButton.setColor(Assets.RED);
        
        stage.addActor(backButton);

        weaponIcons = new Skin(Assets.weaponIconAtlas);
        
        weaponButtons = new ImageButton[WeaponManager.NUMBER_OF_WEAPONS];
        
        PagedScrollPane scroll = new PagedScrollPane();
        scroll.setFlingTime(0.1f);
        scroll.setPageSpacing(AppData.width / 6);
        scroll.setWidth(AppData.width);
        
        for(int i = 0; i < weaponButtons.length; i++) {
            final Weapon weapon = WeaponManager.getWeapons().get(i);

            ImageButton button = new ImageButton(
                    weaponIcons.getDrawable(WeaponManager.getWeapons().get(i)
                            .getIconPath()));
           
            weaponButtons[i] = button;
            
            final int a = i;
            
            weaponButtons[i].addListener(new InputListener() {
                
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    StoreScreen.this.display(weapon);
                    
                    return super.touchDown(event, x, y, pointer, button);
                }
                
            });
            
            
            scroll.addPage(button);
        }
        
        stage.addActor(scroll);
        
        display(WeaponManager.getWeapons().get(0));
        positionElements();
        
        Gdx.input.setInputProcessor(stage);
    }

    public void positionElements() {
        VerticalGroup v = new VerticalGroup();
        v.setFillParent(true);
        
        v.addActor(moneyLabel);
        v.addActor(weaponInfo.icon);
        v.addActor(weaponInfo.name);
        v.addActor(weaponInfo.description);
        v.addActor(weaponInfo.cost);
        v.addActor(weaponInfo.purchase);
        
        stage.addActor(v);
    }
    
    public void display(Weapon weapon) {
        if(weaponInfo.icon == null) {
            weaponInfo.icon = new Image();
        }
        
        weaponInfo.icon.setDrawable(weaponIcons.getDrawable(weapon.getIconPath()));
        weaponInfo.name.setText(weapon.getName());
        weaponInfo.description.setText("Description: " + weapon.getDescription());
        weaponInfo.cost.setText("Cost: " + weapon.getCost());
        weaponInfo.id = weapon.getId();
        
        // Only allow purchase if enough money is there
       // weaponInfo.purchase.setDisabled(Bank.getBalance() >= weapon.getCost() || 
                                 //       WeaponManager.isUnlocked(weapon.getId()));
        
        if(WeaponManager.isUnlocked(weapon.getId())) {
            weaponInfo.purchase.setText("Purchased");
        }
        else {
            weaponInfo.purchase.setText("Purchase");
        }
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
        dispose();
    }

    @Override
    public void dispose() {
        Assets.deallocateStoreScreen();
        stage.dispose();
    }

}
