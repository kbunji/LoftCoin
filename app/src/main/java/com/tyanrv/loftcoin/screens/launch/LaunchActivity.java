package com.tyanrv.loftcoin.screens.launch;

import android.os.Bundle;

import com.tyanrv.loftcoin.App;
import com.tyanrv.loftcoin.data.prefs.Prefs;

import androidx.appcompat.app.AppCompatActivity;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Prefs prefs = ((App) getApplication()).getPrefs();

        if (prefs.isFirstLaunch()) {
            // welcomeActivity
        } else {
            // startActivity
        }
    }
}
