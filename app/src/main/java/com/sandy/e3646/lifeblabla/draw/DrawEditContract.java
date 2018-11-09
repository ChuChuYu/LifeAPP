package com.sandy.e3646.lifeblabla.draw;

import android.content.Context;

import com.sandy.e3646.BasePresenter;
import com.sandy.e3646.BaseView;
import com.sandy.e3646.lifeblabla.object.Note;

public interface DrawEditContract {

    interface View extends BaseView<Presenter> {


        void setPresenter(Presenter presenter);

        void takeNoteData();

        void hideUI();

        void getTagEditFocus();

        void getTagEditUnFocus();


    }

    interface Presenter extends BasePresenter {

        void cancelEditing();

        void completeCreating();

        void completeEditing();

        void insertNoteData(Context context, Note note);


    }
}
