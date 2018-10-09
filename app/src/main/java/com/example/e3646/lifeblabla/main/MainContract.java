package com.example.e3646.lifeblabla.main;

import android.content.Context;

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

        void refreshList();

        void setIsListMode(boolean isListMode);

        void hideUI();

    }

    interface Presenter extends BasePresenter {

        void setContext(Context context);

        void switchToGridLayout();

        void switchToListLayout();


        void showDiaryFragment(int i);

        void showConferenceFragment();

        void showJotFragment();

        void showTodolistFragment();

        void showAccountFragment();

        void refreshList();

        void takeNoteList();

        void takeNoteListPosition(int i);


    }
}