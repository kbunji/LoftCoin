package com.tyanrv.loftcoin.screens.rate;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tyanrv.loftcoin.App;
import com.tyanrv.loftcoin.R;
import com.tyanrv.loftcoin.data.api.Api;
import com.tyanrv.loftcoin.data.db.Database;
import com.tyanrv.loftcoin.data.db.model.CoinEntity;
import com.tyanrv.loftcoin.data.db.model.CoinEntityMapper;
import com.tyanrv.loftcoin.data.db.model.CoinEntityMapperImpl;
import com.tyanrv.loftcoin.data.prefs.Prefs;
import com.tyanrv.loftcoin.utils.Fiat;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class RateFragment extends Fragment implements RateView, Toolbar.OnMenuItemClickListener, CurrencyDialog.CurrencyDialogListener {

    private static final String LAYOUT_MANAGER_STATE = "layout_manager_state";

    private Parcelable layoutManagerState;
    private RatePresenter presenter;
    private RateAdapter adapter;

    public RateFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.rate_toolbar)
    Toolbar toolbar;

    @BindView(R.id.rate_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.rate_refresh)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.rate_content)
    ViewGroup content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = getActivity();

        if (activity == null) return;

        Application application = activity.getApplication();

        Prefs prefs = ((App) application).getPrefs();
        Api api = (((App) application).getApi());
        Database database = (((App) application).getDatabase());
        CoinEntityMapper mapper = new CoinEntityMapperImpl();

        presenter = new RatePresenterImpl(prefs, api, database, mapper);

        adapter = new RateAdapter(prefs);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        toolbar.setTitle(R.string.rate_screen_title);
        toolbar.inflateMenu(R.menu.menu_rate);
        toolbar.setOnMenuItemClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(() -> presenter.onRefresh());

        if (savedInstanceState != null) {
            layoutManagerState = savedInstanceState.getParcelable(LAYOUT_MANAGER_STATE);
        }

        assert getFragmentManager() != null;
        Fragment fragmentDialog = getFragmentManager().findFragmentByTag(CurrencyDialog.TAG);

        if (fragmentDialog != null) {
            CurrencyDialog dialog = (CurrencyDialog) fragmentDialog;
            dialog.setListener(this);
        }

        presenter.attachView(this);
        presenter.getRate();
    }

    @Override
    public void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void setCoins(List<CoinEntity> coins) {
        Timber.d("setCoins: ");
        adapter.setItems(coins);

        if (layoutManagerState != null) {
            Objects.requireNonNull(recyclerView.getLayoutManager()).onRestoreInstanceState(layoutManagerState);
            layoutManagerState = null;
        }
    }

    // сохранение состояния(для поворота экрана)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(
                LAYOUT_MANAGER_STATE,
                Objects.requireNonNull(recyclerView.getLayoutManager()).onSaveInstanceState()
        );
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setRefreshing(Boolean refreshing) {
        refreshLayout.setRefreshing(refreshing);
    }

    @Override
    public void showCurrencyDialog() {
        CurrencyDialog dialog = new CurrencyDialog();
        assert getFragmentManager() != null;
        dialog.show(getFragmentManager(), CurrencyDialog.TAG);
        dialog.setListener(this);
    }

    @Override
    public void invalidateRates() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_currency:
                presenter.onMenuItemCurrencyClick();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onCurrencySelected(Fiat currency) {
        presenter.onFiatCurrencySelected(currency);
    }
}
