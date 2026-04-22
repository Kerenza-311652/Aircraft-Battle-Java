package edu.hitsz.application;

import javafx.scene.image.Image;

public class MediumGame extends Game {

    public MediumGame() {
        super();
        this.difficulty = 2;
    }

    @Override
    protected Image getBackgroundImage() {
        return ImageManager.BACKGROUND_IMAGE_MEDIUM;
    }
}