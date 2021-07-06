package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Chats extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.chats);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.chats:
                        return true;
                    case R.id.status:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Calls:
                        startActivity(new Intent(getApplicationContext(),Calls.class));
                        overridePendingTransition(0,0);

                        return true;
                }
                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_hamberger,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.new_group:
            {
                startActivity(new Intent(getApplicationContext(),New_Group.class));
                break;
            }
            case R.id.new_broadcast:
            {
                startActivity(new Intent(getApplicationContext(),New_Broadcast.class));
                break;
            }
            case R.id.Settings:{
                startActivity(new Intent(getApplicationContext(),Settings.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }
}