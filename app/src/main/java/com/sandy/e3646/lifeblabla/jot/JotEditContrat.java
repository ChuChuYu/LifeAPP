package com.sandy.e3646.lifeblabla.jot;

import android.content.Context;

import com.sandy.e3646.BasePresenter;
import com.sandy.e3646.BaseView;
import com.sandy.e3646.lifeblabla.object.Note;

public interface JotEditContrat {

    interface View extends BaseView<Presenter> {

        void setPresenter(Presenter presenter);

        void hideUI();

        void takeJotData();

        void getTagEditFocus();

        void getTagEditUnFocus();


    }

    interface Presenter extends BasePresenter {

        void cancelEditing();

        void completeCreating();

        void completeEditing(Note note);

        void insertJotData(Context context, Note note);

        void updateJotData(Context context, Note note);



    }
}
