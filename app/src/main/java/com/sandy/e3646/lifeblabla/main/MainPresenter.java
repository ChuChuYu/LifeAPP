package com.sandy.e3646.lifeblabla.main;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.sandy.e3646.Sqldatabase;
import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.account.AccountFragment;
import com.sandy.e3646.lifeblabla.account.AccountPresenter;

import com.sandy.e3646.lifeblabla.diary.DiaryFragment;
import com.sandy.e3646.lifeblabla.diary.DiaryPresenter;
import com.sandy.e3646.lifeblabla.jot.JotFragment;
import com.sandy.e3646.lifeblabla.jot.JotPresenter;
import com.sandy.e3646.lifeblabla.mainactivity.MainActPresenter;
import com.sandy.e3646.lifeblabla.object.Note;


import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mMainView;
    private MainContract.View mMainDiaryView;
    private MainContract.View mMainJotView;
    private MainContract.View mMainAccountView;

    private FragmentManager mFragmentManager;
    private Context mContext;
    private int mNoteListPosition;

    private DiaryFragment mDiaryFragment;
    private JotFragment mJotFragment;
    private AccountFragment mAccountFragment;
    private DiaryPresenter mDiaryPresenter;
    private JotPresenter mJotPresenter;
    private AccountPresenter mAccountPresenter;
    private MainActPresenter mMainActPresenter;

    private ArrayList<Note> mNoteList;

    public MainPresenter (MainContract.View mainView, MainContract.View mainDiaryView, MainContract.View mainJotView, MainContract.View mainAccountView, FragmentManager fragmentManager, MainActPresenter mainActPresenter) {

        mMainView = checkNotNull(mainView);
        mMainView.setPresenter(this);
        mMainDiaryView = checkNotNull(mainDiaryView);
        mMainDiaryView.setPresenter(this);
        mMainJotView = checkNotNull(mainJotView);
        mMainJotView.setPresenter(this);
        mMainAccountView = mainAccountView;
        mMainAccountView.setPresenter(this);

        mFragmentManager = fragmentManager;
        mMainActPresenter = mainActPresenter;

    }

    @Override
    public void start() { }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public void switchToGridLayout() {

        mMainView.showGridLayout();
        mMainDiaryView.showGridLayout();
        mMainJotView.showGridLayout();
        mMainAccountView.showGridLayout();

    }

    @Override
    public void switchToListLayout() {

        mMainView.showListLayout();
        mMainDiaryView.showListLayout();
        mMainJotView.showListLayout();
        mMainAccountView.showListLayout();
    }

    //  沒有用到的
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
            mAccountPresenter = new AccountPresenter(mAccountFragment, mFragmentManager, mMainActPresenter, mNoteList, mNoteListPosition);
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
    }

    @Override
    public void showdiary(Note note) {


        if (note.getmClassification().equals("diary")) {

            mDiaryFragment = new DiaryFragment(note);
            mDiaryPresenter = new DiaryPresenter(mDiaryFragment, mFragmentManager, mMainActPresenter, mNoteListPosition, mNoteList);
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.replace(R.id.whole_container, mDiaryFragment, "DIARY")
                    .show(mDiaryFragment)
                    .addToBackStack(null)
                    .commit();
        } else if (note.getmClassification().equals("jot")) {
            mJotFragment = new JotFragment(note);
            mJotPresenter = new JotPresenter(mJotFragment, mFragmentManager, mMainActPresenter, mNoteListPosition, mNoteList);
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.replace(R.id.whole_container, mJotFragment, "JOT")
                    .show(mJotFragment)
                    .addToBackStack(null)
                    .commit();


        } else if (note.getmClassification().equals("account")) {

            mAccountFragment = new AccountFragment(note);
            mAccountPresenter = new AccountPresenter(mAccountFragment, mFragmentManager, mMainActPresenter, mNoteList, mNoteListPosition);
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.replace(R.id.whole_container, mAccountFragment, "ACCOUNT")
                    .show(mAccountFragment)
                    .addToBackStack(null)
                    .commit();
        }

        mMainActPresenter.goDiaryDetail();

    }
}
