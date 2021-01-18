package com.ehsanrc.zikr.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ehsanrc.zikr.R;
import com.ehsanrc.zikr.model.Dua;
import com.ehsanrc.zikr.viewmodel.ListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Dualist extends Fragment {

    public ListViewModel listViewModel;
    private final DuaListAdapter adapter = new DuaListAdapter(new ArrayList<>(), getClass().toString());

    @BindView(R.id.recyclerViewDuaList)
    RecyclerView recyclerView;

    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;

    public Dualist() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dualist, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);

        setHasOptionsMenu(true);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listViewModel = ViewModelProviders.of(this).get(ListViewModel.class);

        if (getArguments()!=null){
            int flag = DualistArgs.fromBundle(getArguments()).getFlagFromEdit();

            String title;
            String arabic;
            String pronunciation;
            String translation;
            if (flag == AddDua.INSERT_DUA){
                title = DualistArgs.fromBundle(getArguments()).getDuaTitle();
                arabic = DualistArgs.fromBundle(getArguments()).getDuaArabic();
                pronunciation = DualistArgs.fromBundle(getArguments()).getDuaPronunciation();
                translation = DualistArgs.fromBundle(getArguments()).getDuaTranslation();
                getArguments().clear();

                Dua dua = new Dua(title, arabic, pronunciation, translation);
                listViewModel.insertSingleDua(dua);
            }else if (flag > 0){
                title = DualistArgs.fromBundle(getArguments()).getDuaTitle();
                arabic = DualistArgs.fromBundle(getArguments()).getDuaArabic();
                pronunciation = DualistArgs.fromBundle(getArguments()).getDuaPronunciation();
                translation = DualistArgs.fromBundle(getArguments()).getDuaTranslation();

                Dua dua = new Dua(title, arabic, pronunciation, translation);
                dua.setId(flag);
                dua.setFavorite(DualistArgs.fromBundle(getArguments()).getFavorite());
                listViewModel.updateDua(dua);
            }
        }

        observeViewModel();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> {
            NavDirections action = DualistDirections.actionAddDua();
            Navigation.findNavController(fabAdd).navigate(action);
        });

    }

    private void observeViewModel() {

        listViewModel.refresh().observe(getViewLifecycleOwner(), duas -> {
            if (duas != null) {
                adapter.updateList(duas);
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.dua_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.list_menu_item_favorite:
                NavDirections action = DualistDirections.actionFavorites();
                Navigation.findNavController(fabAdd).navigate(action);
                return true;
            case R.id.list_menu_item_about:
                Navigation.findNavController(fabAdd).navigate(DualistDirections.actionAbout());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
