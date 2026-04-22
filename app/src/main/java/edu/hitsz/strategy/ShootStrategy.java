package edu.hitsz.strategy;

import edu.hitsz.bullet.BaseBullet;
import java.util.List;

/**
 * 射击策略接口
 * 定义了所有射击行为的通用规范
 */
public interface ShootStrategy {

    /**
     * 执行射击
     * * @param locationX 飞机的中心 x 坐标
     * @param locationY 飞机的中心 y 坐标
     * @param speedX    飞机的 x 速度 (有时子弹速度受飞机速度影响)
     * @param speedY    飞机的 y 速度
     * @param direction 射击方向 (向上发射为 -1，向下发射为 1)
     * @param power     子弹单发伤害
     * @param shootNum  单次发射的子弹数量
     * @return 包含所有发射出子弹的列表
     */
    List<BaseBullet> doShoot(int locationX, int locationY, int speedX, int speedY, int direction, int power, int shootNum);
}