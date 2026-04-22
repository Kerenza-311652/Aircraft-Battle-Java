package edu.hitsz.application;

import javafx.scene.image.Image;

public class EasyGame extends Game {

    public EasyGame() {
        super();
        this.difficulty = 1;
        // 可以在这里修改简单模式特有的参数
        // 比如：this.enemySpawnCycle = 30; // 降低敌机生成频率
    }

    @Override
    protected Image getBackgroundImage() {
        return ImageManager.BACKGROUND_IMAGE_EASY;
    }
}