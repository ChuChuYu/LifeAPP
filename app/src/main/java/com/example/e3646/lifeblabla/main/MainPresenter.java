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
    public void showFragment(int i) {

        Sqldatabase sql = new Sqldatabase(mContext);
        mNoteList = sql.getNotes();
        mNoteListPosition = i;
        Note note = mNoteList.get(mNoteListPosition);
        mMainView.hideUI();

        if (mNoteList.get(mNoteListPosition).getmClassification().equals("diary")) {

            mDiaryFragment = new DiaryFragment(note);
            mDiaryPresenter = new DiaryPresenter(mDiaryFragment, mFragmentManager, mMainActPresenter, mNoteListPosition, mNoteList);
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.replace(R.id.whole_container, mDiaryFragment, "DIARY")
                    .show(mDiaryFragment)
                    .addToBackStack(null)
                    .commit();
        } else if (mNoteList.get(mNoteListPosition).getmClassification().equals("jot")) {
            mJotFragment = new JotFragment(note);
            mJotPresenter = new JotPresenter(mJotFragment, mFragmentManager, mMainActPresenter, mNoteListPosition, mNoteList);
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.replace(R.id.whole_container, mJotFragment, "JOT")
                    .show(mJotFragment)
                    .addToBackStack(null)
                    .commit();


        } else if (mNoteList.get(mNoteListPosition).getmClassification().equals("account")) {


            mAccountFragment = new AccountFragment(note);
            mAccountPresenter = new AccountPresenter(mAccountFragment);
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.replace(R.id.whole_container, mAccountFragment, "ACCOUNT")
                    .show(mAccountFragment)
                    .addToBackStack(null)
                    .commit();
        }


        mMainActPresenter.goDiaryDetail(); //just hide component

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
