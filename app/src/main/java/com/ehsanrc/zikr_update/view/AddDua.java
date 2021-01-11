package com.ehsanrc.zikr_update.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.ehsanrc.zikr_update.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddDua extends Fragment {

    private int duaId;

    public static final int INSERT_DUA = -100;

    @BindView(R.id.etDuaTitle)
    EditText etDuaTitle;

    @BindView(R.id.etDuaArabic)
    EditText etDuaArabic;

    @BindView(R.id.etDuaPronunciation)
    EditText etDuaPronunciation;

    @BindView(R.id.etDuaTranslation)
    EditText etDuaTranslation;

    public AddDua() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        if (getArguments() != null){
            duaId = AddDuaArgs.fromBundle(getArguments()).getId();
            if (duaId != -1){
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("পরিবর্তন");
            }else {
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("নতুন দোয়া");
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_dua, container, false);

        ButterKnife.bind(this, view);

        if (duaId != -1){
            etDuaTitle.setText(AddDuaArgs.fromBundle(getArguments()).getTitle());
            etDuaArabic.setText(AddDuaArgs.fromBundle(getArguments()).getArabic());
            etDuaPronunciation.setText(AddDuaArgs.fromBundle(getArguments()).getPronunciation());
            etDuaTranslation.setText(AddDuaArgs.fromBundle(getArguments()).getTranslation());
        }

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_dua_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_item_save:
                saveDua();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void saveDua() {
        String title = String.valueOf(etDuaTitle.getText());
        String arabic = String.valueOf(etDuaArabic.getText());
        String pronunciation = String.valueOf(etDuaPronunciation.getText());
        String translation = String.valueOf(etDuaTranslation.getText());

        if(title.isEmpty()){
            new AlertDialog.Builder(getActivity())
                    .setMessage("দোয়া যোগ করুন")
                    .setNegativeButton("ওকে", (dialog, which) -> {

                    })
                    .create()
                    .show();
        }else{

            if (duaId != -1){
                AddDuaDirections.ActionDualist action = AddDuaDirections.actionDualist();
                action.setDuaTitle(title);
                action.setDuaArabic(arabic);
                action.setDuaPronunciation(pronunciation);
                action.setDuaTranslation(translation);
                action.setFlagFromEdit(duaId);
                action.setFavorite(AddDuaArgs.fromBundle(getArguments()).getFavorite());
                Navigation.findNavController(getActivity(), R.id.fragment).navigate(action);
            }else{
                AddDuaDirections.ActionDualist action = AddDuaDirections.actionDualist();
                action.setDuaTitle(title);
                action.setDuaArabic(arabic);
                action.setDuaPronunciation(pronunciation);
                action.setDuaTranslation(translation);
                action.setFlagFromEdit(INSERT_DUA);

                Navigation.findNavController(getActivity(), R.id.fragment).navigate(action);
            }

        }
    }
}