package com.ruilin.caipiao.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PasswordDao {

    @Query("SELECT COUNT(*) FROM " + Password.TABLE_NAME + " WHERE " + Password.COLUMN_ID + " = :id")
    int hasKey(long id);

    @Insert
    long insert(Password pwd);

    @Update
    int update(Password pwd);

    @Query("SELECT * FROM " + Password.TABLE_NAME)
    List<Password> selectAll();

    @Query("SELECT * FROM " + Password.TABLE_NAME + " WHERE " + Password.COLUMN_ID + " = :id")
    Password selectById(long id);

    @Query("DELETE FROM " + Password.TABLE_NAME + " WHERE " + Password.COLUMN_ID + " = :id")
    int deleteById(long id);
}
