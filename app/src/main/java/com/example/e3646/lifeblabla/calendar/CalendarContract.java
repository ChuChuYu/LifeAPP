package com.example.e3646.lifeblabla.calendar;

import com.example.e3646.BasePresenter;
import com.example.e3646.BaseView;

public interface CalendarContract {

    interface View extends BaseView<Presenter> {

        void setPresenter(Presenter presenter);


    }

    interface Presenter extends BasePresenter {

    }
}
