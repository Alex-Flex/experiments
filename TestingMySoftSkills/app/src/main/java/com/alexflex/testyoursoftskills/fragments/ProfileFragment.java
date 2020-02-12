package com.alexflex.testyoursoftskills.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.alexflex.testyoursoftskills.R;
import com.alexflex.testyoursoftskills.logic.UsefulThings;
import static com.alexflex.testyoursoftskills.logic.UsefulThings.BIOGRAPHY;
import static com.alexflex.testyoursoftskills.logic.UsefulThings.FIRST_NAME;
import static com.alexflex.testyoursoftskills.logic.UsefulThings.SECOND_NAME;

public class ProfileFragment extends Fragment {

    private View v;
    private TextView biography;
    private EditText editYourLive;
    private Button saver;
    private TextView firstName;
    private TextView secondName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        findViews();    //ищем все вьюшки
        return v;
    }

    @Override
    public void onStart(){
        super.onStart();
        biography.setVisibility(View.VISIBLE);
        editYourLive.setVisibility(View.GONE);
        saver.setVisibility(View.GONE);
        setListeners();     //настраиваем слушатели событий

        //настраиваем начальный текст для TextView с текстом "биографии"
        String aboutMe = getActivity().getPreferences(Context.MODE_PRIVATE).getString(BIOGRAPHY, null);
        if (aboutMe != null && !aboutMe.equals("")) biography.setText(aboutMe);
        else biography.setText(R.string.placeholder);

        //отображение имени
        String name = getActivity().getPreferences(Context.MODE_PRIVATE).getString(FIRST_NAME, null);
        if (name != null && !name.equals("")) firstName.setText(name);
        else firstName.setText(R.string.your_name);

        //отображение фамилии
        String family = getActivity().getPreferences(Context.MODE_PRIVATE).getString(SECOND_NAME, null);
        if (family != null && !family.equals("")) secondName.setText(family);
        else secondName.setText(R.string.your_family);
    }

    //ищем виды на экране
    private void findViews(){
        biography = v.findViewById(R.id.biography);
        editYourLive = v.findViewById(R.id.biography_editor);
        saver = v.findViewById(R.id.save);
        firstName = v.findViewById(R.id.first_name);
        secondName = v.findViewById(R.id.second_name);
    }

    //присваиваем видам слушатели событий
    private void setListeners(){

        //ввод имени
        firstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View firstNameDialog = getLayoutInflater().inflate(R.layout.entering_first_name, null);
                final EditText firstNameText = firstNameDialog.findViewById(R.id.enter_first_name);
                UsefulThings.getDialogWithCustomView(new AlertDialog.Builder(getContext()),
                        firstNameDialog,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String text = firstNameText.getText().toString();
                                getActivity().getPreferences(Context.MODE_PRIVATE).edit()
                                        .putString(FIRST_NAME, text)
                                        .apply();
                                firstName.setText(text);
                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        },
                        false).show();
            }
        });

        //ввод фамилии
        secondName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View secondNameDialog = getLayoutInflater().inflate(R.layout.entering_last_name, null);
                final EditText secondNameText = secondNameDialog.findViewById(R.id.enter_last_name);
                UsefulThings.getDialogWithCustomView(new AlertDialog.Builder(getContext()),
                        secondNameDialog,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String text = secondNameText.getText().toString();
                                getActivity().getPreferences(Context.MODE_PRIVATE).edit()
                                        .putString(SECOND_NAME, text)
                                        .apply();
                                secondName.setText(text);
                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        },
                        false).show();
            }
        });

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
                String aboutMe = editYourLive.getText().toString();
                getActivity().getPreferences(Context.MODE_PRIVATE)
                        .edit().putString(BIOGRAPHY, aboutMe)
                        .apply();
                biography.setText(aboutMe);
                editYourLive.setVisibility(View.GONE);
                saver.setVisibility(View.GONE);
                biography.setVisibility(View.VISIBLE);
            }
        });
    }
}
