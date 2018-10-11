package com.example.e3646.lifeblabla.map;

import com.example.e3646.BasePresenter;
import com.example.e3646.BaseView;

public interface MapContract {

    interface View extends BaseView<Presenter> {

        void setPresenter(Presenter presenter);


    }

    interface Presenter extends BasePresenter {

    }
}
