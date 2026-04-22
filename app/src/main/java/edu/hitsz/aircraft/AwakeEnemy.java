package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.strategy.DirectShoot;

import java.util.LinkedList;
import java.util.List;

/**
 * 精英敌机
 */
public class AwakeEnemy extends AbstractAircraft {

    private int shootNum = 2;
    private int power = 10;
    private int direction = 1;

    public AwakeEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.image = ImageManager.AWAKE_ENEMY_IMAGE;
        this.strategy = new DirectShoot();
    }

    @Override
    public void forward() {
        super.forward();
        // 敌机一直向下移动
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return strategy.doShoot(this.locationX, this.locationY, this.speedX, this.speedY, this.direction, this.power, this.shootNum);
    }
}