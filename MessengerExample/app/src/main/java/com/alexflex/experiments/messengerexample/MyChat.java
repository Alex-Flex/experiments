package com.alexflex.experiments.messengerexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.alexflex.experiments.messengerexample.logic.Message;
import com.alexflex.experiments.messengerexample.logic.UsefulMethods;
import com.daasuu.bl.ArrowDirection;
import com.daasuu.bl.BubbleLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MyChat extends AppCompatActivity {

    private ListView msgsLayout;
    private EditText message;
    private Button sender;
    private final int REQUEST_CODE = 0;
    private FirebaseListAdapter<Message> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        msgsLayout = findViewById(R.id.messages_list);
        message = findViewById(R.id.message);
        sender = findViewById(R.id.send_button);

        msgsLayout.setDivider(null);
        msgsLayout.setDividerHeight(15);

        FirebaseListOptions<Message> options = new FirebaseListOptions.Builder<Message>()
                .setQuery(FirebaseDatabase.getInstance().getReference(), Message.class)
                .setLayout(R.layout.message_item).build();

        adapter = new FirebaseListAdapter<Message>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Message model, int position){
                LinearLayout layout = v.findViewById(R.id.background);
                TextView username = v.findViewById(R.id.username);
                TextView text = v.findViewById(R.id.text);
                TextView date = v.findViewById(R.id.date);
                BubbleLayout bubble = v.findViewById(R.id.bubble);

                if (FirebaseAuth.getInstance().getCurrentUser().getDisplayName().equals(model.getUsername())) {
                    layout.setGravity(Gravity.END);
                    bubble.setBubbleColor(Color.BLUE);
                    bubble.setArrowDirection(ArrowDirection.RIGHT);
                } else {
                    layout.setGravity(Gravity.START);
                    bubble.setBubbleColor(Color.GREEN);
                    bubble.setArrowDirection(ArrowDirection.LEFT);
                }

                bubble.setCornersRadius(40);

                username.setText(model.getUsername());
                text.setText(model.getText());
                date.setText(model.getDate());
            }
        };

        msgsLayout.setAdapter(adapter);

        if (UsefulMethods.checkIfGooglePlayServicesAvailable(this)) {
            UsefulMethods.createAlertDialog(new AlertDialog.Builder(this),
                    null,
                    getResources().getString(R.string.warning),
                    getResources().getString(R.string.gms_not_found),
                    getResources().getString(R.string.no),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    },
                    null,
                    null,
                    getResources().getString(R.string.yes),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(MyChat.this);
                        }
                    },
                    false,
                    null).show();
        } else {
            if (FirebaseAuth.getInstance().getCurrentUser() == null){
                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), REQUEST_CODE);
            } else {
                Toast.makeText(this, R.string.you_are_authorized, Toast.LENGTH_LONG).show();
            }
        }

        //listener для кнопки сообщений
        sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!message.getText().toString().equals("")) {
                    FirebaseDatabase.getInstance().getReference().push()
                            .setValue(new Message(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                                    message.getText().toString(),
                                    new SimpleDateFormat("dd-MM-yyyy hh:mm").format(new Date())));
                    message.setText("");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, R.string.you_are_authorized, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.you_are_not_authorized, Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
        if (UsefulMethods.isUserRealGangster(this))
            Toast.makeText(this, R.string.easter_egg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop(){
        adapter.stopListening();
        super.onStop();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (UsefulMethods.checkIfGooglePlayServicesAvailable(this)) {
            UsefulMethods.createAlertDialog(new AlertDialog.Builder(this),
                    null,
                    getResources().getString(R.string.warning),
                    getResources().getString(R.string.gms_not_found),
                    getResources().getString(R.string.no),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    },
                    null,
                    null,
                    getResources().getString(R.string.yes),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(MyChat.this);
                        }
                    },
                    false,
                    null).show();
        }
    }
}
