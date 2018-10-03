package com.example.e3646.lifeblabla.jot;

import android.support.v4.app.Fragment;
import static com.google.common.base.Preconditions.checkNotNull;

public class JotFragment extends Fragment implements JotContract.View {

    private JotContract.Presenter mPresenter;


    @Override
    public void setPresenter(JotContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);

    }
}
