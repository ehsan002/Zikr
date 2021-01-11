package com.ehsanrc.zikr_update.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DuaDAO {

    @Insert
    List<Long> insertAll(Dua... duas);

    @Insert
    void insert(Dua dua);

    @Delete
    void delete(Dua dua);

    @Update
    void update(Dua dua);

    @Query("SELECT * FROM dua_table")
    LiveData<List<Dua>> getAllDuas();

    @Query("SELECT * FROM dua_table WHERE id = :duaId")
    Dua getDua(int duaId);

    @Query("DELETE FROM dua_table")
    void deleteAllDuas();

    @Query("SELECT * FROM dua_table WHERE favorite = 1 ORDER BY id DESC")
    LiveData<List<Dua>> getFavorites();

}
