package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 半环射策略
 */
public class RingShoot implements ShootStrategy {

    @Override
    public List<BaseBullet> doShoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        int baseSpeed = 8;
        int radius = 30;

        int locationX = aircraft.getLocationX();
        int locationY = aircraft.getLocationY();
        int direction = aircraft.getDirection();
        int power = aircraft.getPower();
        int shootNum = aircraft.getShootNum();

        if (shootNum <= 1) {
            shootNum = 2;
        }

        for (int i = 0; i < shootNum; i++) {
            double angle = 2 * Math.PI * i / (shootNum - 1);

            int bulletSpeedX = (int) Math.round(baseSpeed * Math.cos(angle));
            int bulletSpeedY = (int) Math.round(baseSpeed * Math.sin(angle)) * direction;

            int spawnX = locationX + (int) Math.round(radius * Math.cos(angle));
            int spawnY = locationY + (int) Math.round(radius * Math.sin(angle)) * direction;

            BaseBullet bullet;
            if (direction == -1) {
                bullet = new HeroBullet(spawnX, spawnY, bulletSpeedX, bulletSpeedY, power);
            } else {
                bullet = new EnemyBullet(spawnX, spawnY, bulletSpeedX, bulletSpeedY, power);
            }
            res.add(bullet);
        }
        return res;
    }
}