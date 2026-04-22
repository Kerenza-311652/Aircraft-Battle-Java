package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AwakeEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class AwakeFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy() {
        int locationX = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.AWAKE_ENEMY_IMAGE.getWidth()));
        int locationY = 0;
        int speedX = 0;
        int speedY = 5;
        int hp = 50;
        return new AwakeEnemy(locationX, locationY, speedX, speedY, hp);
    }
}