package com.tyanrv.loftcoin.data.db.model;

import com.tyanrv.loftcoin.data.api.model.Coin;

import java.util.ArrayList;
import java.util.List;

public class CoinEntityMapperImpl implements CoinEntityMapper {
    @Override
    public List<CoinEntity> map(List<Coin> coins) {
        List<CoinEntity> entities = new ArrayList<>();

        for (Coin coin : coins) {
            entities.add(mapEntity(coin));
        }

        return entities;
    }

    private CoinEntity mapEntity(Coin coin) {

        CoinEntity entity = new CoinEntity();

        entity.id = coin.id;
        entity.name = coin.name;
        entity.symbol = coin.symbol;
        entity.slug = coin.slug;
        entity.lastUpdated = coin.lastUpdated;

        return entity;
    }
}
