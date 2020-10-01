package com.example.dell.a20hour.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dell.a20hour.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class AppIntoActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_app_into);

        addSlide(AppIntroFragment.newInstance("Grow your own forest.","Whenever you want to stay focused, plant a tree.",R.drawable.pic1, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark)));
        addSlide(AppIntroFragment.newInstance("Stay focused whenever you can.","Your tree will grow while you focus on your work.",R.drawable.pic2, ContextCompat.getColor(getApplicationContext(),R.color.colorAccent)));
        addSlide(AppIntroFragment.newInstance("BUT!! REMEMBER.","The tree will be killed if you leave this app",R.drawable.pic3, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark)));


    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent=new Intent(getApplicationContext(),SkillActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent=new Intent(getApplicationContext(),SkillActivity.class);
        startActivity(intent);
    }
}