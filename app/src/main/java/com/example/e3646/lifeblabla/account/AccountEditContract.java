package com.example.e3646.lifeblabla.account;

import android.content.Context;
import android.widget.ImageButton;

import com.example.e3646.BasePresenter;
import com.example.e3646.BaseView;
import com.example.e3646.lifeblabla.object.Note;

public interface AccountEditContract {

    interface View extends BaseView<Presenter> {

        void setPresenter(Presenter presenter);

        void setAmount(String num);

        void setCategoryBack(ImageButton imageButton, String i);

        void takeAccountData();

        void takeNoteData();

        void clearAddItemButtonsheet();

        void hideUI();


    }

    interface Presenter extends BasePresenter {

        void saveAccountData();

        void cancelEditing();

        void completeCreating();

        void completeEditing(Note note);

        void saveNoteData(Context context, Note note);


    }
}
