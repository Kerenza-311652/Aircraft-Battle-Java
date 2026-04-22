package edu.hitsz.record;

import java.util.List;

/**
 * 得分记录的数据访问接口
 * 对排行榜数据进行操作的标准规范
 */
public interface RecordDao {

    void loadData();

    List<Record> getAllRecords();

    void doAdd(Record record);

    void doDelete(Record record);

}