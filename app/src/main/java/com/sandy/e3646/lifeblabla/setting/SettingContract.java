package com.sandy.e3646.lifeblabla.setting;

import com.sandy.e3646.BasePresenter;
import com.sandy.e3646.BaseView;

public interface SettingContract {

    interface View extends BaseView<Presenter> {

        void setPresenter(Presenter presenter);

        void printSometing();


    }

    interface Presenter extends BasePresenter {

    }
}
