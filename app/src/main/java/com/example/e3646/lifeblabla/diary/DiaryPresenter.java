package com.example.e3646.lifeblabla.diary;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.mainactivity.MainActPresenter;

import static com.google.common.base.Preconditions.checkNotNull;

public class DiaryPresenter implements DiaryContract.Presenter {

    private DiaryContract.View mDiaryView;

    private FragmentManager mFragmentManager;
    private DiaryEditFragment mDiatyEditFragment;
    private MainActPresenter mMainActPresenter;


    public DiaryPresenter (DiaryContract.View diaryView, FragmentManager fragmentManager) {
        mDiaryView = checkNotNull(diaryView);
        mDiaryView.setPresenter(this);
        mFragmentManager = fragmentManager;
    }


    @Override
    public void start() {

    }

    @Override
    public void goEditDiary() {

        mDiatyEditFragment = new DiaryEditFragment();

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.diary_container, mDiatyEditFragment, "EDIT DIARY")
                .show(mDiatyEditFragment)
                .addToBackStack(null)
                .commit();

        setDiaryData();

    }

    @Override
    public void setDiaryData() {

    }
}
