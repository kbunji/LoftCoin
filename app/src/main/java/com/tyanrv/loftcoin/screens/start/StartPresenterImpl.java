package com.tyanrv.loftcoin.screens.start;

import com.tyanrv.loftcoin.data.api.Api;
import com.tyanrv.loftcoin.data.prefs.Prefs;

import androidx.annotation.Nullable;

public class StartPresenterImpl implements StartPresenter {

    private Prefs prefs;
    private Api api;

    @Nullable
    private StartView view;

    public StartPresenterImpl(Prefs prefs, Api api) {
        this.prefs = prefs;
        this.api = api;
    }

    @Override
    public void attachView(StartView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void loadRates() {

    }
}
