package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private float x1;
    private float y1;
    private float x2;
    private float y2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.status);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.chats:
                        startActivity(new Intent(getApplicationContext(),Chats.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.status:
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
    public void onBackPressed() {
        super.onBackPressed();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                x1=event.getX();
//                y1=event.getY();
//                break;
//            case MotionEvent.ACTION_UP:
//                x2=event.getX();
//                y2=event.getY();
//                if(x1<x2)
//                {
//                    startActivity(new Intent(getApplicationContext(),Chats.class));
//                }
//                else if(x1>x2)
//                {
//                    startActivity(new Intent(getApplicationContext(),Calls.class));
//                }
//                break;
//        }
//        return false;
//
//    }

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