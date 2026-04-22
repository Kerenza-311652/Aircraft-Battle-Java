package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.strategy.ScatterShoot;

/**
 * 火力道具
 */
public class FireProp extends AbstractProp {

    public FireProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        this.image = ImageManager.PROP_FIRE_IMAGE;
    }

    @Override
    public void active() {
        HeroAircraft hero = HeroAircraft.getInstance();
        hero.setShootNum(3);
        hero.setShootStrategy(new ScatterShoot());
        System.out.println("FireSupply active!");
    }
}