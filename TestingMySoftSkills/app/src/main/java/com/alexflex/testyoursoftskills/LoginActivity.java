package com.alexflex.testyoursoftskills;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import java.util.regex.Pattern;
import static com.alexflex.testyoursoftskills.logic.UsefulThings.EMAIL;
import static com.alexflex.testyoursoftskills.logic.UsefulThings.PASSWORD;

public class LoginActivity extends AppCompatActivity {

    private EditText login;     //имя пользователя
    private EditText password;  //его пароль
    private CardView confirmer;   //кнопка для подтверждения
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirmer = findViewById(R.id.login);
        login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //если текст красный, при изменении он будет чёрным
                if (login.getCurrentTextColor() != Color.BLACK)
                    login.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //если текст красный, при изменении он будет чёрным
                if (password.getCurrentTextColor() != Color.BLACK)
                    password.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login.getText().toString();
                String watchword = password.getText().toString();
                if (!Pattern.matches("^(.+)@(.+)$", email) && !watchword.equals("123")){
                    //неверный емаил и пароль
                    login.setTextColor(Color.RED);
                    password.setTextColor(Color.RED);
                    Toast.makeText(getApplicationContext(), R.string.both_wrong, Toast.LENGTH_LONG).show();
                    return;
                }
                if (!Pattern.matches("^(.+)@(.+)$", email)){
                    //неверный только емаил
                    login.setTextColor(Color.RED);
                    Toast.makeText(getApplicationContext(), R.string.wrong_username, Toast.LENGTH_LONG).show();
                    return;
                }
                if (!watchword.equals("123")){
                    //неверный только пароль
                    password.setTextColor(Color.RED);
                    Toast.makeText(getApplicationContext(), R.string.wrong_password, Toast.LENGTH_LONG).show();
                    return;
                }
                editor = getPreferences(Context.MODE_PRIVATE).edit();
                editor.putString(EMAIL, email);
                editor.putString(PASSWORD, watchword);
                editor.apply();
                startActivity(new Intent(getApplicationContext(), PagerActivity.class));
                finish();
            }
        });
    }
}
