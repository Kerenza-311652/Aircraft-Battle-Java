package edu.hitsz.application;

import edu.hitsz.factory.BossFactory;
import edu.hitsz.factory.EnemyFactory;
import javafx.scene.image.Image;

public class NormalGame extends Game {

    private int timeCount = 0;

    public NormalGame() {
        super();
        this.difficulty = 2;
    }

    @Override
    protected void updateDifficulty() {
        timeCount += timeInterval;

        if (timeCount >= 10000) {
            timeCount = 0;

            if (this.enemySpawnCycle > 10) {
                this.enemySpawnCycle -= 0.5;
            }
            this.eliteProb += 0.01;
            this.enemyPropertyRate += 0.02;

            System.out.printf("提高难度！精英机概率:%.2f,敌机周期:%.2f, 敌机属性提升倍率:%.2f.\n",
                    eliteProb, enemySpawnCycle, enemyPropertyRate);

        }
    }

    @Override
    protected void generateBoss() {
        if (score >= bossScoreThreshold && !bossExisting) {
            bossExisting = true;
            bossScoreThreshold += 500;

            EnemyFactory bossFactory = new BossFactory();
            enemyAircrafts.add(bossFactory.createEnemy());

            System.out.println("产生BOSS敌机");
            MusicManager.playBossBGM();
        }
    }

    @Override
    protected Image getBackgroundImage() {
        return ImageManager.BACKGROUND_IMAGE_NORMAL;
    }
}