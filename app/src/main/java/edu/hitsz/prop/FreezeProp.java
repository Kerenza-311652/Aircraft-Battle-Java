package edu.hitsz.prop;

import edu.hitsz.application.ImageManager;

/**
 * 冰冻道具
 */
public class FreezeProp extends AbstractProp {

    public FreezeProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        this.image = ImageManager.PROP_FREEZE_IMAGE;
    }

    @Override
    public void active() {
        // Todo: 后续迭代实现冰冻减速逻辑
        System.out.println("FreezeProp active!");
    }
}