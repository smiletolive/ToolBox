package com.ruilin.caipiao.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = Info.TABLE_NAME)
public class Info {

    public static final String TABLE_NAME = "info";

    public static final String COLUMN_ID = "id";
    public static final int TYPE_FULI = 0;
    public static final int TYPE_TIYU = 1;
    /**
     * 类型 (0-福利，1-体彩)
     */
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_ONE = "one";
    public static final String COLUMN_TWO = "two";
    public static final String COLUMN_THREE = "three";
    public static final String COLUMN_FOUR = "four";
    public static final String COLUMN_FIVE = "five";
    public static final String COLUMN_SIX = "six";
    public static final String COLUMN_SEVEN = "seven";
    public static final String COLUMN_time = "time";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Info.COLUMN_ID)
    public long id;
    @ColumnInfo(name = Info.COLUMN_TYPE)
    private int type;
    @ColumnInfo(name = Info.COLUMN_ONE)
    private int one;
    @ColumnInfo(name = Info.COLUMN_TWO)
    private int two;
    @ColumnInfo(name = Info.COLUMN_THREE)
    private int thee;
    @ColumnInfo(name = Info.COLUMN_FOUR)
    private int four;
    @ColumnInfo(name = Info.COLUMN_FIVE)
    private int five;
    @ColumnInfo(name = Info.COLUMN_SIX)
    private int six;
    @ColumnInfo(name = Info.COLUMN_SEVEN)
    private int seven;
    @ColumnInfo(name = Info.COLUMN_time)
    private long time;

    @Ignore
    private boolean isNew = false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOne() {
        return one;
    }

    public void setOne(int one) {
        this.one = one;
    }

    public int getTwo() {
        return two;
    }

    public void setTwo(int two) {
        this.two = two;
    }

    public int getThee() {
        return thee;
    }

    public void setThee(int thee) {
        this.thee = thee;
    }

    public int getFour() {
        return four;
    }

    public void setFour(int four) {
        this.four = four;
    }

    public int getFive() {
        return five;
    }

    public void setFive(int five) {
        this.five = five;
    }

    public int getSix() {
        return six;
    }

    public void setSix(int six) {
        this.six = six;
    }

    public int getSeven() {
        return seven;
    }

    public void setSeven(int seven) {
        this.seven = seven;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isNew() {
        return getId() == 0;
    }
}
