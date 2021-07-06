package com.example.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;

public class New_Broadcast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_broadcast);
        getSupportActionBar().setTitle(Html.fromHtml("New Broadcast"));
    }
}