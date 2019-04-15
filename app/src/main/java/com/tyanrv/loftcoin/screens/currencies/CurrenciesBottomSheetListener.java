package com.tyanrv.loftcoin.screens.currencies;

import com.tyanrv.loftcoin.data.db.model.CoinEntity;

public interface CurrenciesBottomSheetListener {
    void onCurrencySelected(CoinEntity coin);
}
