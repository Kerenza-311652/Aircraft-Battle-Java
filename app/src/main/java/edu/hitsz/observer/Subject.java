package edu.hitsz.observer;

/**
 * 抽象观察目标接口
 * 负责管理观察者，并发送通知
 */
public interface Subject {
    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
}