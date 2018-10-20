package com.example.e3646.lifeblabla.account;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.Search.SearchFragment;
import com.example.e3646.lifeblabla.Search.SearchPresenter;
import com.example.e3646.lifeblabla.dialogfragment.CheckDeleteFragment;
import com.example.e3646.lifeblabla.mainactivity.MainActPresenter;
import com.example.e3646.lifeblabla.object.Note;

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


    public AccountPresenter(AccountContract.View accountView, FragmentManager fragmentManager, MainActPresenter mainActPresenter, ArrayList<Note> noteList, int i) {
        mAccountView = checkNotNull(accountView);
        mAccountView.setPresenter(this);


        mFragmentManager = fragmentManager;
        mMainActPresenter = mainActPresenter;
        mNoteList = noteList;
        mNotePosition = i;



    }
    @Override
    public void start() {

    }

    @Override
    public void backToMain() {
        mAccountView.hideUI();
        mMainActPresenter.refreshMainFragment();
        mMainActPresenter.backToMain();
    }

    @Override
    public void showCheckDeleteDialog() {
        mCheckDeleteFragment = new CheckDeleteFragment(null, null, this, mNoteList.get(mNotePosition).getmId(), 3);
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
    public void goEditAccount(boolean isCreating) {
        Note note = mNoteList.get(mNotePosition);

        mAccountEditFragment = new AccountEditFragment(false, note);
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
        mSearchPresenter = new SearchPresenter(mSearchFragment);


        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.account_container, mSearchFragment, "SEARCH")
                .show(mSearchFragment)
                .addToBackStack(null)
                .commit();
    }
}
