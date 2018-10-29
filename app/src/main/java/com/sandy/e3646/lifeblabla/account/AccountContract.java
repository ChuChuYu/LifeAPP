package com.sandy.e3646.lifeblabla.account;

import com.sandy.e3646.BasePresenter;
import com.sandy.e3646.BaseView;
import com.sandy.e3646.lifeblabla.object.Note;

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

        void goEditAccount(boolean isCreating, Note note);

        void goSearch(String tag);


    }
}
