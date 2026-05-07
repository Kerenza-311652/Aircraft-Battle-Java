package edu.hitsz.factory;

import edu.hitsz.prop.*;

/**
 * 道具的简单工厂类
 * 负责集中创建所有种类的道具
 */
public class PropFactory {
    public static AbstractProp createProp(String propType, int locationX, int locationY, int speedX, int speedY) {
        switch (propType) {
            case "Blood":
                return new BloodProp(locationX, locationY, speedX, speedY);
            case "Fire":
                return new FireProp(locationX, locationY, speedX, speedY);
            case "SuperFire":
                return new SuperFireProp(locationX, locationY, speedX, speedY);
            case "Bomb":
                return new BombProp(locationX, locationY, speedX, speedY);
            case "Freeze":
                return new FreezeProp(locationX, locationY, speedX, speedY);
            default:
                System.out.println("未知的道具类型: " + propType);
                return null;
        }
    }
}