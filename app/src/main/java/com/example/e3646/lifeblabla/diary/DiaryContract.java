package com.example.e3646.lifeblabla.diary;

import android.support.v4.app.Fragment;

import com.example.e3646.BasePresenter;
import com.example.e3646.BaseView;
import com.example.e3646.lifeblabla.object.Note;

public interface DiaryContract {

    interface View extends BaseView<Presenter> {

        void setPresenter(Presenter presenter);

        void hideUI();

        void setNoteData(Note note);

        void deleteNoteData(String id);

        void parseNote(Note note);

    }

    interface Presenter extends BasePresenter {

        void goEditDiary(boolean isCreating, Note note);

        void backToMain();

        void setDiaryData();

        void refreshDetail(Note note);

        void completeDeleting();

        void showCheckDeleteDialog();

        void deleteNoteData(String id);

        void parseNote(Note note);

        void goSearch(String tag);

    }
}
