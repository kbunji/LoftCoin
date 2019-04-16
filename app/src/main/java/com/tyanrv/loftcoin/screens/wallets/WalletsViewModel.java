package com.tyanrv.loftcoin.screens.wallets;

import android.app.Application;

import com.tyanrv.loftcoin.data.db.model.CoinEntity;
import com.tyanrv.loftcoin.data.db.model.WalletModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

abstract class WalletsViewModel extends AndroidViewModel {

    public WalletsViewModel(@NonNull Application application) {
        super(application);
    }

    abstract LiveData<Object> selectCurrency();

    abstract LiveData<Boolean> walletsVisible();

    abstract LiveData<Boolean> newWalletVisible();

    abstract LiveData<List<WalletModel>> wallets();

    abstract void getWallets();

    abstract void onNewWalletClick();

    abstract void onCurrencySelected(CoinEntity coinEntity);

}
