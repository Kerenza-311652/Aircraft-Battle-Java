package edu.hitsz.observer;

/**
 * 抽象观察者接口
 * 接收炸弹和冰冻道具生效时的通知
 */
public interface Observer {
    void updateBomb();

    void updateFreeze();
}