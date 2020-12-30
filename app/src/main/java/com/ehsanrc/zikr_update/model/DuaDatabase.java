package com.ehsanrc.zikr_update.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Dua.class}, version = 1)
public abstract class DuaDatabase extends RoomDatabase {

    private static DuaDatabase instance;
    public static DuaDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    DuaDatabase.class,
                    "dua_database")
                    .build();
        }
        return instance;
    }

    public abstract DuaDAO duaDAO();

}
