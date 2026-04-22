package edu.hitsz.application;

import javafx.scene.image.Image;

public class HardGame extends Game {

    public HardGame() {
        super();
        this.difficulty = 3;
    }

    @Override
    protected Image getBackgroundImage() {
        return ImageManager.BACKGROUND_IMAGE_HARD;
    }
}