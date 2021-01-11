package com.ehsanrc.zikr_update.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ehsanrc.zikr_update.model.Dua;
import com.ehsanrc.zikr_update.model.DuaDAO;
import com.ehsanrc.zikr_update.model.DuaDatabase;

import java.util.List;

public class ListViewModel extends AndroidViewModel {

    private AsyncTask<Dua, Void, Void> insertTask;
    private AsyncTask<Dua, Void, Void> updateTask;

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public void insertSingleDua(Dua dua) {
        insertTask = new InsertSingleDua();
        insertTask.execute(dua);
    }

    public void updateDua(Dua dua){
        updateTask = new UpdateDuaTask();
        updateTask.execute(dua);
    }

    public LiveData<List<Dua>> refresh() {

        Log.i("Test", "Refreshing\n");

        DuaDAO duaDAO = DuaDatabase.getInstance(getApplication()).duaDAO();

        return duaDAO.getAllDuas();
    }

    public LiveData<List<Dua>> getFavoriteDuas() {

        Log.i("Test", "Favorites\n");

        DuaDAO duaDAO = DuaDatabase.getInstance(getApplication()).duaDAO();

        return duaDAO.getFavorites();
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (insertTask != null) {
            insertTask.cancel(true);
            insertTask = null;
        }

        if (updateTask != null){
            updateTask.cancel(true);
            updateTask = null;
        }
    }

    private class InsertSingleDua extends AsyncTask<Dua, Void, Void> {

        @Override
        protected Void doInBackground(Dua... duas) {
            DuaDAO duaDAO = DuaDatabase.getInstance(getApplication()).duaDAO();
            duaDAO.insert(duas[0]);
            return null;
        }
    }

    private class UpdateDuaTask extends AsyncTask<Dua, Void, Void>{

        @Override
        protected Void doInBackground(Dua... duas) {
            DuaDatabase.getInstance(getApplication()).duaDAO().update(duas[0]);
            return null;
        }
    }
}