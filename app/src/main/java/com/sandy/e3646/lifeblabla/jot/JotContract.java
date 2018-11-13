package com.sandy.e3646.lifeblabla.jot;

import com.sandy.e3646.BasePresenter;
import com.sandy.e3646.BaseView;
import com.sandy.e3646.lifeblabla.object.Note;

public interface JotContract {

    interface View extends BaseView<Presenter> {

        void setPresenter(Presenter presenter);

        void setNoteData(Note note);

        void hideUI();

        void deleteNoteData(String id);





    }

    interface Presenter extends BasePresenter {

        void backToMain(boolean islisting);

        void deleteNoteData(String id);

        void completeDeleting();

        void showCheckDeleteDialog();

        void goEditJot(boolean isCreating, boolean islisting);

        void goSearch(String tag);




    }
}
