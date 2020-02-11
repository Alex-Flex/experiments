package com.alexflex.testyoursoftskills.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alexflex.testyoursoftskills.R;

import static com.alexflex.testyoursoftskills.logic.UsefulThings.BIOGRAPHY;

public class ProfileFragment extends Fragment {

    private View v;
    private TextView biography;
    private EditText editYourLive;
    private Button saver;
    String aboutMe;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        biography = v.findViewById(R.id.biography);
        editYourLive = v.findViewById(R.id.biography_editor);
        saver = v.findViewById(R.id.save);
        return v;
    }

    @Override
    public void onStart(){
        super.onStart();
        editYourLive.setVisibility(View.GONE);
        saver.setVisibility(View.GONE);
        biography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biography.setVisibility(View.GONE);
                editYourLive.setVisibility(View.VISIBLE);
                saver.setVisibility(View.VISIBLE);
            }
        });
        saver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutMe = editYourLive.getText().toString();
                getActivity().getPreferences(Context.MODE_PRIVATE)
                        .edit().putString(BIOGRAPHY, aboutMe)
                        .apply();
                biography.setText(aboutMe);
                editYourLive.setVisibility(View.GONE);
                saver.setVisibility(View.GONE);
                biography.setVisibility(View.VISIBLE);
            }
        });
        aboutMe = getActivity().getPreferences(Context.MODE_PRIVATE).getString(BIOGRAPHY, null);
        if (aboutMe != null) {
            biography.setText(aboutMe);
        } else {
            biography.setText(R.string.placeholder);
        }
    }

}
