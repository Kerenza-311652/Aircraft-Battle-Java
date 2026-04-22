package edu.hitsz.factory;

import edu.hitsz.prop.*;

/**
 * 道具的简单工厂类
 * 负责集中创建所有种类的道具
 */
public class PropFactory {

    /**
     * 静态工厂方法
     * @param propType 道具的类型标识（如 "Blood", "Fire", "SuperFire"）
     * @param locationX x坐标
     * @param locationY y坐标
     * @param speedX x方向速度
     * @param speedY y方向速度
     * @return 具体的道具实例，如果类型不匹配则返回null
     */
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