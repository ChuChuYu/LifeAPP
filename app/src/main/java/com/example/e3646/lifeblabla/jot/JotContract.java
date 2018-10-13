package com.example.e3646.lifeblabla.jot;

import android.widget.ImageView;

import com.example.e3646.BasePresenter;
import com.example.e3646.BaseView;
import com.example.e3646.lifeblabla.diary.DiaryEditContract;
import com.example.e3646.lifeblabla.object.Note;

import java.util.ArrayList;

public interface JotContract {

    interface View extends BaseView<Presenter> {

        void setPresenter(Presenter presenter);

        void setNoteData(Note note);

        void hideUI();

        void deleteNoteData(String id);





    }

    interface Presenter extends BasePresenter {

        void backToMain();

        void deleteNoteData(String id);

        void completeDeleting();

        void showCheckDeleteDialog();

        void goEditJot(boolean isCreating);




    }
}
