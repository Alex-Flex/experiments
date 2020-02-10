package com.alexflex.testyoursoftskills.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexflex.testyoursoftskills.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginPasswordFragment extends Fragment {


    public LoginPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_password, container, false);
    }

}
