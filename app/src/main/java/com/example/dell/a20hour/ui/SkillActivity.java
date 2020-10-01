package com.example.dell.a20hour.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.devadvance.circularseekbar.CircularSeekBar;
import com.example.dell.a20hour.R;
import com.example.dell.a20hour.db.entity.Skill;
import com.google.gson.Gson;

public class SkillActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;

    TextView tvTime;
    long selectedTime, MIN_TIME = 10*60*1000, TOTAL_TIME = 1 * 60 * 60 * 1000;
    int hoursCompleted = 0;
    CountDownTimer abortTimer;
    CircularSeekBar seekbar;
    Button btnStart;
    ImageButton btnMenu;
    boolean timerRunnning = false;
    ImageView ivSkillStage;
    int[] stagesPic;
    String skillAsString;

    private void startAbortTimer(){

        abortTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {
                btnStart.setText("Abort in " + Long.toString(l/1000));
            }

            @Override
            public void onFinish() {
                this.cancel();

                //goto skillCount Activity
                Intent intent = new Intent(getApplicationContext(), SkillCountActivity.class);
                intent.putExtra("skill", skillAsString);
                intent.putExtra("selectedTime", selectedTime);
                startActivity(intent);

                btnStart.setText("Start");
            }
        }.start();
    }

    public void formatTimeAndShow(long millis){
        int seconds = (int)millis/(1000);
        int h = seconds/3600;
        int rem = seconds%3600;
        int m = rem/60;
        String hr = Integer.toString(h), min = Integer.toString(m);
        if(h<=9) hr = "0" + Integer.toString(h);
        if(m<=9) min = "0" + Integer.toString(m);
        tvTime.setText(hr + ":" + min);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill);




        //get reference to controls

        seekbar = findViewById(R.id.circularSeekBar);
        tvTime = findViewById(R.id.tvTime);
        btnStart = findViewById(R.id.btnStart);
        ivSkillStage = findViewById(R.id.ivSkillStage);
        btnMenu = findViewById(R.id.ibtnMenu);

        //set initial values
        seekbar.setProgress(1);
        selectedTime = MIN_TIME;
        formatTimeAndShow(MIN_TIME);
        stagesPic = new int[]{R.drawable.s1, R.drawable.s2, R.drawable.s3, R.drawable.s4, R.drawable.s5};
        ivSkillStage.setImageResource(stagesPic[0]);

        //get selected skill
        Gson gson = new Gson();
        skillAsString = getIntent().getStringExtra("skill");


        //set Event Handlers
        seekbar.setOnSeekBarChangeListener(new CircleSeekBarListener());
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerRunnning){
                    abortTimer.cancel();
                    btnStart.setText("Start");
                    timerRunnning = false;
                }
                else{
                    startAbortTimer();
                    timerRunnning = true;
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    public class CircleSeekBarListener implements CircularSeekBar.OnCircularSeekBarChangeListener {
        @Override
        public void onProgressChanged(CircularSeekBar circularSeekBar, int progress, boolean fromUser) {
            //change color in min and more percentage
            if(progress <= 1){
                progress = 1;
                seekbar.setProgress(1);
            }
            selectedTime = progress*5*60*1000;
            formatTimeAndShow(selectedTime);
            int percent = (99/24)*progress;
            ivSkillStage.setImageResource(stagesPic[(percent/20)]);
        }
        @Override
        public void onStopTrackingTouch(CircularSeekBar seekBar) {}
        @Override
        public void onStartTrackingTouch(CircularSeekBar seekBar) {}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
