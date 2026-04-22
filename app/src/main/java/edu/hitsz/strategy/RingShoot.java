package edu.hitsz.strategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 半环射策略 (180度)
 * 会根据发射者的 direction 自动朝前（上或下）发射
 */
public class RingShoot implements ShootStrategy {

    @Override
    public List<BaseBullet> doShoot(int locationX, int locationY, int speedX, int speedY, int direction, int power, int shootNum) {
        List<BaseBullet> res = new LinkedList<>();
        int baseSpeed = 8;

        if (shootNum <= 1) {
            shootNum = 2;
        }

        for (int i = 0; i < shootNum; i++) {
            double angle =  Math.PI * i / (shootNum - 1);

            int bulletSpeedX = (int) (baseSpeed * Math.cos(angle));
            int bulletSpeedY = (int) (baseSpeed * Math.sin(angle)) * direction;

            BaseBullet bullet;
            if (direction == -1) {
                bullet = new HeroBullet(locationX, locationY, bulletSpeedX, bulletSpeedY, power);
            } else {
                bullet = new EnemyBullet(locationX, locationY, bulletSpeedX, bulletSpeedY, power);
            }
            res.add(bullet);
        }
        return res;
    }
}