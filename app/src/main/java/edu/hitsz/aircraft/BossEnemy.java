package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.observer.Observer;
import edu.hitsz.strategy.RingShoot;

/**
 * Boss敌机
 */
public class BossEnemy extends AbstractAircraft implements Observer {

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.image = ImageManager.BOSS_ENEMY_IMAGE;

        this.shootNum = 15;
        this.power = 10;
        this.direction = 1;
        this.setShootStrategy(new RingShoot());
    }

    @Override
    public void forward() {
        super.forward();
    }

    @Override
    public void updateBomb() {
    }

    @Override
    public void updateFreeze() {
    }
}