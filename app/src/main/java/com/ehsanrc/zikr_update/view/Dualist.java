package com.ehsanrc.zikr_update.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ehsanrc.zikr_update.R;
import com.ehsanrc.zikr_update.model.Dua;
import com.ehsanrc.zikr_update.viewmodel.ListViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Dualist extends Fragment {

    private ListViewModel listViewModel;
    private DuaListAdapter adapter = new DuaListAdapter(new ArrayList<>());

    @BindView(R.id.recyclerViewDuaList)
    RecyclerView recyclerView;

    public Dualist() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dualist, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        listViewModel.refresh(listViewModel.addDuas());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        observeViewModel();

    }

    private void observeViewModel(){

        listViewModel.duas.observe(getViewLifecycleOwner(), duas -> {
            if (duas != null && duas instanceof  List){
                adapter.updateList(duas);
            }
        });

    }
}
