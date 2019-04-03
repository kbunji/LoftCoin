package com.tyanrv.loftcoin.data.db.model;

import com.tyanrv.loftcoin.data.api.model.Coin;

import java.util.List;

public interface CoinEntityMapper {

    List<CoinEntity> map(List<Coin> coins);
}
