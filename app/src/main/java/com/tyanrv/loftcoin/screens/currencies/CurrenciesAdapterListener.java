package com.tyanrv.loftcoin.screens.currencies;

import com.tyanrv.loftcoin.data.db.model.CoinEntity;

public interface CurrenciesAdapterListener {
    void onCurrencyClick(CoinEntity coin);
}
