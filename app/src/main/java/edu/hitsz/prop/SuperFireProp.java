package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;

/**
 * 超级火力道具
 */
public class SuperFireProp extends AbstractProp {

    public SuperFireProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        this.image = ImageManager.PROP_SUPER_FIRE_IMAGE;
    }

    @Override
    public void active() {
        HeroAircraft hero = HeroAircraft.getInstance();
        hero.setShootNum(20);
        hero.setShootStrategy(new edu.hitsz.strategy.RingShoot());
        System.out.println("FirePlusSupply active!");
    }
}