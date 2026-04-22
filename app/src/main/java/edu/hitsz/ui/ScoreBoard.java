package edu.hitsz.ui;

import edu.hitsz.record.Record;
import edu.hitsz.record.RecordDao;
import edu.hitsz.record.RecordDaoImpl;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public class ScoreBoard {
    private Stage stage;
    private RecordDao recordDao;
    private ObservableList<Record> recordData;
    private TableView<Record> table;

    // 【修改】：要求传入当前难度
    public ScoreBoard(int difficulty) {
        this.recordDao = new RecordDaoImpl(difficulty);

        stage = new Stage();
        stage.setTitle("得分排行榜");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        // 【新增】：根据难度显示不同的标题
        String modeName = difficulty == 1 ? "EASY" : (difficulty == 2 ? "NORMAL" : "HARD");
        Label titleLabel = new Label("难度: " + modeName + "           排行榜 ");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #1A237E;");

        table = new TableView<>();

        TableColumn<Record, Integer> rankCol = new TableColumn<>("排名");
        rankCol.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(table.getItems().indexOf(cellData.getValue()) + 1));
        rankCol.setPrefWidth(50);

        TableColumn<Record, String> nameCol = new TableColumn<>("玩家姓名");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPName()));

        TableColumn<Record, Integer> scoreCol = new TableColumn<>("最终得分");
        scoreCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getScore()).asObject());

        TableColumn<Record, String> timeCol = new TableColumn<>("记录时间");
        timeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRecordTime()));

        table.getColumns().addAll(rankCol, nameCol, scoreCol, timeCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // 初始装载数据
        recordData = FXCollections.observableArrayList(recordDao.getAllRecords());
        table.setItems(recordData);

        Button deleteBtn = new Button("🗑️ 删除选中记录");
        deleteBtn.setStyle("-fx-font-size: 14px; -fx-background-color: #EF5350; -fx-text-fill: white; -fx-cursor: hand;");

        deleteBtn.setOnAction(e -> {
            Record selectedRecord = table.getSelectionModel().getSelectedItem();
            if (selectedRecord != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("确认删除");
                alert.setHeaderText(null);
                alert.setContentText("是否确定删除玩家 [" + selectedRecord.getPName() + "] 的记录？");

                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        recordDao.doDelete(selectedRecord);
                        recordData.remove(selectedRecord);
                    }
                });
            } else {
                new Alert(Alert.AlertType.WARNING, "请先在表格中点击选中一条记录！").show();
            }
        });

        root.getChildren().addAll(titleLabel, table, deleteBtn);
        Scene scene = new Scene(root, 450, 500);
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }

    public void refreshData() {
        recordDao.loadData();
        List<Record> latestRecords = recordDao.getAllRecords();
        recordData.setAll(latestRecords);
        table.refresh();
    }

    public void addRecordAndRefresh(Record newRecord) {
        recordDao.doAdd(newRecord);

        recordData.add(newRecord);

        javafx.collections.FXCollections.sort(recordData, (r1, r2) -> r2.getScore() - r1.getScore());

        table.refresh();
    }
}