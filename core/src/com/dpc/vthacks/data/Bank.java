package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.dpc.vthacks.EventSystem;
import com.dpc.vthacks.GameEvent;
import com.dpc.vthacks.IListener;

/**
 * Manager for in game money
 * @author Daniel Christopher
 * @version 3/7/15
 *
 */
public final class Bank {
    private static IListener listener;
    private static int balance, levelMoney;
    private static Preferences prefs;
    private static final String BANK_PREFS = "BANK_PREFS";
    private static final String BANK_BALANCE = "BALANCE";
    
    public static final void load() {
        prefs = Gdx.app.getPreferences(BANK_PREFS);
        
        balance = prefs.getInteger(BANK_BALANCE);
        
        listener = new IListener() {
            @Override
            public void onEvent(GameEvent e) {
                switch(e.getEvent()) {
                case EventSystem.PLAYER_MONEY_CHANGED:
                    deposit((Integer) e.getUserData());
                    break;
                }
            }
        };
        
        EventSystem.register(EventSystem.PLAYER_MONEY_CHANGED, listener);
    }
    

    
    public static final void deposit(int amount) {
        if(amount < 0) return;
        
        withdrawal(-amount);
    }
    
    public static final void withdrawal(int amount) {
        if(amount > balance) return;
        
        balance -= amount;
        updateBalance();
    }
    
    private static final void updateBalance() {
        prefs.putInteger(BANK_BALANCE, balance);
        prefs.flush();
    }
    
    public static final int getBalance() {
        return balance;
    }
    
    public static void setLevelMoney(int levelMoney) {
        Bank.levelMoney = levelMoney;
    }
    
    public static int getLevelMoney() {
        return levelMoney;
    }
}
