package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 散射策略
 */
public class ScatterShoot implements ShootStrategy {

    @Override
    public List<BaseBullet> doShoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();

        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY();
        int direction = aircraft.getDirection();
        int speedY = aircraft.getSpeedY() + direction * 5;
        int power = aircraft.getPower();
        int shootNum = aircraft.getShootNum();

        if (shootNum <= 1) {
            shootNum = 3;
        }

        for (int i = 0; i < shootNum; i++) {
            BaseBullet bullet;
            int bulletX = x + (i * 2 - shootNum + 1) * 10;

            int speedX = (i * 2 - shootNum + 1);

            if (direction == -1) {
                bullet = new HeroBullet(bulletX, y, speedX, speedY, power);
            } else {
                bullet = new EnemyBullet(bulletX, y, speedX, speedY, power);
            }
            res.add(bullet);
        }
        return res;
    }
}