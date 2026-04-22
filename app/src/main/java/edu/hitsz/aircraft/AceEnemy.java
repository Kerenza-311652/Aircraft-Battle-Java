package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.strategy.ScatterShoot;

import java.util.LinkedList;
import java.util.List;

/**
 * 精英敌机
 */
public class AceEnemy extends AbstractAircraft {

    private int shootNum = 3;
    private int power = 15;
    private int direction = 1;

    public AceEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.image = ImageManager.ACE_ENEMY_IMAGE;
        this.strategy = new ScatterShoot();
    }

    @Override
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return strategy.doShoot(this.locationX, this.locationY, this.speedX, this.speedY, this.direction, this.power, this.shootNum);
    }
}