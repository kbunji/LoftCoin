package com.tyanrv.loftcoin.screens.wallets;

import android.app.Application;

import com.tyanrv.loftcoin.App;
import com.tyanrv.loftcoin.data.db.Database;
import com.tyanrv.loftcoin.data.db.model.CoinEntity;
import com.tyanrv.loftcoin.data.db.model.Transaction;
import com.tyanrv.loftcoin.data.db.model.Wallet;
import com.tyanrv.loftcoin.data.db.model.WalletModel;
import com.tyanrv.loftcoin.utils.SingleLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class WalletsViewModelImpl extends WalletsViewModel {

    private Database database;
    private CompositeDisposable disposables = new CompositeDisposable();

    public WalletsViewModelImpl(@NonNull Application application) {
        super(application);
        Timber.d("ViewModel constructor");

        database = ((App) getApplication()).getDatabase();
    }

    private SingleLiveData<Object> selectCurrency = new SingleLiveData<>();
    private MutableLiveData<Boolean> walletsVisible = new MutableLiveData<>();
    private MutableLiveData<Boolean> newWalletVisible = new MutableLiveData<>();
    private MutableLiveData<List<WalletModel>> wallets = new MutableLiveData<>();

    @Override
    LiveData<Object> selectCurrency() {
        return selectCurrency;
    }

    @Override
    LiveData<Boolean> walletsVisible() {
        return walletsVisible;
    }

    @Override
    LiveData<Boolean> newWalletVisible() {
        return newWalletVisible;
    }

    @Override
    LiveData<List<WalletModel>> wallets() {
        return wallets;
    }

    @Override
    void getWallets() {
        Disposable disposable = database.getWallets()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        walletModels -> {
                            if (walletModels.isEmpty()) {
                                newWalletVisible.setValue(true);
                                walletsVisible.setValue(false);
                            } else {
                                newWalletVisible.setValue(false);
                                walletsVisible.setValue(true);


                                wallets.setValue(walletModels);
                            }
                        },
                        Timber::e);

        disposables.add(disposable);
    }

    @Override
    void onNewWalletClick() {
        selectCurrency.setValue(new Object());
    }

    @Override
    void onCurrencySelected(CoinEntity coinEntity) {
        Wallet wallet = randomWallet(coinEntity);
        List<Transaction> transactions = randomTransactions(wallet);

        Disposable disposable = Observable.fromCallable(() -> {
                    database.saveWallet(wallet);
            database.saveTransaction(transactions);
                    return new Object();
                }
        ).subscribeOn(Schedulers.io())
                .subscribe(o -> {

                }, Timber::e);

        disposables.add(disposable);
    }

    private List<Transaction> randomTransactions(Wallet wallet) {
        Random random = new Random();
        int max = random.nextInt(20);

        List<Transaction> transactions = new ArrayList<>();

        for (int i = 0; i < max; i++) {
            transactions.add(randomTransaction(wallet));
        }

        return transactions;
    }

    private Transaction randomTransaction(Wallet wallet) {
        Random random = new Random();

        long startDate = 1483228800000L;
        long nowDate = System.currentTimeMillis();
        long date = startDate + (long) (random.nextDouble() * (nowDate - startDate));

        double amount = 4 * random.nextDouble();
        boolean amountSign = random.nextBoolean();

        return new Transaction(wallet.walletId, wallet.currencyId, amountSign ? amount : -amount, date);
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }

    private Wallet randomWallet(CoinEntity coinEntity) {
        Random random = new Random();

        return new Wallet(UUID.randomUUID().toString(), coinEntity.id, 10 * random.nextDouble());
    }
}
