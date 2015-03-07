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
    private static int balance;
    private static Preferences prefs;
    
    public static final void load() {
        prefs = Gdx.app.getPreferences("MY_PREFERENCES");
        
        balance = prefs.getInteger("balance");
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
        prefs.putInteger("balance", balance);
        prefs.flush();
    }
    
    public static final int getBalance() {
        return balance;
    }
}
