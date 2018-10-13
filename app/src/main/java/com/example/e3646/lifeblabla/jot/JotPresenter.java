package com.example.e3646.lifeblabla.jot;

import android.support.v4.app.FragmentManager;

import com.example.e3646.lifeblabla.mainactivity.MainActPresenter;

import static com.google.common.base.Preconditions.checkNotNull;

public class JotPresenter implements JotContract.Presenter {

    private JotContract.View mJotView;
    private FragmentManager mFragmentManager;
    private MainActPresenter mMainActPresenter;

    public JotPresenter (JotContract.View jotView, FragmentManager fragmentManager, MainActPresenter mainActPresenter) {

        mJotView = checkNotNull(jotView);
        mJotView.setPresenter(this);

        mFragmentManager = fragmentManager;
        mMainActPresenter = mainActPresenter;

    }

    @Override
    public void start() {

    }

    @Override
    public void backToMain() {

        mJotView.hideUI();
        mMainActPresenter.refreshMainFragment();
        mMainActPresenter.backToMain();

    }
}
