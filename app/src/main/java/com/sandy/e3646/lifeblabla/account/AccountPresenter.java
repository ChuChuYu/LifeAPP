package com.sandy.e3646.lifeblabla.account;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.dialogfragment.CheckDeleteFragment;
import com.sandy.e3646.lifeblabla.mainactivity.MainActPresenter;
import com.sandy.e3646.lifeblabla.object.Note;
import com.sandy.e3646.lifeblabla.search.SearchFragment;
import com.sandy.e3646.lifeblabla.search.SearchPresenter;
import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class AccountPresenter implements AccountContract.Presenter {

    private AccountContract.View mAccountView;

    private FragmentManager mFragmentManager;
    private MainActPresenter mMainActPresenter;

    private AccountEditFragment mAccountEditFragment;
    protected AccountEditPresenter mAccountEditPresenter;

    private CheckDeleteFragment mCheckDeleteFragment;

    private SearchFragment mSearchFragment;
    private SearchPresenter mSearchPresenter;

    private ArrayList<Note> mNoteList;
    private int mNotePosition;
    private Note mNote;


    public AccountPresenter(AccountContract.View accountView, FragmentManager fragmentManager, MainActPresenter mainActPresenter, ArrayList<Note> noteList, int i, Note note) {
        mAccountView = checkNotNull(accountView);
        mAccountView.setPresenter(this);


        mFragmentManager = fragmentManager;
        mMainActPresenter = mainActPresenter;
        mNoteList = noteList;
        mNotePosition = i;

        mNote = note;


    }

    @Override
    public void start() {

    }

    @Override
    public void backToMain(boolean isListing) {
        mAccountView.hideUI();
        mMainActPresenter.refreshMainFragment();
//        mMainActPresenter.backToMain();
        if (isListing) {
            mMainActPresenter.switchToListLayout();
        } else {
            mMainActPresenter.switchToGridLayout();
        }
    }

    @Override
    public void showCheckDeleteDialog() {
        mCheckDeleteFragment = new CheckDeleteFragment(null, null, this, mNote.getmId(), 3);
        mCheckDeleteFragment.show(mFragmentManager, null);
    }

    @Override
    public void deleteNoteData(String id) {
        mAccountView.deleteNoteData(id);
    }

    @Override
    public void completeDeleting() {

        mAccountView.hideUI();
        mMainActPresenter.refreshMainFragment();
    }

    @Override
    public void goEditAccount(boolean isCreating, Note note, boolean islisting) {

        if (mNoteList != null) {
            mNote = mNoteList.get(mNotePosition);
        } else {
            mNote = note;
        }

        mAccountEditFragment = new AccountEditFragment(false, note, islisting);
        mAccountEditPresenter = new AccountEditPresenter(mAccountEditFragment, mFragmentManager, mMainActPresenter, false);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.account_container, mAccountEditFragment, "EDIT ACCOUNT")
                .show(mAccountEditFragment)
                .addToBackStack(null)
                .commit();


    }

    @Override
    public void goSearch(String tag) {

        mSearchFragment = new SearchFragment(tag);
        mSearchPresenter = new SearchPresenter(mSearchFragment, mFragmentManager, mMainActPresenter);


        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.account_container, mSearchFragment, "SEARCH")
                .show(mSearchFragment)
                .addToBackStack(null)
                .commit();
    }
}
