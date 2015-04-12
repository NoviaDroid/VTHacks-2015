package com.dpc.vthacks.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.dpc.vthacks.AndroidCamera;
import com.dpc.vthacks.App;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.data.Bank;
import com.dpc.vthacks.weapons.Weapon;
import com.dpc.vthacks.weapons.WeaponManager;

public class StoreScreen implements Screen {
    private Weapon currentWeapon;
    private Stage stage;
    private Table rootTable;
    private Table lTable;
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
        
        private WeaponInfo() {
            cost = new Label("", Assets.storeLabelStyle);
            description = new Label("", Assets.storeLabelStyle);
            description.setColor(Color.GRAY);
            name = new Label("", Assets.storeLabelStyle);
        }
    }
    
    public StoreScreen(App context) {
        this.context = context;
    }
    
    @Override
    public void show() {
        Assets.allocateStoreScreen();
        
        stage = new Stage(new ScalingViewport(Scaling.stretch, 
                                              AppData.width,
                                              AppData.height,
                                              new AndroidCamera(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT)),
                                              App.batch);
        
        background = new Image(Assets.menuBackground);
        background.setWidth(AppData.TARGET_WIDTH);
        background.setHeight(AppData.TARGET_HEIGHT);
        background.addAction(alpha(0.5f, 1));
        
        stage.addActor(background);
        
        weaponInfo = new WeaponInfo();
        
        weaponInfo.purchase = new Label("Purchase", Assets.storeLabelStyle);
        
        weaponInfo.purchase.setColor(Assets.RED);
        
        weaponInfo.purchase.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int cost = Integer.parseInt(weaponInfo.cost.getText().toString().replace("$", ""));
                
                if(Bank.getBalance() >= cost && !WeaponManager.isUnlocked(weaponInfo.id)) {
                    if(WeaponManager.isUnlocked(weaponInfo.id)) return false;
                    
                    if(currentWeapon.isPrimary()) {
                        // Unlock the weapon
                        WeaponManager.unlockPrimary(weaponInfo.id);
                    }
                    else {
                        WeaponManager.unlockSecondary(weaponInfo.id);
                    }
                    
                    // Withdrawal money
                    Bank.withdrawal(cost);
                    
                    // Update display
                    moneyLabel.setText("You have $" + Bank.getBalance());
                    weaponInfo.purchase.setText("Purchased");
                }
                
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        
        moneyLabel = new Label("You have $" + Bank.getBalance(), Assets.storeLabelStyle);
        
        moneyLabel.setColor(Assets.GREEN);
        
        moneyLabel.setPosition(AppData.TARGET_WIDTH-
                               (moneyLabel.getStyle().font.getBounds(moneyLabel.getText()).width),
                               AppData.TARGET_HEIGHT -
                               (moneyLabel.getStyle().font.getBounds(moneyLabel.getText()).height*2));
                               
        
    //    stage.addActor(moneyLabel);
        
        backButton = new Label("Back", Assets.storeLabelStyle);
        
        backButton.setPosition(PADDING, 
                AppData.TARGET_HEIGHT - 2 * Assets.textButtonStyle.font.getBounds(backButton.getText()).height);
        
        
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
        
//        PagedScrollPane scroll = new PagedScrollPane();
//        scroll.setFlingTime(0.1f);
//        scroll.setPageSpacing(AppData.TARGET_WIDTH / 6);
//        scroll.setWidth(AppData.TARGET_WIDTH);
//        scroll.setHeight(AppData.TARGET_HEIGHT * 0.35f);
//       
        rootTable = new Table();
        
        Array<Weapon> all = WeaponManager.getAllWeapons();
        
        for(int i = 0; i < weaponButtons.length; i++) {
            final Weapon weapon = all.get(i);

            ImageButton button = new ImageButton(
                    weaponIcons.getDrawable(all.get(i)
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
            
            rootTable.add(weaponButtons[i]).width(weaponButtons[i].getWidth() * 2)
                                       .height(weaponButtons[i].getHeight() * 2);
            
            if((i + 1) % 3 == 0) {
                rootTable.row();
            }
            
        }

        rootTable.bottom();
        
      
        stage.addActor(rootTable);
        
        display(all.get(0));
        positionElements();

        Gdx.input.setInputProcessor(stage);
    }

    public void positionElements() {
        lTable = new Table();
        
        moneyLabel.setPosition((AppData.TARGET_WIDTH * 0.5f) - (moneyLabel.getWidth() * 0.5f),
                               AppData.TARGET_HEIGHT - 3 * moneyLabel.getHeight() + PADDING);
        
        stage.addActor(moneyLabel);
        
        Label header1 = new Label("Description:", Assets.storeLabelStyle);
        header1.setColor(Assets.RED);

        Label header2 = new Label("Cost:      ", Assets.storeLabelStyle);
        header2.setColor(Assets.RED);
       
        lTable.pad(25);
        
        weaponInfo.name.setColor(Assets.RED);
        weaponInfo.cost.setColor(Assets.GREEN);
        
        if(weaponInfo.icon.getScaleX() != 2) {
            weaponInfo.icon.setScale(2);
        }

        lTable.add(weaponInfo.icon).row();
        lTable.add(weaponInfo.name).row();
        lTable.add(weaponInfo.description).row();
        lTable.add(weaponInfo.cost).row();
        lTable.add(weaponInfo.purchase).row();
        
     
        stage.addActor(lTable);
    }
    
    public void display(Weapon weapon) {
        if(weaponInfo.icon == null) {
            weaponInfo.icon = new Image();
        }
        
        weaponInfo.icon.setDrawable(weaponIcons.getDrawable(weapon.getIconPath()));
        weaponInfo.name.setText(weapon.getName());
        weaponInfo.description.setText(weapon.getDescription());
        weaponInfo.cost.setText("$" + weapon.getCost());
        weaponInfo.id = weapon.getId();
        
        this.currentWeapon = weapon;
        
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

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().setCamera(new AndroidCamera(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT));
        lTable.invalidateHierarchy();
        lTable.setSize(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT);
        rootTable.invalidateHierarchy();
        rootTable.setSize(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT); 
        background.setSize(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT);
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
