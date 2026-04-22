package edu.hitsz.prop;

import edu.hitsz.application.ImageManager;

/**
 * 炸弹道具
 */
public class BombProp extends AbstractProp {

    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        this.image = ImageManager.PROP_BOMB_IMAGE;
    }

    @Override
    public void active() {
        // Todo: 后续迭代实现清屏炸弹逻辑
        System.out.println("BombProp active!");
    }
}