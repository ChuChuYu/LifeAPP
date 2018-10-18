package com.example.e3646.lifeblabla.account;

import static com.google.common.base.Preconditions.checkNotNull;

public class AccountPresenter implements AccountContract.Presenter {

    private AccountContract.View mAccountView;


    public AccountPresenter(AccountContract.View accountView) {
        mAccountView = checkNotNull(accountView);
        mAccountView.setPresenter(this);

    }
    @Override
    public void start() {

    }
}
