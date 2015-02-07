package com.dpc.vthacks.factories;

import com.dpc.vthacks.infantry.Soldier;
import com.dpc.vthacks.infantry.Tank;
import com.dpc.vthacks.plane.Plane;

public class Factory {
    private static float playerHealth, playerDamage, playerVelX, playerVelY;
    private static float tankHealth, tankDamage, tankCost, tankVelX, tankVelY;
    private static float enemyHealth, enemyDamage, enemyVelX, enemyVelY;
    private static float soldierHealth, soldierDamage, soldierVelX, soldierVelY, soldierCost;
    
    public static Plane createPlayer(float x, float y) {
        return new Plane(playerDamage, playerHealth, playerVelX, playerVelY, x, y);
    }
    
    public static Tank createTank(float x, float y) {
        return new Tank(tankDamage, tankHealth, tankVelX, tankVelY, x, y);
    }
    
    public static Plane createEnemyPlane(float x, float y) {
        return new Plane(enemyDamage, enemyHealth, enemyVelX, enemyVelY, x, y);
    }
    
    public static Soldier createSoldier(float x, float y) {
        return new Soldier(soldierDamage, soldierHealth, soldierVelX, soldierVelY, x, y);
    }

    public static float getPlayerHealth() {
        return playerHealth;
    }

    public static void setPlayerHealth(float playerHealth) {
        Factory.playerHealth = playerHealth;
    }

    public static float getPlayerDamage() {
        return playerDamage;
    }

    public static void setPlayerDamage(float playerDamage) {
        Factory.playerDamage = playerDamage;
    }

    public static float getPlayerVelX() {
        return playerVelX;
    }

    public static void setPlayerVelX(float playerVelX) {
        Factory.playerVelX = playerVelX;
    }

    public static float getPlayerVelY() {
        return playerVelY;
    }

    public static void setPlayerVelY(float playerVelY) {
        Factory.playerVelY = playerVelY;
    }

    public static float getTankHealth() {
        return tankHealth;
    }

    public static void setTankHealth(float tankHealth) {
        Factory.tankHealth = tankHealth;
    }

    public static float getTankDamage() {
        return tankDamage;
    }

    public static void setTankDamage(float tankDamage) {
        Factory.tankDamage = tankDamage;
    }

    public static float getTankCost() {
        return tankCost;
    }

    public static void setTankCost(float tankCost) {
        Factory.tankCost = tankCost;
    }

    public static float getTankVelX() {
        return tankVelX;
    }

    public static void setTankVelX(float tankVelX) {
        Factory.tankVelX = tankVelX;
    }

    public static float getTankVelY() {
        return tankVelY;
    }

    public static void setTankVelY(float tankVelY) {
        Factory.tankVelY = tankVelY;
    }

    public static float getEnemyHealth() {
        return enemyHealth;
    }

    public static void setEnemyHealth(float enemyHealth) {
        Factory.enemyHealth = enemyHealth;
    }

    public static float getEnemyDamage() {
        return enemyDamage;
    }

    public static void setEnemyDamage(float enemyDamage) {
        Factory.enemyDamage = enemyDamage;
    }

    public static float getEnemyVelX() {
        return enemyVelX;
    }

    public static void setEnemyVelX(float enemyVelX) {
        Factory.enemyVelX = enemyVelX;
    }

    public static float getEnemyVelY() {
        return enemyVelY;
    }

    public static void setEnemyVelY(float enemyVelY) {
        Factory.enemyVelY = enemyVelY;
    }
}
