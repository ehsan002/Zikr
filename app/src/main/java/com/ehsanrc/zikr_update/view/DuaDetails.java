package com.ehsanrc.zikr_update.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ehsanrc.zikr_update.R;
import com.ehsanrc.zikr_update.model.Dua;
import com.ehsanrc.zikr_update.viewmodel.DetailsViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DuaDetails extends Fragment {

    private DetailsViewModel detailsViewModel;

    private int duaId;

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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null){
            duaId = DuaDetailsArgs.fromBundle(getArguments()).getDuaId();
        }

        detailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        detailsViewModel.fetch(duaId);

        observeViewModel();
    }

    private void observeViewModel() {

        detailsViewModel.duaMutableLiveData.observe(getViewLifecycleOwner(), dua -> {

            if (dua != null && dua instanceof Dua){

                Log.i("Test", "Inside observe");
                tvDuaName.setText(dua.duaTitle);
                tvDuaArabic.setText(dua.duaArabic);
                tvDuaPronunciation.setText(dua.duaPronunciation);
                tvDuaTranslation.setText(dua.duaTranslation);
            }
        });

    }
}