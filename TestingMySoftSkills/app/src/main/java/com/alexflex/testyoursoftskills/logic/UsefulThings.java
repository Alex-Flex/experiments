package com.alexflex.testyoursoftskills.logic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import androidx.annotation.NonNull;
import com.alexflex.testyoursoftskills.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UsefulThings {

    //всякие константы
    public static final int INTENT_CODE = 5;
    public static final String PATH_SETTING_NAME = "customPhotoPath";
    public static final String FOLDER_NAME = "MoreThanApp";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String BIOGRAPHY = "biography";
    public static final String FIRST_NAME = "firstname";
    public static final String SECOND_NAME = "secondname";

    //копирование фото по uri и возврат пути к этому фото
    public static String copyPhotoAndGetPath(Activity activity, Intent intent){
        Uri uri = intent.getData();
        if(uri != null) {
            //фото успешно выбрано
            if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //недоступна память
                return null;
            } else {
                //всё ок
                //создаём папку и файл в ней
                File folder = new File(Environment.getExternalStorageDirectory(), FOLDER_NAME);
                InputStream inputStream = null;
                OutputStream outputStream = null;
                if(!folder.exists()) {
                    folder.mkdir();
                }
                try {
                    //создаём файл
                    File photo = new File(folder, System.currentTimeMillis() + ".jpg");
                    photo.createNewFile();

                    //копируем выбранное фото в файл в нашем приложении
                    inputStream = activity.getContentResolver().openInputStream(uri);
                    outputStream = new FileOutputStream(photo);
                    byte[] buffer = new byte[1024];
                    int len;
                    while((len = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, len);
                    }
                    SharedPreferences.Editor editor = activity.getPreferences(Context.MODE_PRIVATE).edit();
                    editor.putString(PATH_SETTING_NAME, photo.getPath());
                    editor.apply();
                    return photo.getAbsolutePath();
                } catch (IOException e) {
                    return null;
                } finally {
                    try {
                        if(outputStream != null)
                            outputStream.close();
                        if(inputStream != null)
                            inputStream.close();
                    } catch (IOException ignored) { }
                }
            }
        }
        return null;
    }

    //создание alert dialog с кастомным видом и, соответственно поведением
    public static AlertDialog getDialogWithCustomView(@NonNull AlertDialog.Builder builder,
                                                      @NonNull View forDialog,
                                                      @NonNull DialogInterface.OnClickListener positiveListener,
                                                      @NonNull DialogInterface.OnClickListener negativeListener,
                                                      boolean isCancellable) {
        builder.setView(forDialog);

        builder.setPositiveButton(R.string.okay, positiveListener);
        builder.setNegativeButton(R.string.cancel, negativeListener);
        builder.setCancelable(isCancellable);
        return builder.create();
    }
}
