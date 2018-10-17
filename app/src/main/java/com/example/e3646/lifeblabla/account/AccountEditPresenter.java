package com.example.e3646.lifeblabla.account;

import static com.google.common.base.Preconditions.checkNotNull;

public class AccountEditPresenter implements AccountEditContract.Presenter {

    private AccountEditContract.View mAccountEditView;
    private AccountAdapter mAccountAdapter;

    public AccountEditPresenter (AccountEditContract.View accountEditView) {

        mAccountEditView = checkNotNull(accountEditView);
        mAccountEditView.setPresenter(this);
        mAccountAdapter = new AccountAdapter();

    }
    @Override
    public void start() {

    }
}
