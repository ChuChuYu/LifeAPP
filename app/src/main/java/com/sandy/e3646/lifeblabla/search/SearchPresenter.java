package com.sandy.e3646.lifeblabla.search;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.sandy.e3646.Sqldatabase;
import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.account.AccountFragment;
import com.sandy.e3646.lifeblabla.account.AccountPresenter;
import com.sandy.e3646.lifeblabla.diary.DiaryFragment;
import com.sandy.e3646.lifeblabla.diary.DiaryPresenter;
import com.sandy.e3646.lifeblabla.jot.JotFragment;
import com.sandy.e3646.lifeblabla.jot.JotPresenter;
import com.sandy.e3646.lifeblabla.mainactivity.MainActPresenter;
import com.sandy.e3646.lifeblabla.object.Account;
import com.sandy.e3646.lifeblabla.object.Note;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View mSearchView;

    private DiaryFragment mDiaryFragment;
    private JotFragment mJotFragment;
    private AccountFragment mAccountFragment;

    private DiaryPresenter mDiaryPresenter;
    private JotPresenter mJotPresenter;
    private AccountPresenter mAccountPresenter;

    private FragmentManager mFragmentManager;
    private MainActPresenter mMainActPresenter;



    public SearchPresenter(SearchContract.View searchView, FragmentManager fragmentManager, MainActPresenter mainActPresenter) {
        mSearchView = checkNotNull(searchView);
        mSearchView.setPresenter(this);

        mFragmentManager = fragmentManager;
        mainActPresenter = mainActPresenter;
    }

    @Override
    public void start() {

    }

    @Override
    public ArrayList<Note> getSearchList(Context context, String tag) {

        ArrayList<Note> searchList = new ArrayList<Note>();

        Sqldatabase sql = new Sqldatabase(context);
        ArrayList<Note> noteList = sql.getNotes();

        for (int i = 0; i < noteList.size(); i ++) {
            if ( noteList.get(i).getmTag().contains(tag)) {
                searchList.add(noteList.get(i));
            }
        }

        return searchList;
    }

    @Override
    public void showFragment(Note note) {
        if (note.getmClassification().equals("diary")) {

            mDiaryFragment = new DiaryFragment(note, true);
            mDiaryPresenter = new DiaryPresenter(mDiaryFragment, mFragmentManager, mMainActPresenter, note);
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.replace(R.id.whole_container, mDiaryFragment, "DIARY")
                    .show(mDiaryFragment)
                    .addToBackStack(null)
                    .commit();
        } else if (note.getmClassification().equals("jot")) {
            mJotFragment = new JotFragment(note, true);
            mJotPresenter = new JotPresenter(mJotFragment, mFragmentManager, mMainActPresenter,0, null, note);
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.replace(R.id.whole_container, mJotFragment, "JOT")
                    .show(mJotFragment)
                    .addToBackStack(null)
                    .commit();

        } else if (note.getmClassification().equals("account")) {

            mAccountFragment = new AccountFragment(note, true);
            mAccountPresenter = new AccountPresenter(mAccountFragment, mFragmentManager, mMainActPresenter, null, 0, note);
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.replace(R.id.whole_container, mAccountFragment, "ACCOUNT")
                    .show(mAccountFragment)
                    .addToBackStack(null)
                    .commit();
        }

//        mMainActPresenter.goDiaryDetail();
    }
}
