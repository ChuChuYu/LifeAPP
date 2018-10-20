package com.example.e3646.lifeblabla.calendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e3646.lifeblabla.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class CalendarFragment extends Fragment implements CalendarContract.View {

    private CalendarContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        return view;
    }

    @Override
    public void setPresenter(CalendarContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);

    }
}
