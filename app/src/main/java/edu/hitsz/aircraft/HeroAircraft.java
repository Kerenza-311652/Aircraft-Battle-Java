package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.application.ImageManager;
import edu.hitsz.strategy.DirectShoot;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    private volatile static HeroAircraft instance;

    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = 1;
        this.power = 30;
        this.direction = -1;
        this.setShootStrategy(new DirectShoot());
    }

    public static HeroAircraft getInstance() {
        if (instance == null) {
            synchronized (HeroAircraft.class) {
                if (instance == null) {
                    instance = new HeroAircraft(
                            Main.WINDOW_WIDTH / 2,
                            (int) (Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight()),
                            0,
                            0,
                            1000
                    );
                }
            }
        }
        return instance;
    }

    public void setHp(int hp) {
        this.hp = Math.min(hp, maxHp);
    }

    public int getMaxHp() {
        return maxHp;
    }

    @Override
    public void forward() {
    }
}