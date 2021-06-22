package com.example.whatsappclone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen2 extends AppCompatActivity {

  TextView Term;
  Button btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(restorepref())
        {
            startActivity(new Intent(getApplicationContext(),entermobnumber.class));
        }


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen2);


        Term=findViewById(R.id.Terms);
        btn=findViewById(R.id.Accept);


        Term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse("https://www.whatsapp.com/legal/updates/privacy-policy/?lang=en"));
                startActivity(myWebLink);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),IntroActivity.class));
                savedprefsData();
            }
        });
    }

    private Boolean restorepref()
    {
        SharedPreferences pref=getApplicationContext().getSharedPreferences("prefs",MODE_PRIVATE);
        Boolean isActivityOpened=pref.getBoolean("IsOpened",false);
        return isActivityOpened;

    }

    private void savedprefsData() {
        SharedPreferences pref=getApplicationContext().getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putBoolean("IsOpened",true);
        editor.commit();
    }

}
