package com.dpc.vthacks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Manager for in game money
 * @author Daniel Christopher
 * @version 3/7/15
 *
 */
public final class Bank {
    private static int balance, levelMoney;
    private static Preferences prefs;
    private static final String BANK_PREFS = "BANK_PREFS";
    private static final String BANK_BALANCE = "BALANCE";
    
    public static final void load() {
        prefs = Gdx.app.getPreferences(BANK_PREFS);
        
        balance = prefs.getInteger(BANK_BALANCE);
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
