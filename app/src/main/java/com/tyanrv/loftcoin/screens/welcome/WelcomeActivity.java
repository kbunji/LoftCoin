package com.tyanrv.loftcoin.screens.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.tyanrv.loftcoin.App;
import com.tyanrv.loftcoin.R;
import com.tyanrv.loftcoin.data.prefs.Prefs;
import com.tyanrv.loftcoin.screens.start.StartActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, WelcomeActivity.class);
        context.startActivity(starter);
    }

    public static void startInNewTask(Context context) {
        Intent starter = new Intent(context, WelcomeActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(starter);
    }


    @BindView(R.id.pager)
    ViewPager pager;

    @BindView(R.id.start_btn)
    Button startBtn;

    @BindView(R.id.tabs)
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ButterKnife.bind(this);


        final Prefs prefs = ((App) getApplication()).getPrefs();

        pager.setAdapter(new WelcomePagerAdapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(pager, true);
        startBtn.setOnClickListener(v -> {

            prefs.setFirstLaunch(false);
            StartActivity.startInNewTask(WelcomeActivity.this);
        });
    }
}
