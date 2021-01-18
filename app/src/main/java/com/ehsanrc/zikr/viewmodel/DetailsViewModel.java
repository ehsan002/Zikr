package com.ehsanrc.zikr.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ehsanrc.zikr.model.Dua;
import com.ehsanrc.zikr.model.DuaDatabase;

public class DetailsViewModel extends AndroidViewModel {

    public MutableLiveData<Dua> duaMutableLiveData = new MutableLiveData<>();
    AsyncTask<Integer, Void, Dua> retrieveTask;
    AsyncTask<Dua, Void, Void> deleteTask;
    AsyncTask<Dua, Void, Void> updateFavoriteTask;

    public DetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public void fetch(int id){

        Log.i("Test", "Fetch is called");
        retrieveTask = new RetrieveDua();
        retrieveTask.execute(id);

    }

    public void deleteDua(Dua dua){
        deleteTask = new DeleteDuaTask();
        deleteTask.execute(dua);
    }

    public void updateFavorite(Dua dua){
        updateFavoriteTask = new UpdateFavorite();
        updateFavoriteTask.execute(dua);
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (retrieveTask != null){
            retrieveTask.cancel(true);
            retrieveTask = null;
        }

        if (deleteTask != null){
            deleteTask.cancel(true);
            deleteTask = null;
        }
    }

    private class RetrieveDua extends AsyncTask<Integer, Void, Dua>{


        @Override
        protected Dua doInBackground(Integer... integers) {

            int id = integers[0];

            Log.i("Test", "Doinbackground is called with id "+id);
            return DuaDatabase.getInstance(getApplication()).duaDAO().getDua(id);

        }

        @Override
        protected void onPostExecute(Dua dua) {
            duaMutableLiveData.setValue(dua);
        }
    }

    private class DeleteDuaTask extends AsyncTask<Dua, Void, Void>{

        @Override
        protected Void doInBackground(Dua... duas) {
            Dua dua = duas[0];
            DuaDatabase.getInstance(getApplication()).duaDAO().delete(dua);
            return null;
        }
    }

    private class UpdateFavorite extends AsyncTask<Dua, Void, Void>{

        @Override
        protected Void doInBackground(Dua... duas) {
            DuaDatabase.getInstance(getApplication()).duaDAO().update(duas[0]);
            return null;
        }
    }

}
