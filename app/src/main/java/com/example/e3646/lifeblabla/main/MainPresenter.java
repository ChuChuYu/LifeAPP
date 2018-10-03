package com.example.e3646.lifeblabla.main;

import android.support.v4.app.FragmentManager;

import com.example.e3646.lifeblabla.account.AccountFragment;
import com.example.e3646.lifeblabla.account.AccountPresenter;
import com.example.e3646.lifeblabla.conference.ConferenceFragment;
import com.example.e3646.lifeblabla.conference.ConferencePresenter;
import com.example.e3646.lifeblabla.diary.DiaryFragment;
import com.example.e3646.lifeblabla.diary.DiaryPresenter;
import com.example.e3646.lifeblabla.jot.JotFragment;
import com.example.e3646.lifeblabla.jot.JotPresenter;
import com.example.e3646.lifeblabla.todolist.TodolistFragment;
import com.example.e3646.lifeblabla.todolist.TodolistPresenter;
import static com.google.common.base.Preconditions.checkNotNull;

public class MainPresenter implements  MainContract.Presenter {

    private MainContract.View mMainView;
    private FragmentManager mFragmentManager;

    private DiaryFragment mDiaryFragment;
    private ConferenceFragment mConferenceFragment;
    private JotFragment mJotFragment;
    private TodolistFragment mTodolistFragment;
    private AccountFragment mAccountFragment;

    private DiaryPresenter mDiaryPresenter;
    private ConferencePresenter mConferencePresenter;
    private JotPresenter mJotPresenter;
    private TodolistPresenter mTodolistPresenter;
    private AccountPresenter mAccountPresenter;


    public MainPresenter (MainContract.View mainView, FragmentManager fragmentManager) {

        mMainView = checkNotNull(mainView);
        mMainView.setPresenter(this);
        mFragmentManager = fragmentManager;

    }

    @Override
    public void start() {

    }

    @Override
    public void switchToGridLayout() {

        mMainView.showGridLayout();

    }

    @Override
    public void switchToListLayout() {

        mMainView.showListLayout();
    }

    @Override
    public void showDiaryFragment() {

        mDiaryFragment = new DiaryFragment();

        if (mDiaryPresenter == null) {
            mDiaryPresenter = new DiaryPresenter(mDiaryFragment, mFragmentManager);
        }

        mMainView.showDiaryUI();

    }

    @Override
    public void showConferenceFragment() {

        mConferenceFragment = new ConferenceFragment();

        if (mConferencePresenter == null) {
            mConferencePresenter = new ConferencePresenter();
        }

    }

    @Override
    public void showJotFragment() {

//        if (mJotPresenter ==  null) {
//            mJotPresenter = new JotPresenter();
//        }

    }

    @Override
    public void showTodolistFragment() {

        if (mTodolistPresenter == null) {
            mTodolistPresenter = new TodolistPresenter();
        }

    }

    @Override
    public void showAccountFragment() {

        if (mAccountPresenter ==  null) {
            mAccountPresenter = new AccountPresenter();
        }

    }
}
