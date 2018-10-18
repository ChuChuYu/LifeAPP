package com.example.e3646.lifeblabla.account;

import android.support.v4.app.FragmentManager;

import com.example.e3646.lifeblabla.dialogfragment.CheckDeleteFragment;
import com.example.e3646.lifeblabla.mainactivity.MainActPresenter;
import com.example.e3646.lifeblabla.object.Note;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class AccountPresenter implements AccountContract.Presenter {

    private AccountContract.View mAccountView;

    private FragmentManager mFragmentManager;
    private MainActPresenter mMainActPresenter;

    private CheckDeleteFragment mCheckDeleteFragment;

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
}
