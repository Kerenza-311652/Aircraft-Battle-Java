package edu.hitsz.application;

import javafx.scene.image.Image;

public class EasyGame extends Game {

    public EasyGame() {
        super();
        this.difficulty = 1;
    }

    @Override
    protected void updateDifficulty() {
    }

    @Override
    protected void generateBoss() {
    }

    @Override
    protected Image getBackgroundImage() {
        return ImageManager.BACKGROUND_IMAGE_EASY;
    }
}