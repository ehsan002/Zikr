package com.ehsanrc.zikr.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.ehsanrc.zikr.R;
import com.ehsanrc.zikr.model.Dua;
import com.ehsanrc.zikr.viewmodel.DetailsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DuaDetails extends Fragment {

    private DetailsViewModel detailsViewModel;

    private boolean isFavorite;

    private int duaId, flag;

    private String title, arabic, pronunciation, translation;

    @BindView(R.id.duaName)
    TextView tvDuaName;

    @BindView(R.id.duaArabic)
    TextView tvDuaArabic;

    @BindView(R.id.duaPronunciation)
    TextView tvDuaPronunciation;

    @BindView(R.id.duaTranslation)
    TextView tvDuaTranslation;

    public DuaDetails() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dua_details, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("দোয়া");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        detailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);

        if (getArguments() != null){
            duaId = DuaDetailsArgs.fromBundle(getArguments()).getDuaId();
            flag = DuaDetailsArgs.fromBundle(getArguments()).getFlagFromEdit();
            isFavorite = DuaDetailsArgs.fromBundle(getArguments()).getFavorite();
        }

        if (flag != -1){
            title = DuaDetailsArgs.fromBundle(getArguments()).getTitle();
            arabic = DuaDetailsArgs.fromBundle(getArguments()).getArabic();
            pronunciation = DuaDetailsArgs.fromBundle(getArguments()).getPronunciation();
            translation = DuaDetailsArgs.fromBundle(getArguments()).getTranslation();

        }else {
            detailsViewModel.fetch(duaId);

            observeViewModel();
        }


    }

    private void observeViewModel() {

        detailsViewModel.duaMutableLiveData.observe(getViewLifecycleOwner(), dua -> {

            if (dua != null){
                tvDuaName.setText(dua.duaTitle);
                tvDuaArabic.setText(dua.duaArabic);
                tvDuaPronunciation.setText(dua.duaPronunciation);
                tvDuaTranslation.setText(dua.duaTranslation);

                title = dua.duaTitle;
                arabic = dua.duaArabic;
                pronunciation = dua.duaPronunciation;
                translation = dua.duaTranslation;
                isFavorite = dua.isFavorite;
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.details_menu, menu);

        MenuItem item = menu.findItem(R.id.menu_item_favorite);
        if (item != null) {
            if (isFavorite){
                item.setIcon(R.drawable.ic_favorite);
            }else item.setIcon(R.drawable.ic_make_favorite);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_delete:
                deleteAction();
                return true;

            case R.id.menu_item_edit:
                editAction();
                return true;

            case R.id.menu_item_favorite:
                favoriteAction(item);

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void editAction() {
        DuaDetailsDirections.ActionAddDuaFromdetails action2 = DuaDetailsDirections.actionAddDuaFromdetails();
        action2.setId(duaId);
        action2.setTitle(title);
        action2.setArabic(arabic);
        action2.setPronunciation(pronunciation);
        action2.setTranslation(translation);
        action2.setFavorite(isFavorite);

        Navigation.findNavController(getActivity(),R.id.fragment).navigate(action2);
    }

    private void favoriteAction(MenuItem item) {

        Dua dua2 = new Dua(title, arabic, pronunciation, translation);
        dua2.setId(duaId);
        dua2.setFavorite(isFavorite);
        detailsViewModel.updateFavorite(dua2);

        if (!isFavorite){
            dua2.setFavorite(true);
            isFavorite = true;
            item.setIcon(R.drawable.ic_favorite);
        }else{
            dua2.setFavorite(false);
            isFavorite = false;
            item.setIcon(R.drawable.ic_make_favorite);
        }

    }

    private void deleteAction() {

        new AlertDialog.Builder(getActivity())
                .setMessage("আপনি নিশ্চিত?")
                .setPositiveButton("হ্যা", (dialog, which) -> {
                    Dua dua = new Dua(title, arabic, pronunciation, translation);
                    dua.setId(duaId);
                    detailsViewModel.deleteDua(dua);

                    DuaDetailsDirections.ActionDualistFromDetails action = DuaDetailsDirections.actionDualistFromDetails();
                    Navigation.findNavController(getActivity(),R.id.fragment).navigate(action);
                })
                .setNegativeButton("না", (dialog, which) -> {

                })
                .create()
                .show();

    }
}