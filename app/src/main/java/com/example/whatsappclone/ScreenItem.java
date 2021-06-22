package com.example.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

public class ScreenItem extends AppCompatActivity {

    String Title,Description;
    int ScreenImg;

    public ScreenItem( String title, String description, int screenImg) {
        Title = title;
        Description = description;
        ScreenImg = screenImg;
    }

    public String gettitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public int getScreenImg() {
        return ScreenImg;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setScreenImg(int screenImg) {
        ScreenImg = screenImg;
    }
}
