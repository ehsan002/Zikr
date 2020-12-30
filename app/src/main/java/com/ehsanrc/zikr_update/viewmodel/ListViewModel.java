package com.ehsanrc.zikr_update.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ehsanrc.zikr_update.model.Dua;
import com.ehsanrc.zikr_update.model.DuaDAO;
import com.ehsanrc.zikr_update.model.DuaDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListViewModel extends AndroidViewModel {

    public MutableLiveData<List<Dua>> duas = new MutableLiveData<List<Dua>>();
    public MutableLiveData<Boolean> error = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    private AsyncTask<List<Dua>, Void, List<Dua>> insertTask;
    private AsyncTask<Void, Void, List<Dua>> retrieveTask;

    public ListViewModel(@NonNull Application application) {
        super(application);
    }



    public void refresh(List<Dua> duaList){

        insertTask = new InsertDuaTask();
        insertTask.execute(duaList);

        loading.setValue(true);
        retrieveTask = new RetrieveDuasTask();
        retrieveTask.execute();
    }

    private void duaRetrieved(List<Dua> duaList) {
        duas.setValue(duaList);
        loading.setValue(false);
        error.setValue(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (insertTask != null){

            insertTask.cancel(true);
            insertTask = null;

        }

        if (retrieveTask != null){

            retrieveTask.cancel(true);
            retrieveTask = null;

        }
    }

    private class InsertDuaTask extends AsyncTask<List<Dua>, Void, List<Dua>>{

        @Override
        protected List<Dua> doInBackground(List<Dua>... lists) {
            List<Dua> list = lists[0];
            DuaDAO duaDAO = DuaDatabase.getInstance(getApplication()).duaDAO();
            duaDAO.deleteAllDuas();

            ArrayList<Dua> duaList = new ArrayList<>(list);
            List<Long> result = duaDAO.insertAll(duaList.toArray(new Dua[0]));

            int i = 0;
            while (i<list.size()){
                list.get(i).id = result.get(i).intValue();
                i++;
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<Dua> duas) {
            duaRetrieved(duas);
        }
    }

    private class RetrieveDuasTask extends AsyncTask<Void, Void, List<Dua>>{


        @Override
        protected List<Dua> doInBackground(Void... voids) {

            return DuaDatabase.getInstance(getApplication()).duaDAO().getAllDuas();
        }

        @Override
        protected void onPostExecute(List<Dua> duas) {
            duaRetrieved(duas);
            Toast.makeText(getApplication(), "Duas retrieved from Database", Toast.LENGTH_SHORT).show();
        }
    }

    public
    List<Dua> addDuas(){

        Dua dua1 = new Dua("Dua One", "Dua One Arabic", "Dua One Pronunciation", "Dua One Translation");
        Dua dua2 = new Dua("Dua Two", "Dua two Arabic", "Dua One Pronunciation", "Dua One Translation");
        Dua dua3 = new Dua("Dua Three", "Dua three Arabic", "Dua One Pronunciation", "Dua One Translation");
        Dua dua4 = new Dua( "Dua Four", "Dua four Arabic", "Dua One Pronunciation", "Dua One Translation");
        Dua dua5 = new Dua( "Dua Five", "Dua five Arabic", "Dua One Pronunciation", "Dua One Translation");
        Dua dua6 = new Dua( "Dua Six", "Dua six Arabic", "Dua One Pronunciation", "Dua One Translation");
        Dua dua7 = new Dua( "Dua Seven", "Dua seven Arabic", "Dua One Pronunciation", "Dua One Translation");
        Dua dua8 = new Dua( "Dua Eight", "Dua eight Arabic", "Dua One Pronunciation", "Dua One Translation");
        Dua dua9 = new Dua( "Dua Nine", "Dua nine Arabic", "Dua One Pronunciation", "Dua One Translation");
        Dua dua10 = new Dua( "Dua Ten", "Dua ten Arabic", "Dua One Pronunciation", "Dua One Translation");

        ArrayList<Dua> myDuas = new ArrayList<>();
        myDuas.add(dua1);
        myDuas.add(dua2);
        myDuas.add(dua3);
        myDuas.add(dua4);
        myDuas.add(dua5);
        myDuas.add(dua6);
        myDuas.add(dua7);
        myDuas.add(dua8);
        myDuas.add(dua9);
        myDuas.add(dua10);

        return myDuas;
    }
}