package com.example.e3646.lifeblabla.account;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.example.e3646.Sqldatabase;
import com.example.e3646.lifeblabla.mainactivity.MainActPresenter;
import com.example.e3646.lifeblabla.object.Note;

import static com.google.common.base.Preconditions.checkNotNull;

public class AccountEditPresenter implements AccountEditContract.Presenter {

    private AccountEditContract.View mAccountEditView;

    private FragmentManager mFragmentManager;
    private MainActPresenter mMainActPresenter;
    private boolean isCreating;

    private Note mNote;
    private Context mContext;
    public AccountEditPresenter (AccountEditContract.View accountEditView, FragmentManager fragmentManager, MainActPresenter mainActPresenter, boolean iscreating) {

        mAccountEditView = checkNotNull(accountEditView);
        mAccountEditView.setPresenter(this);

        mFragmentManager = fragmentManager;
        mMainActPresenter = mainActPresenter;
        isCreating = iscreating;

    }
    @Override
    public void start() {

    }

    @Override
    public void saveAccountData() {

    }

    @Override
    public void cancelEditing() {

        mAccountEditView.hideUI();
        mMainActPresenter.backToMain();

    }

    @Override
    public void completeCreating() {

        mAccountEditView.takeNoteData();
        mAccountEditView.hideUI();
        mMainActPresenter.refreshMainFragment();

    }

    @Override
    public void completeEditing(Note note) {

    }

    @Override
    public void saveNoteData(Context context, Note note) {

        mContext = context;
        mNote = note;
        Sqldatabase sql = new Sqldatabase(mContext);
        sql.insert(mNote);

    }
}
