package com.ehsanrc.zikr_update.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ehsanrc.zikr_update.model.Dua;
import com.ehsanrc.zikr_update.model.DuaDatabase;

public class DetailsViewModel extends AndroidViewModel {

    public MutableLiveData<Dua> duaMutableLiveData = new MutableLiveData<>();
    AsyncTask<Integer, Void, Dua> relieveTask;

    public DetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public void fetch(int id){

        Log.i("Test", "Fetch is called");
        relieveTask = new RetrieveDua();
        relieveTask.execute(id);

    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (relieveTask != null){
            relieveTask.cancel(true);
            relieveTask = null;
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

}
