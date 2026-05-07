package edu.hitsz.application;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.factory.BossFactory;
import edu.hitsz.factory.EnemyFactory;
import javafx.scene.image.Image;


public class HardGame extends Game {

    private int timeCount = 0;
    private int bossCount = 0;

    public HardGame() {
        super();
        this.difficulty = 3;
    }

    @Override
    protected void updateDifficulty() {
        timeCount += timeInterval;

        if (timeCount >= 10000) {
            timeCount = 0;

            if (this.enemySpawnCycle > 6) {
                this.enemySpawnCycle -= 0.8;
            }

            if (this.shootCycle > 15) {
                this.shootCycle -= 0.5;
            }
            this.eliteProb += 0.02;
            this.enemyPropertyRate += 0.04;

            System.out.printf("提高难度！精英机概率:%.2f,敌机周期:%.2f, 敌机属性提升倍率:%.2f.\n",
                    eliteProb, enemySpawnCycle, enemyPropertyRate);
        }
    }

    @Override
    protected void generateBoss() {
        if (score >= bossScoreThreshold && !bossExisting) {
            bossExisting = true;
            bossScoreThreshold += 500;
            bossCount++;

            if (bossCount > 1) {
                this.bossHpRate += 0.10;
            }

            EnemyFactory bossFactory = new BossFactory();
            AbstractAircraft boss = bossFactory.createEnemy();

            if (bossHpRate > 1.0) {
                int currentHp = boss.getHp();
                int extraHp = (int) (currentHp * (bossHpRate - 1));
                if (extraHp > 0) {
                    boss.decreaseHp(-extraHp);
                }
            }

            enemyAircrafts.add(boss);

            System.out.println("产生BOSS敌机");
            System.out.printf("Boss敌机血量倍率:%.2f.\n", bossHpRate);

            MusicManager.playBossBGM();
        }
    }

    @Override
    protected Image getBackgroundImage() {
        return ImageManager.BACKGROUND_IMAGE_HARD;
    }
}