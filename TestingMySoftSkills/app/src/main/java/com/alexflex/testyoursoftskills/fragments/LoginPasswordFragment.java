package com.alexflex.testyoursoftskills.fragments;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.alexflex.testyoursoftskills.R;

public class LoginPasswordFragment extends Fragment {

    private TextView username;
    private TextView password;
    private View v;
    private final String EMAIL = "email";
    private final String PASSWORD = "password";

    public LoginPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_login_password, container, false);
        username = v.findViewById(R.id.username);
        password = v.findViewById(R.id.password);
        return v;
    }

    @Override
    public void onStart(){
        super.onStart();
        username.setText(getActivity().getSharedPreferences("LoginActivity", Context.MODE_PRIVATE)
                .getString(EMAIL, ""));
        password.setText(getActivity().getSharedPreferences("LoginActivity", Context.MODE_PRIVATE)
                .getString(PASSWORD, ""));
    }

}
