package com.example.e3646.lifeblabla.addnote;

import android.support.v4.app.FragmentTransaction;

import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.diary.DiaryEditFragment;
import com.example.e3646.lifeblabla.diary.DiaryEditPresenter;
import com.example.e3646.lifeblabla.jot.JotEditContrat;
import com.example.e3646.lifeblabla.jot.JotEditFragment;
import com.example.e3646.lifeblabla.mainactivity.MainActPresenter;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddNotePresenter implements AddNoteContract.Presenter {

    private AddNoteContract.View mAddNoteView;
    private MainActPresenter mMainActPresenter;

    private JotEditFragment mJotEditFragment;

    public AddNotePresenter(AddNoteContract.View addNoteView, MainActPresenter mainActPresenter) {

        mAddNoteView = checkNotNull(addNoteView);
        mAddNoteView.setPresenter(this);
        mMainActPresenter = mainActPresenter;

    }

    @Override
    public void start() {

    }

    @Override
    public void goCreateDiary() {

        mAddNoteView.goCreateDiary();

    }

    @Override
    public void goCreateJot() {

        mAddNoteView.goCreateJot();
    }

    @Override
    public void goCreateConference() {

    }

    @Override
    public void goCreateAccount() {

        mAddNoteView.goCreateAccount();

    }

    @Override
    public void goCreateTodolist() {

    }

    @Override
    public void backToMain() {

        mAddNoteView.backToMain();
        mMainActPresenter.showBottomNavigation();
        mMainActPresenter.showToggleButton();


    }
}
