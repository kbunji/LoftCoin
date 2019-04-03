package com.tyanrv.loftcoin.data.db.model;

import com.tyanrv.loftcoin.utils.Fiat;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Coin")
public class CoinEntity {

    @PrimaryKey
    public int id;

    public String name;

    public String symbol;

    public String slug;

    public String lastUpdated;

    @Embedded(prefix = "usd")
    public QuoteEntity usd;

    @Embedded(prefix = "eur")
    public QuoteEntity eur;

    @Embedded(prefix = "rub")
    public QuoteEntity rub;

    public QuoteEntity getQuote(Fiat fiat) {
        switch (fiat) {
            case USD:
                return usd;
            case EUR:
                return eur;
            case RUB:
                return rub;
        }
        return usd;
    }
}
