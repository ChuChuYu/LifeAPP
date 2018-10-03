package com.example.e3646.lifeblabla.jot;

import static com.google.common.base.Preconditions.checkNotNull;

public class JotPresenter implements JotContract.Presenter {

    private JotContract.View mJotView;

    public JotPresenter (JotContract.View jotView) {

        mJotView = checkNotNull(jotView);
        mJotView.setPresenter(this);

    }

    @Override
    public void start() {

    }
}
