package com.tyanrv.loftcoin.screens.rate;

import com.tyanrv.loftcoin.utils.Fiat;

public interface RatePresenter {

    void attachView(RateView view);

    void detachView();

    void getRate();

    void onRefresh();

    void onMenuItemCurrencyClick();

    void onFiatCurrencySelected(Fiat currency);
}
