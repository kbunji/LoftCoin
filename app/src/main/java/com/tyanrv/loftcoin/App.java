package com.tyanrv.loftcoin;

import android.app.Application;

import com.tyanrv.loftcoin.data.api.Api;
import com.tyanrv.loftcoin.data.api.ApiInitializer;
import com.tyanrv.loftcoin.data.prefs.Prefs;
import com.tyanrv.loftcoin.data.prefs.PrefsImpl;

public class App extends Application {

    private Prefs prefs;
    private Api api;

    @Override
    public void onCreate() {
        super.onCreate();

        prefs = new PrefsImpl(this);
        api = new ApiInitializer().init();
    }

    public Prefs getPrefs() {
        return prefs;
    }

    public Api getApi() {
        return api;
    }
}
