package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.observer.Observer;
import edu.hitsz.strategy.ScatterShoot;

/**
 * 王牌敌机
 */
public class AceEnemy extends AbstractAircraft implements Observer {

    public AceEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.image = ImageManager.ACE_ENEMY_IMAGE;

        this.shootNum = 3;
        this.power = 15;
        this.direction = 1;
        this.setShootStrategy(new ScatterShoot());
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
        this.hp -= 10;
    }

    @Override
    public void updateFreeze() {
        int originalSpeedX = this.speedX;
        int originalSpeedY = this.speedY;

        this.speedX -= 1;
        this.speedY -= 1;

        Runnable freezeTask = () -> {
            try {
                Thread.sleep(5000);

                this.speedX = originalSpeedX;
                this.speedY = originalSpeedY;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        new Thread(freezeTask, "Freeze-Ace-Thread").start();
    }
}