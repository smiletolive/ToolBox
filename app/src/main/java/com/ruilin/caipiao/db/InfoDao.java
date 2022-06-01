package com.ruilin.caipiao.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface InfoDao {
    @Query("SELECT COUNT(*) FROM " + Info.TABLE_NAME + " WHERE " + Info.COLUMN_ID + " = :id")
    int hasKey(long id);

    @Insert
    long insert(Info info);

    @Update
    int update(Info info);

    @Query("SELECT * FROM " + Info.TABLE_NAME)
    List<Info> selectAll();

    @Query("DELETE FROM " + Info.TABLE_NAME + " WHERE " + Info.COLUMN_ID + " = :id")
    int deleteById(long id);

    @Query("DELETE FROM " + Info.TABLE_NAME)
    int deleteAll();
}
