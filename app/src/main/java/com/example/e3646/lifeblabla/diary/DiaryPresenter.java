package com.example.e3646.lifeblabla.diary;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.dialogfragment.CheckDeleteFragment;
import com.example.e3646.lifeblabla.mainactivity.MainActPresenter;
import com.example.e3646.lifeblabla.object.Note;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class DiaryPresenter implements DiaryContract.Presenter {

    private DiaryContract.View mDiaryView;
    private ArrayList<Note> mNoteList;
    private int mNotePosition;

    private FragmentManager mFragmentManager;
    private DiaryEditFragment mDiaryEditFragment;
    private DiaryEditPresenter mDiaryEditPresenter;
    private MainActPresenter mMainActPresenter;

    private CheckDeleteFragment mCheckDeleteFragment;

    public DiaryPresenter (DiaryContract.View diaryView, FragmentManager fragmentManager, MainActPresenter mainActPresenter, int i, ArrayList<Note> noteList) {
        mDiaryView = checkNotNull(diaryView);
        mDiaryView.setPresenter(this);
        mFragmentManager = fragmentManager;
        mMainActPresenter = mainActPresenter;
        mNotePosition = i;
        mNoteList = noteList;
    }


    @Override
    public void start() {

    }

    @Override
    public void goEditDiary(boolean isCreating) {

        Note note = mNoteList.get(mNotePosition);

        mDiaryEditFragment = new DiaryEditFragment(isCreating, note);

        mDiaryEditPresenter = new DiaryEditPresenter(mDiaryEditFragment, null, mMainActPresenter, null,null, false);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.diary_container, mDiaryEditFragment, "EDIT DIARY")
                .show(mDiaryEditFragment)
                .addToBackStack(null)
                .commit();


//        mDiaryEditPresenter.setNoteList(note);

//        setDiaryData();

    }

    @Override
    public void backToMain() {
        mDiaryView.hideUI();
        mMainActPresenter.backToMain();
    }

    @Override
    public void setDiaryData() {

        Note note = mNoteList.get(mNotePosition);
        mDiaryView.setNoteData(note);
    }

    @Override
    public void refreshDetail() {
        mDiaryView.refreshDetail();
    }

    @Override
    public void completeDeleting() {
        mDiaryView.hideUI();
        mMainActPresenter.refreshMainFragment();
    }

    @Override
    public void showCheckDeleteDialog() {
        mCheckDeleteFragment = new CheckDeleteFragment(this, mNoteList.get(mNotePosition).getmId());
        mCheckDeleteFragment.show(mFragmentManager, null);
    }

    @Override
    public void deleteNoteData(String id) {
        mDiaryView.deleteNoteData(id);

    }
}