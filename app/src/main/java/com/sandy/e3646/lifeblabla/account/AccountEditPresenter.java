package com.sandy.e3646.lifeblabla.account;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.sandy.e3646.Sqldatabase;
import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.mainactivity.MainActPresenter;
import com.sandy.e3646.lifeblabla.object.Note;

import static com.google.common.base.Preconditions.checkNotNull;

public class AccountEditPresenter implements AccountEditContract.Presenter {

    private AccountEditContract.View mAccountEditView;
    private FragmentManager mFragmentManager;
    private MainActPresenter mMainActPresenter;
    private AccountFragment mAccountFragment;
    private AccountPresenter mAccountPresenter;
    private boolean isCreating;
    private Note mNote;
    private Context mContext;

    public AccountEditPresenter(AccountEditContract.View accountEditView, FragmentManager fragmentManager, MainActPresenter mainActPresenter, boolean iscreating) {

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
    public void cancelEditing() {

        mAccountEditView.hideUi();
        mMainActPresenter.refreshMainFragment();

    }

    @Override
    public void completeCreating(boolean islisting) {

        mAccountEditView.takeNoteData();
        mAccountEditView.hideUi();
        mMainActPresenter.refreshMainFragment();
        if (islisting == true) {
            mMainActPresenter.switchToListLayout();
        } else {
            mMainActPresenter.switchToGridLayout();
        }

    }

    @Override
    public void completeEditing(Note note) {

        mAccountEditView.hideUi();

        mAccountFragment = new AccountFragment(note, true);
        mAccountPresenter = new AccountPresenter(mAccountFragment, mFragmentManager, mMainActPresenter, null, 0, note);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.whole_container, mAccountFragment)
                .show(mAccountFragment)
                .commit();

    }

    @Override
    public void saveNoteData(Context context, Note note) {

        mContext = context;
        mNote = note;
        Sqldatabase sql = new Sqldatabase(mContext);
        sql.insert(mNote);

    }
}
