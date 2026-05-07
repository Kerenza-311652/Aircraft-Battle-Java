package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import java.util.List;

/**
 * 攻击方式接口
 */
public interface ShootStrategy {

    /**
     * 执行射击动作
     * @param aircraft 执行开火的飞机对象，策略可以直接从飞机身上获取所需的属性
     * @return 产生的子弹列表
     */
    List<BaseBullet> doShoot(AbstractAircraft aircraft);
}