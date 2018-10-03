package com.example.e3646.lifeblabla.mainactivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.account.AccountFragment;
import com.example.e3646.lifeblabla.account.AccountPresenter;
import com.example.e3646.lifeblabla.addnote.AddNoteContract;
import com.example.e3646.lifeblabla.addnote.AddNoteFragment;
import com.example.e3646.lifeblabla.addnote.AddNotePresenter;
import com.example.e3646.lifeblabla.conference.ConferenceFragment;
import com.example.e3646.lifeblabla.conference.ConferencePresenter;
import com.example.e3646.lifeblabla.diary.DiaryEditFragment;
import com.example.e3646.lifeblabla.diary.DiaryEditPresenter;
import com.example.e3646.lifeblabla.diary.DiaryFragment;
import com.example.e3646.lifeblabla.diary.DiaryPresenter;
import com.example.e3646.lifeblabla.jot.JotFragment;
import com.example.e3646.lifeblabla.jot.JotPresenter;
import com.example.e3646.lifeblabla.main.MainFragment;
import com.example.e3646.lifeblabla.main.MainPresenter;
import com.example.e3646.lifeblabla.object.Note;
import com.example.e3646.lifeblabla.todolist.TodolistFragment;
import com.example.e3646.lifeblabla.todolist.TodolistPresenter;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainActPresenter implements MainActContract.Presenter {

    private MainActContract.View mMainActView;

    private MainFragment mMainFragment;
    private DiaryFragment mDiaryFragment;
    private ConferenceFragment mConferenceFragment;
    private JotFragment mJotFragment;
    private TodolistFragment mTodolistFragment;
    private AccountFragment mAccountFragment;
    private DiaryEditFragment mDiaryEditFragment;
    private AddNoteFragment mAddNoteFragment;

    private MainPresenter mMainPresenter;
    private DiaryPresenter mDiaryPresenter;
    private ConferencePresenter mConferencePresenter;
    private JotPresenter mJotPresenter;
    private TodolistPresenter mTodolistPresenter;
    private AccountPresenter mAccountPresenter;
    private DiaryEditPresenter mDiaryEditPresenter;
    private AddNotePresenter mAddNotePresenter;

    private FragmentManager mFragmentManager;

    private ArrayList<Note> mNoteList;
    private Note mNote;

    public MainActPresenter(MainActContract.View mainActView, FragmentManager fragmentManager, MainFragment mainFragment) {

        mMainActView = checkNotNull(mainActView);
        mMainActView.setPresenter(this);
        mFragmentManager = fragmentManager;
        mMainFragment = mainFragment;

    }

    @Override
    public void start() {

        showMainFragment();
        setViewandPresenter();

    }


    @Override
    public void showMainFragment() {

        mMainActView.showMainUI();

        if (mMainPresenter == null) {
            mMainPresenter = new MainPresenter(mMainFragment, mFragmentManager);
        }

    }

    @Override
    public void setViewandPresenter() {

        mDiaryFragment = new DiaryFragment();
        if (mDiaryPresenter == null) {
            mDiaryPresenter = new DiaryPresenter(mDiaryFragment, mFragmentManager);
        }

        mConferenceFragment = new ConferenceFragment();
        if (mConferencePresenter == null) {
            mConferencePresenter = new ConferencePresenter();
        }

        mJotFragment = new JotFragment();
        mAccountFragment = new AccountFragment();
        mTodolistFragment = new TodolistFragment();
        mDiaryEditFragment = new DiaryEditFragment();

    }

    @Override
    public void showBottomNavigation() {

        mMainActView.showBottomNaviagtion();

    }

    @Override
    public void showToggleButton() {
        mMainActView.showToggleButton();
    }

    @Override
    public void goDiaryDetail() {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.whole_container, mDiaryFragment, "DIARY")
                .hide(mMainFragment)
                .show(mDiaryFragment)
                .addToBackStack(null)
                .commit();

        mMainActView.hideToggleButton();
        mMainActView.hideBottomNavigationBar();
        mMainActView.hideToolBar();

    }

    @Override
    public void goConferenceDetail() {

    }

    @Override
    public void goJotDetail() {

    }

    @Override
    public void goAccountDetail() {

    }

    @Override
    public void goTodolistDetail() {

    }

    @Override
    public void goEditDiary() {

//        mDiaryEditPresenter = new DiaryEditPresenter(mDiaryEditFragment, mFragmentManager, this, mDiaryEditFragment);
//        FragmentTransaction transaction = mFragmentManager.beginTransaction();
//        transaction.add(R.id.whole_container, mDiaryEditFragment, "CREATE DIARY")
//                .hide(mMainFragment)
//                .show(mDiaryEditFragment)
//                .commit();
//
//        mMainActView.hideToggleButton();

    }

    @Override
    public void goAddNotePost() {

        mAddNoteFragment = new AddNoteFragment();

        if (mAddNotePresenter == null) {
            mAddNotePresenter = new AddNotePresenter(mAddNoteFragment, this);
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.whole_container, mAddNoteFragment, "ADD NOTE")
                .hide(mMainFragment)
                .show(mAddNoteFragment)
                .commit();

//        mMainActView.hideToggleButton();

    }


    @Override
    public void completeEdit(ArrayList<Note> noteList) {

        mNoteList = noteList;

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        mMainFragment = new MainFragment(mNoteList);
        transaction.replace(R.id.main_activity_container, mMainFragment)
                .detach(mDiaryEditFragment)
                .commit();

    }

    @Override
    public void cancelEdit() {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.show(mMainFragment)
                .detach(mDiaryEditFragment)
                .commit();

    }



    @Override
    public void switchToGridLayout() {

        mMainPresenter.switchToGridLayout();

    }

    @Override
    public void switchToListLayout() {

        mMainPresenter.switchToListLayout();

    }





}
