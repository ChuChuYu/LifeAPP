package com.example.e3646.lifeblabla.main;

import android.content.Context;

import com.example.e3646.BasePresenter;
import com.example.e3646.BaseView;
import com.example.e3646.lifeblabla.object.Note;

public interface MainContract {

    interface View extends BaseView<Presenter> {

        void setPresenter(Presenter presenter);

        void showGridLayout();

        void showListLayout();


        void showDiaryUI();


        void refreshList();

        void hideUI();

    }

    interface Presenter extends BasePresenter {

        void setContext(Context context);

        void switchToGridLayout();

        void switchToListLayout();


        void showFragment(int i);

        void refreshList();

        void takeNoteList();

        void takeNoteListPosition(int i);

        void showdiary(Note note);


    }
}
