package edu.hitsz.record;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 得分记录的数据访问实现类
 * 负责将数据持久化到本地 txt 文件中
 */
public class RecordDaoImpl implements RecordDao {

    private List<Record> records;
    private File dataFile;

    public RecordDaoImpl(int difficulty) {
        records = new ArrayList<>();

        String fileName = "records_easy.txt";
        if (difficulty == 2) fileName = "records_normal.txt";
        else if (difficulty == 3) fileName = "records_hard.txt";

        dataFile = new File("data/" + fileName);

        try {
            if (!dataFile.getParentFile().exists()) {
                dataFile.getParentFile().mkdirs();
            }
            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        records.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String pName = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    String time = parts[2];
                    records.add(new Record(pName, score, time));
                }
            }
        } catch (Exception e) {
            System.out.println("读取记录文件失败！");
            e.printStackTrace();
        }
    }

    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            for (Record record : records) {
                writer.write(record.getPName() + "," + record.getScore() + "," + record.getRecordTime());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("保存记录文件失败！");
            e.printStackTrace();
        }
    }

    @Override
    public List<Record> getAllRecords() {
        records.sort(new Comparator<Record>() {
            @Override
            public int compare(Record r1, Record r2) {
                return r2.getScore() - r1.getScore();
            }
        });
        return records;
    }

    @Override
    public void doAdd(Record record) {
        records.add(record);
        saveData();
    }

    @Override
    public void doDelete(Record record) {
        records.remove(record);
        saveData();
        System.out.println("成功从文件中删除记录：" + record.getPName());
    }
}