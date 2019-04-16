package com.tyanrv.loftcoin.screens.wallets;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tyanrv.loftcoin.R;
import com.tyanrv.loftcoin.data.db.model.CoinEntity;
import com.tyanrv.loftcoin.screens.currencies.CurrenciesBottomSheet;
import com.tyanrv.loftcoin.screens.currencies.CurrenciesBottomSheetListener;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletsFragment extends Fragment implements CurrenciesBottomSheetListener {


    public WalletsFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.wallets_toolbar)
    Toolbar toolbar;

    @BindView(R.id.transactions_recycler)
    RecyclerView transactionsRecycler;

    @BindView(R.id.wallets_pager)
    ViewPager walletsPager;

    @BindView(R.id.new_wallet)
    ViewGroup newWallet;

    private WalletsViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(WalletsViewModelImpl.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        toolbar.setTitle(R.string.wallets_screen_title);
        toolbar.inflateMenu(R.menu.menu_wallets);

        Fragment bottomSheet = Objects.requireNonNull(getFragmentManager()).findFragmentByTag(CurrenciesBottomSheet.TAG);
        if (bottomSheet != null) {
            ((CurrenciesBottomSheet) bottomSheet).setListener(this);
        }

        viewModel.getWallets();

        initOutputs();
        initInputs();
    }

    private void initOutputs() {
        newWallet.setOnClickListener(v ->
                viewModel.onNewWalletClick()
        );

        toolbar.getMenu().findItem(R.id.menu_item_add_wallet).setOnMenuItemClickListener(item -> {
            viewModel.onNewWalletClick();
            return true;
        });
    }

    private void initInputs() {

        viewModel.selectCurrency().observe(this, o -> showCurrenciesBottomSheet());

        viewModel.newWalletVisible().observe(this, visible ->
                newWallet.setVisibility(visible ? View.VISIBLE : View.GONE));

        viewModel.walletsVisible().observe(this, visible ->
                walletsPager.setVisibility(visible ? View.VISIBLE : View.GONE));
    }

    private void showCurrenciesBottomSheet() {
        CurrenciesBottomSheet bottomSheet = new CurrenciesBottomSheet();

        bottomSheet.show(Objects.requireNonNull(getFragmentManager()), CurrenciesBottomSheet.TAG);
        bottomSheet.setListener(this);
    }

    @Override
    public void onCurrencySelected(CoinEntity coin) {
        viewModel.onCurrencySelected(coin);
    }
}
