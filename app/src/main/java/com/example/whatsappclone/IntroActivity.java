package com.example.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {



    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    Button btn;
    int position=0;
    Button GetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(restoPrefData())
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_intro);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

            
        tabIndicator=findViewById(R.id.tab_indicator);
        btn=findViewById(R.id.Nextbtn);
        GetStarted=findViewById(R.id.finish);
        
        
        
         List<ScreenItem> mList=new ArrayList<>();
         mList.add(new ScreenItem("MESSAGING","Text Any one with ease",R.drawable.splash1));
         mList.add(new ScreenItem("Vaccination","Book Your Slots",R.drawable.ic_baseline_local_hospital_24));

        screenPager =findViewById(R.id.screen_pager);
        introViewPagerAdapter=new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);
        
        
        tabIndicator.setupWithViewPager(screenPager);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position=screenPager.getCurrentItem();
                if(position<mList.size())
                {
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if(position==mList.size()-1)
                {
                    loadlastsccreen();
                }
            }
        });
        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==mList.size()-1)
                {
                    loadlastsccreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        GetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainactivity=new Intent(getApplicationContext(),entermobnumber.class);
                startActivity(mainactivity); 
                saveprefsData();
                finish();
            }
        });
    }

    private boolean restoPrefData() {

        SharedPreferences pref=getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isItnreActivityOpenedBefore=pref.getBoolean("isIntroOpened",false);
        return isItnreActivityOpenedBefore;
    }

    private void loadlastsccreen(){
        btn.setVisibility(View.INVISIBLE);
        GetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);

    }

    private void saveprefsData() {

        SharedPreferences pref=getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.commit();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}