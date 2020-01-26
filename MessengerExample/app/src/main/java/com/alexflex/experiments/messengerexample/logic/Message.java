package com.alexflex.experiments.messengerexample.logic;

import androidx.annotation.NonNull;

public class Message {
    private String username;
    private String text;
    private String date;

    public Message(){}
    public Message(@NonNull String username,
                   @NonNull String text,
                   @NonNull String date) {
        this.username = username;
        this.text = text;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }
}
