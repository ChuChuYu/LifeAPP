package com.example.e3646.lifeblabla.main;

import com.example.e3646.BasePresenter;
import com.example.e3646.BaseView;

public interface MainContract {

    interface View extends BaseView<Presenter> {

        void setPresenter(Presenter presenter);

        void showGridLayout();

        void showListLayout();


        void showDiaryUI();

        void showConferenceUI();

        void showJotUI();

        void showTodolistUI();

        void showAccountUI();


    }

    interface Presenter extends BasePresenter {

        void switchToGridLayout();

        void switchToListLayout();


        void showDiaryFragment();

        void showConferenceFragment();

        void showJotFragment();

        void showTodolistFragment();

        void showAccountFragment();


    }
}
