package com.tyanrv.loftcoin;

import android.app.Application;

import com.tyanrv.loftcoin.data.api.Api;
import com.tyanrv.loftcoin.data.api.ApiInitializer;
import com.tyanrv.loftcoin.data.db.Database;
import com.tyanrv.loftcoin.data.db.DatabaseInitializer;
import com.tyanrv.loftcoin.data.prefs.Prefs;
import com.tyanrv.loftcoin.data.prefs.PrefsImpl;

import timber.log.Timber;

public class App extends Application {

    private Prefs prefs;
    private Api api;
    private Database database;

    @Override
    public void onCreate() {
        super.onCreate();

        // logs only for debug
        Timber.plant(new Timber.DebugTree());

        prefs = new PrefsImpl(this);
        api = new ApiInitializer().init();
        database = new DatabaseInitializer().init(this);
    }

    public Prefs getPrefs() {
        return prefs;
    }

    public Api getApi() {
        return api;
    }

    public Database getDatabase() {
        return database;
    }
}
