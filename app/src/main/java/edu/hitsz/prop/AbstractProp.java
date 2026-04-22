package edu.hitsz.prop;

import edu.hitsz.basic.AbstractFlyingObject;

/**
 * 道具的抽象父类
 * 继承自所有飞行物的最顶层父类 AbstractFlyingObject
 */
public abstract class AbstractProp extends AbstractFlyingObject {

    public AbstractProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void forward() {
        super.forward();
        // 道具默认向下匀速直线运动
        locationY += speedY;
        // 判定 y 轴出界 (768 为 Main.WINDOW_HEIGHT)
        if (locationY >= 768) {
            vanish();
        }
    }

    /**
     * 道具生效的方法（抽象方法，留给具体的子类去实现）
     */
    public abstract void active();
}