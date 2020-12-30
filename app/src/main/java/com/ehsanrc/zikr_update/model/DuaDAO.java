package com.ehsanrc.zikr_update.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DuaDAO {

    @Insert
    List<Long> insertAll(Dua... duas);

    @Query("SELECT * FROM dua_table")
    List<Dua> getAllDuas();

    @Query("SELECT * FROM dua_table WHERE id = :duaId")
    Dua getDua(int duaId);

    @Query("DELETE FROM dua_table")
    void deleteAllDuas();

}
