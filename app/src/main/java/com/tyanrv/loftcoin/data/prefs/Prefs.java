package com.tyanrv.loftcoin.data.prefs;

import com.tyanrv.loftcoin.utils.Fiat;

public interface Prefs {
    boolean isFirstLaunch();

    void setFirstLaunch(boolean firstLaunch);

    Fiat getFiatCurrency();

    void setFiatCurrency(Fiat currency);
}
