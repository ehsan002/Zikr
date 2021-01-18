package com.ehsanrc.zikr.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ehsanrc.zikr.viewmodel.ListViewModel;

import java.util.List;

@Database(entities = {Dua.class}, version = 2)
public abstract class DuaDatabase extends RoomDatabase {

    private static DuaDatabase instance;

    public static DuaDatabase getInstance(Context context){

        if (instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    DuaDatabase.class,
                    "dua_database")
                    .addCallback(roomCallback)
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return instance;
    }

    public abstract DuaDAO duaDAO();

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDbTask().execute();
        }
    };

    private static class PopulateDbTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            DuaDAO duaDAO = instance.duaDAO();
            duaDAO.deleteAllDuas();


            List<Dua> myDuas = ListViewModel.addDuas();

            for (Dua dua:myDuas){
                duaDAO.insert(dua);
            }
            return null;
        }
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(
                    "ALTER TABLE dua_table ADD COLUMN favorite INTEGER DEFAULT 0 NOT NULL");
        }
    };
}
