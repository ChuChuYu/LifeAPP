package com.example.e3646.lifeblabla.diary;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.main.MainFragment;
import com.example.e3646.lifeblabla.mainactivity.MainActPresenter;
import com.example.e3646.lifeblabla.object.Note;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class DiaryEditPresenter implements DiaryEditContract.Presenter {

    private DiaryEditContract.View mDiaryEditView;
    private FragmentManager mFragmentManager;
    private MainFragment mMainFragment;
    private DiaryEditFragment mDiaryEditFragment;
    private MainActPresenter mMainActPresenter;

    private ArrayList<Note> mNoteList;

    private boolean isCreating = false;

    public DiaryEditPresenter(DiaryEditContract.View diaryEditView, FragmentManager fragmentManager, MainActPresenter mainActPresenter, DiaryEditFragment diaryEditFragment) {
        mDiaryEditView = checkNotNull(diaryEditView);
        mDiaryEditView.setPresenter(this);
        mFragmentManager = fragmentManager;
        mMainActPresenter = mainActPresenter;
        mDiaryEditFragment = diaryEditFragment;

        if (fragmentManager == null && mainActPresenter == null && diaryEditFragment == null) {
            isCreating = true;
        }
    }


    @Override
    public void start() {

    }

    @Override
    public void completeEditDiary() {

        mDiaryEditView.takeDiaryData();
        mMainActPresenter.completeEdit(mNoteList);

    }

    @Override
    public void cancelEditDiary() {

        mMainActPresenter.cancelEdit();
    }

    @Override
    public void saveDiaryData(ArrayList<Note> noteList) {

        mNoteList = noteList;
        Log.d("note", "title: " + mNoteList.get(0).getmTitle());

    }
}
