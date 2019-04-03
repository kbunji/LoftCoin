package com.tyanrv.loftcoin.data.db;

import com.tyanrv.loftcoin.data.db.model.CoinEntity;

import java.util.List;

public interface Database {

    void saveCoins(List<CoinEntity> coins);

    List<CoinEntity> getCoins();
}
