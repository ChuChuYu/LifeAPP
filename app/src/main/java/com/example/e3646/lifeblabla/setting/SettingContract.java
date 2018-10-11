package com.example.e3646.lifeblabla.setting;

import com.example.e3646.BasePresenter;
import com.example.e3646.BaseView;

public interface SettingContract {

    interface View extends BaseView<Presenter> {

        void setPresenter(Presenter presenter);


    }

    interface Presenter extends BasePresenter {

    }
}
