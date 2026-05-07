package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.observer.Observer;
import edu.hitsz.strategy.DirectShoot;

/**
 * 精英敌机
 */
public class EliteEnemy extends AbstractAircraft implements Observer {

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.image = ImageManager.ELITE_ENEMY_IMAGE;

        this.shootNum = 1;
        this.power = 10;
        this.direction = 1;
        this.setShootStrategy(new DirectShoot());
    }

    @Override
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public void updateBomb() {
        this.vanish();
    }

    @Override
    public void updateFreeze() {
        int originalSpeedX = this.speedX;
        int originalSpeedY = this.speedY;

        this.speedX = 0;
        this.speedY = 0;

        Runnable freezeTask = () -> {
            try {
                Thread.sleep(4000);

                this.speedX = originalSpeedX;
                this.speedY = originalSpeedY;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        new Thread(freezeTask, "Freeze-Elite-Thread").start();
    }
}