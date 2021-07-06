package com.example.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle(Html.fromHtml("Settings"));
    }

}