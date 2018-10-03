package com.example.e3646.lifeblabla.jot;

import static com.google.common.base.Preconditions.checkNotNull;

public class JotEditPresenter implements JotEditContrat.Presenter {


    private JotEditContrat.View mJotEditView;

    public JotEditPresenter (JotEditContrat.View jotEditView) {

        mJotEditView = checkNotNull(jotEditView);
        mJotEditView.setPresenter(this);


    }


    @Override
    public void start() {

    }
}
