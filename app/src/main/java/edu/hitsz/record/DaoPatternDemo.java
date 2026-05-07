package edu.hitsz.record;

import java.util.List;

/**
 * DAO 模式测试类
 * 用于脱离游戏主逻辑，单独测试文件读写和排行榜排序功能
 */
public class DaoPatternDemo {

    public static void main(String[] args) {
        RecordDao recordDao = new RecordDaoImpl(1);

        System.out.println("========== 1. 当前初始排行榜 ==========");
        printRecords(recordDao.getAllRecords());

        System.out.println("\n========== 2. 模拟添加三条新记录 ==========");
        recordDao.doAdd(new Record("玩家A", 150, "2026-04-15 10:00"));
        recordDao.doAdd(new Record("大佬B", 500, "2026-04-15 10:05"));
        recordDao.doAdd(new Record("菜鸟C", 50,  "2026-04-15 10:10"));

        System.out.println("添加完成！最新的排行榜 ：");
        printRecords(recordDao.getAllRecords());

        System.out.println("\n========== 3. 模拟删除榜首记录 ==========");
        List<Record> currentRecords = recordDao.getAllRecords();
        if (!currentRecords.isEmpty()) {
            Record topRecord = currentRecords.get(0);
            System.out.println("准备删除榜首：" + topRecord.getPName());
            recordDao.doDelete(topRecord);
        } else {
            System.out.println("排行榜为空，无可删除的数据。");
        }

        System.out.println("删除后最新的排行榜：");
        printRecords(recordDao.getAllRecords());
    }

    private static void printRecords(List<Record> records) {
        if (records.isEmpty()) {
            System.out.println("排行榜空空如也~");
            return;
        }
        for (int i = 0; i < records.size(); i++) {
            System.out.println("第 " + (i + 1) + " 名 -> " + records.get(i));
        }
    }
}