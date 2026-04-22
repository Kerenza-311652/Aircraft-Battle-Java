package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;

/**
 * 加血道具
 */
public class BloodProp extends AbstractProp {

    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        this.image = ImageManager.PROP_BLOOD_IMAGE;
    }

    @Override
    public void active() {
        // Todo: 后续迭代实现具体的加血逻辑
        HeroAircraft hero = HeroAircraft.getInstance();
        hero.setHp(hero.getHp() + 20);

        System.out.println("BloodProp active!");
    }


}