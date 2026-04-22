package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AceEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class AceFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy() {
        int locationX = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ACE_ENEMY_IMAGE.getWidth()));
        int locationY = 0;
        int speedX = 1;
        int speedY = 4;
        int hp = 60;
        return new AceEnemy(locationX, locationY, speedX, speedY, hp);
    }
}