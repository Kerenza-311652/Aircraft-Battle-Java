package edu.hitsz.record;

/**
 * 得分记录类 (Value Object)
 * 存放数据的所有属性和方法
 */
public class Record {

    private String pName;
    private int score;
    private String recordTime;

    public Record(String pName, int score, String recordTime) {
        this.pName = pName;
        this.score = score;
        this.recordTime = recordTime;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    @Override
    public String toString() {
        return "玩家: " + pName + ",\t得分: " + score + ",\t时间: " + recordTime;
    }
}