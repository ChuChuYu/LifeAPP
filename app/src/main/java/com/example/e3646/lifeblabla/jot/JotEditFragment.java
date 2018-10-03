package com.example.e3646.lifeblabla.jot;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e3646.lifeblabla.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class JotEditFragment extends Fragment implements JotEditContrat.View {

    private JotEditContrat.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jotedit, container, false);

        return view;
    }

    @Override
    public void setPresenter(JotEditContrat.Presenter presenter) {
        mPresenter = checkNotNull(presenter);

    }
}
