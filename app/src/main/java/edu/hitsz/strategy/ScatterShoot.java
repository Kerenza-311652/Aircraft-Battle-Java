package edu.hitsz.strategy;

import edu.hitsz.bullet.*;
import java.util.LinkedList;
import java.util.List;

public class ScatterShoot implements ShootStrategy {
    @Override
    public List<BaseBullet> doShoot(int locationX, int locationY, int speedX, int speedY, int direction, int power, int shootNum) {
        List<BaseBullet> res = new LinkedList<>();
        int x = locationX;
        int y = locationY + direction * 2;
        int bulletSpeedY = speedY + direction * 8;
        for (int i = 0; i < shootNum; i++) {
            int bulletSpeedX = (i - shootNum / 2) * 2;
            BaseBullet bullet;
            if (direction == -1) {
                bullet = new HeroBullet(x + (i - shootNum / 2) * 10, y, bulletSpeedX, bulletSpeedY, power);
            } else {
                bullet = new EnemyBullet(x + (i - shootNum / 2) * 10, y, bulletSpeedX, bulletSpeedY, power);
            }
            res.add(bullet);
        }
        return res;
    }
}