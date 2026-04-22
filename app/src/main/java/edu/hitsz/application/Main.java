package edu.hitsz.application;

import edu.hitsz.ui.StartMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 程序入口
 * @author hitsz & doctxing
 */
public class Main extends Application {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    public static Stage stage;

    public static void main(String[] args) {
        System.out.println("Aircraft War Start!");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Main.stage = primaryStage;

        primaryStage.setTitle("Aircraft War");
        primaryStage.setResizable(false);

        StartMenu menu = new StartMenu(primaryStage);
        primaryStage.setScene(menu.getScene());

        primaryStage.show();
    }


    public static void switchToGame(int difficulty) {
        Game game;

        switch (difficulty) {
            case 1:
                game = new EasyGame();
                break;
            case 2:
                game = new MediumGame();
                break;
            case 3:
                game = new HardGame();
                break;
            default:
                game = new EasyGame();
        }

        Scene gameScene = new Scene(game, WINDOW_WIDTH, WINDOW_HEIGHT);

        stage.setScene(gameScene);

        game.action();
    }
}