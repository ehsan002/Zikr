package com.ehsanrc.zikr.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ehsanrc.zikr.R;
import com.ehsanrc.zikr.viewmodel.ListViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Favorites extends Fragment {

    public ListViewModel listViewModel;
    private DuaListAdapter adapter = new DuaListAdapter(new ArrayList<>(), getClass().toString());

    @BindView(R.id.recyclerViewFavorites)
    RecyclerView recyclerView;

    public Favorites() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        ButterKnife.bind(this, view);


        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("আমার দোয়াসমূহ");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listViewModel = ViewModelProviders.of(this).get(ListViewModel.class);

        observeViewModel();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void observeViewModel() {

        listViewModel.getFavoriteDuas().observe(getViewLifecycleOwner(), duas -> {
            if (duas != null) {
                adapter.updateList(duas);
            }
        });

    }
}