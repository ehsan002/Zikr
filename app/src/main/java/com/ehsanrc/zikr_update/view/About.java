package com.ehsanrc.zikr_update.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.ehsanrc.zikr_update.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class About extends Fragment {

    private static final String APP_PNAME = "com.ehsanrc.zikr";
    private static final String FACEBOOK_URL = "https://www.facebook.com/rightcliks/";

    @BindView(R.id.btn_rate)
    Button rateButton;

    @BindView(R.id.btn_developedBy)
    ImageButton rightclikButton;

    public About() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("এপ সম্পর্কে");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, view);

        rateButton.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME))));

        rightclikButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse(FACEBOOK_URL));
                startActivity(viewIntent);
            }
        });

        return view;
    }
}