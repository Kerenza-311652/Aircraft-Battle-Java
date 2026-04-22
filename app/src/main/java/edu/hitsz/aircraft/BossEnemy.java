package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.strategy.RingShoot;

import java.util.LinkedList;
import java.util.List;

/**
 * 精英敌机
 */
public class BossEnemy extends AbstractAircraft {

    private int shootNum = 20;
    private int power = 10;
    private int direction = 1;

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.image = ImageManager.BOSS_ENEMY_IMAGE;
        this.strategy = new RingShoot();
    }

    @Override
    public void forward() {
        super.forward();
    }

    @Override
    public List<BaseBullet> shoot() {
        return strategy.doShoot(this.locationX, this.locationY, this.speedX, this.speedY, this.direction, this.power, this.shootNum);
    }
}