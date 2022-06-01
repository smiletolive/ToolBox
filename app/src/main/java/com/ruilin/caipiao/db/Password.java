package com.ruilin.caipiao.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Password.TABLE_NAME)
public class Password {

    public static final String TABLE_NAME = "password";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_FOLDER = "folder";
    public static final String COLUMN_ACCOUNT = "account";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_BANK_NUMBER = "bank_number";
    public static final String COLUMN_BANK_DATA = "bank_date";
    public static final String COLUMN_DATE = "date";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Password.COLUMN_ID)
    private long id;
    @ColumnInfo(name = Password.COLUMN_NAME)
    private String name;
    @ColumnInfo(name = Password.COLUMN_TYPE)
    private int type;
    @ColumnInfo(name = Password.COLUMN_FOLDER)
    private String folder;
    @ColumnInfo(name = Password.COLUMN_ACCOUNT)
    private String account;
    @ColumnInfo(name = Password.COLUMN_PASSWORD)
    private String password;
    @ColumnInfo(name = Password.COLUMN_BANK_NUMBER)
    private String bankNumber;
    @ColumnInfo(name = Password.COLUMN_BANK_DATA)
    private String bankDate;
    @ColumnInfo(name = Password.COLUMN_DATE)
    private String date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankDate() {
        return bankDate;
    }

    public void setBankDate(String bankDate) {
        this.bankDate = bankDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
