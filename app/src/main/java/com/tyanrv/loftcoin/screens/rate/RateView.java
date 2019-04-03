package com.tyanrv.loftcoin.screens.rate;

import com.tyanrv.loftcoin.data.db.model.CoinEntity;

import java.util.List;

public interface RateView {

    void setCoins(List<CoinEntity> coins);

    void setRefreshing(Boolean refreshing);

    void showCurrencyDialog();
}
