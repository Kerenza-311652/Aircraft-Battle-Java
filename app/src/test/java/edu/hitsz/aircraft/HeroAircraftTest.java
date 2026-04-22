package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    private HeroAircraft heroAircraft;

    @BeforeEach
    void setUp() {
        System.out.println("**--- 英雄机测试开始执行 ---**");
        heroAircraft = HeroAircraft.getInstance();
    }

    @AfterEach
    void tearDown() {
        System.out.println("**--- 英雄机单次测试结束 ---**\n");
    }

    @Test
    @DisplayName("测试碰撞检测机制 (crash)")
    void crash() {
        System.out.println("执行测试: crash()");

        MobEnemy closeEnemy = new MobEnemy(
                heroAircraft.getLocationX(),
                heroAircraft.getLocationY(),
                0, 0, 10
        );
        assertTrue(heroAircraft.crash(closeEnemy), "坐标重合，应该发生碰撞！");

        MobEnemy farEnemy = new MobEnemy(
                heroAircraft.getLocationX() + 1000,
                heroAircraft.getLocationY() + 1000,
                0, 0, 10
        );
        assertFalse(heroAircraft.crash(farEnemy), "距离过远，不应该发生碰撞！");
    }

    @Test
    @DisplayName("测试扣血及血量下限机制 (decreaseHp)")
    void decreaseHp() {
        System.out.println("执行测试: decreaseHp()");

        int initialHp = heroAircraft.getHp();

        heroAircraft.decreaseHp(10);
        assertEquals(initialHp - 10, heroAircraft.getHp(), "扣除10点血量计算错误");

        heroAircraft.decreaseHp(10000);
        assertEquals(0, heroAircraft.getHp(), "血量扣到0以下时，应该强制置为0");
    }

    @Test
    @DisplayName("测试射击机制 (shoot)")
    void shoot() {
        System.out.println("执行测试: shoot()");

        List<BaseBullet> bullets = heroAircraft.shoot();

        assertNotNull(bullets, "射击生成的子弹列表不应为 null");

        assertEquals(1, bullets.size(), "初始状态下单次射击应该生成 1 颗子弹");
    }
}