package com.tyanrv.loftcoin.screens.rate;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tyanrv.loftcoin.App;
import com.tyanrv.loftcoin.R;
import com.tyanrv.loftcoin.data.api.Api;
import com.tyanrv.loftcoin.data.api.model.Coin;
import com.tyanrv.loftcoin.data.prefs.Prefs;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RateFragment extends Fragment implements RateView {


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

    private RatePresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = getActivity();

        if (activity == null) return;

        Application application = activity.getApplication();

        Prefs prefs = ((App) application).getPrefs();
        Api api = (((App) application).getApi());

        presenter = new RatePresenterImpl(prefs, api);
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

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onRefresh();
            }
        });

        presenter.attachView(this);
        presenter.getRate();
    }

    @Override
    public void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void setCoins(List<Coin> coins) {

    }

    @Override
    public void setRefreshing(Boolean refreshing) {
        refreshLayout.setRefreshing(refreshing);
    }

    @Override
    public void showCurrencyDialog() {

    }
}