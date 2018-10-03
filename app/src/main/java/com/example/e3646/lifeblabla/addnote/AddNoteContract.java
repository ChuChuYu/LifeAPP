package com.example.e3646.lifeblabla.addnote;

import com.example.e3646.BasePresenter;
import com.example.e3646.BaseView;
import com.example.e3646.lifeblabla.diary.DiaryContract;

public interface AddNoteContract {

    interface View extends BaseView<AddNoteContract.Presenter> {

        void setPresenter(AddNoteContract.Presenter presenter);

        void goCreateDiary();

        void goCreateJot();

        void goCreateAccount();

        void backToMain();

        void setModeSelection(String modeSelection);

        void getModeSelection();


    }

    interface Presenter extends BasePresenter {

        void goCreateDiary();

        void goCreateJot();

        void goCreateConference();

        void goCreateAccount();

        void goCreateTodolist();

        void backToMain();

    }
}
