package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;

/**
 * 敌机工厂接口
 */
public interface EnemyFactory {
    /**
     * 创建敌机的方法
     * @return 具体的敌机实例
     */
    AbstractAircraft createEnemy();
}