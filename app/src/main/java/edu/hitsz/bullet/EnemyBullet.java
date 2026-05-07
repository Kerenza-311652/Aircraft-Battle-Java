package edu.hitsz.bullet;
import edu.hitsz.observer.Observer;
/**
 * 敌机子弹
 * @Author hitsz
 */
public class EnemyBullet extends BaseBullet implements Observer {

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
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
                Thread.sleep(5000);

                this.speedX = originalSpeedX;
                this.speedY = originalSpeedY;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        new Thread(freezeTask, "Freeze-Elite-Thread").start();
    }

}
