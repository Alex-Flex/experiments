package com.alexflex.testyoursoftskills.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.alexflex.testyoursoftskills.R;
import com.alexflex.testyoursoftskills.logic.UsefulThings;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.alexflex.testyoursoftskills.logic.UsefulThings.INTENT_CODE;
import static com.alexflex.testyoursoftskills.logic.UsefulThings.PATH_SETTING_NAME;

public class YourPhotoFragment extends Fragment {

    private CardView getPhotoButton;
    private ImageView preview;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_your_photo, container, false);
        getPhotoButton = view.findViewById(R.id.load_photo);
        preview = view.findViewById(R.id.image);
        String path = getActivity().getPreferences(Context.MODE_PRIVATE).getString(PATH_SETTING_NAME, null);
        if (path != null) {
            preview.setBackground(Drawable.createFromPath(path));
        }
        getPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //runtime request на запись в памяти
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, 5);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == INTENT_CODE) {    //ответ пришёл нам
            if (resultCode == RESULT_OK) {      //норм результат
                if (data != null) {
                    //интент не пустой
                    String path = UsefulThings.copyPhotoAndGetPath(getActivity(), data);
                    if (path != null) {
                        //путь правильный
                        Drawable drawable = Drawable.createFromPath(path);
                        preview.setBackground(drawable);
                        Toast.makeText(getActivity(), R.string.success, Toast.LENGTH_SHORT).show();
                    } else {
                        //файл не найден
                        Toast.makeText(getActivity(), R.string.file_not_found, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //снова не найден
                    Toast.makeText(getActivity(), R.string.file_not_found, Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                //выбор отменён
                Toast.makeText(getActivity(), R.string.pick_cancelled, Toast.LENGTH_SHORT).show();
            } else {
                //выбора нет, ибо кто-то накосячил
                Toast.makeText(getActivity(), R.string.trouble, Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
