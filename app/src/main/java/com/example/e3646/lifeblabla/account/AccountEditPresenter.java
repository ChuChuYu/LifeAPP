package com.example.e3646.lifeblabla.account;

import android.support.v4.app.FragmentManager;

import com.example.e3646.lifeblabla.mainactivity.MainActPresenter;

import static com.google.common.base.Preconditions.checkNotNull;

public class AccountEditPresenter implements AccountEditContract.Presenter {

    private AccountEditContract.View mAccountEditView;

    private FragmentManager mFragmentManager;
    private MainActPresenter mMainActPresenter;
    private boolean isCreating;

    public AccountEditPresenter (AccountEditContract.View accountEditView, FragmentManager fragmentManager, MainActPresenter mainActPresenter, boolean iscreating) {

        mAccountEditView = checkNotNull(accountEditView);
        mAccountEditView.setPresenter(this);

        mFragmentManager = fragmentManager;
        mMainActPresenter = mainActPresenter;
        isCreating = iscreating;

    }
    @Override
    public void start() {

    }

    @Override
    public void saveAccountData() {

    }

    @Override
    public void cancelEditing() {

    }

    @Override
    public void completeCreating() {

    }

    @Override
    public void completeEditing() {

    }
}
