package edu.hitsz.ui;

import edu.hitsz.application.Main;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * 游戏主菜单界面
 */
public class StartMenu {

    private VBox root;
    private Scene scene;

    public StartMenu(Stage primaryStage) {
        root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #E0F7FA;");

        Label titleLabel = new Label("飞机大战");
        titleLabel.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 36));
        titleLabel.setStyle("-fx-text-fill: #006064;");

        String btnStyle = "-fx-font-size: 18px; -fx-pref-width: 200px; -fx-pref-height: 40px; " +
                "-fx-background-color: #00BCD4; -fx-text-fill: white; -fx-background-radius: 10;";

        Button btnEasy = new Button("简单模式 (EASY)");
        btnEasy.setStyle(btnStyle);

        Button btnMedium = new Button("普通模式 (NORMAL)");
        btnMedium.setStyle(btnStyle);

        Button btnHard = new Button("困难模式 (HARD)");
        btnHard.setStyle(btnStyle);

        btnEasy.setOnAction(e -> startGame(primaryStage, 1));
        btnMedium.setOnAction(e -> startGame(primaryStage, 2));
        btnHard.setOnAction(e -> startGame(primaryStage, 3));

        root.getChildren().addAll(titleLabel, btnEasy, btnMedium, btnHard);

        scene = new Scene(root, 512, 768);
    }

    public Scene getScene() {
        return scene;
    }

    /**
     * 开始游戏逻辑
     * @param difficulty 难度：1-简单，2-普通，3-困难
     */
    private void startGame(Stage stage, int difficulty) {
        System.out.println("玩家点击了开始！难度: " + difficulty);

        Main.switchToGame(difficulty);
    }
}