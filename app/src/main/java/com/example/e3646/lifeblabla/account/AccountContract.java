package com.example.e3646.lifeblabla.account;

import com.example.e3646.BasePresenter;
import com.example.e3646.BaseView;

public interface AccountContract {

    interface View extends BaseView<Presenter> {


        void setPresenter(Presenter presenter);

        void setNoteData();

        void hideUI();

        void deleteNoteData(String id);
    }

    interface Presenter extends BasePresenter {

        void backToMain();

        void showCheckDeleteDialog();

        void deleteNoteData(String id);

        void completeDeleting();


    }
}
