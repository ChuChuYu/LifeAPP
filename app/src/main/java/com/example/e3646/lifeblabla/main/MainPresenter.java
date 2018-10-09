package com.example.e3646.lifeblabla.main;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.e3646.Sqldatabase;
import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.account.AccountFragment;
import com.example.e3646.lifeblabla.account.AccountPresenter;
import com.example.e3646.lifeblabla.conference.ConferenceFragment;
import com.example.e3646.lifeblabla.conference.ConferencePresenter;
import com.example.e3646.lifeblabla.diary.DiaryFragment;
import com.example.e3646.lifeblabla.diary.DiaryPresenter;
import com.example.e3646.lifeblabla.jot.JotFragment;
import com.example.e3646.lifeblabla.jot.JotPresenter;
import com.example.e3646.lifeblabla.mainactivity.MainActPresenter;
import com.example.e3646.lifeblabla.object.Note;
import com.example.e3646.lifeblabla.todolist.TodolistFragment;
import com.example.e3646.lifeblabla.todolist.TodolistPresenter;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mMainView;
    private FragmentManager mFragmentManager;
    private Context mContext;
    private int mNoteListPosition;

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
    private MainActPresenter mMainActPresenter;

    private ArrayList<Note> mNoteList;


    public MainPresenter (MainContract.View mainView, FragmentManager fragmentManager, MainActPresenter mainActPresenter) {

        mMainView = checkNotNull(mainView);
        mMainView.setPresenter(this);
        mFragmentManager = fragmentManager;

        mMainActPresenter = mainActPresenter;

    }

    @Override
    public void start() {

    }

    @Override
    public void setContext(Context context) {
        mContext = context;
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
    public void showDiaryFragment(int i) {

//        mDiaryFragment = new DiaryFragment();
//
//        if (mDiaryPresenter == null) {
//            mDiaryPresenter = new DiaryPresenter(mDiaryFragment, mFragmentManager, );
//        }
//
//        mMainView.showDiaryUI();

        Sqldatabase sql = new Sqldatabase(mContext);
        mNoteList = sql.getNotes();
        mNoteListPosition = i;
        Note note = mNoteList.get(mNoteListPosition);
        Log.d("item tag", "step3: " + mNoteListPosition);
        mMainView.hideUI();
        mDiaryFragment = new DiaryFragment(note);
        if (mDiaryPresenter == null) {
            mDiaryPresenter = new DiaryPresenter(mDiaryFragment, mFragmentManager, mMainActPresenter, mNoteListPosition, mNoteList);
        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.whole_container, mDiaryFragment, "DIARY")
                .show(mDiaryFragment)
                .addToBackStack(null)
                .commit();

        mMainActPresenter.goDiaryDetail();
//        mDiaryPresenter.setDiaryData();

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

    @Override
    public void refreshList() {
        mMainView.refreshList();
    }

    @Override
    public void takeNoteList() {
        Sqldatabase sql = new Sqldatabase(mContext);
        mNoteList = sql.getNotes();
    }

    @Override
    public void takeNoteListPosition(int i) {
        mNoteListPosition = i;
        Log.d("item tag", "step2: " + mNoteListPosition);
    }
}
