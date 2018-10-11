package com.example.e3646.lifeblabla.addnote;

import android.support.v4.app.FragmentManager;
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
    private DiaryEditFragment mDiaryEditFragment;
    private DiaryEditPresenter mDiaryEditPresenter;

    private JotEditFragment mJotEditFragment;

    private FragmentManager mFragmentManager;

    public AddNotePresenter(AddNoteContract.View addNoteView, MainActPresenter mainActPresenter, FragmentManager fragmentManager) {

        mAddNoteView = checkNotNull(addNoteView);
        mAddNoteView.setPresenter(this);
        mMainActPresenter = mainActPresenter;
        mFragmentManager = fragmentManager;

    }

    @Override
    public void start() {

    }

    @Override
    public void goCreateDiary() {

        mDiaryEditFragment = new DiaryEditFragment(true, null);
        mDiaryEditPresenter = new DiaryEditPresenter(mDiaryEditFragment, mFragmentManager, mMainActPresenter, null, this, true);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.add_note_container, mDiaryEditFragment, "CREATE DIARY")
                .addToBackStack(null)
                .commit();

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
        mMainActPresenter.backToMain();

    }

    @Override
    public void hideUI() {
        mAddNoteView.hideUI();
    }
}
