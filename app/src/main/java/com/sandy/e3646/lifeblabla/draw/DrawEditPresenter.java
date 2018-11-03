package com.sandy.e3646.lifeblabla.draw;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;

import com.sandy.e3646.Sqldatabase;
import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.mainactivity.MainActPresenter;
import com.sandy.e3646.lifeblabla.object.Note;

import static com.google.common.base.Preconditions.checkNotNull;

public class DrawEditPresenter implements DrawEditContract.Presenter {

    private DrawEditContract.View mDrawEditView;
    private MainActPresenter mMainActPresenter;

    public DrawEditPresenter(DrawEditContract.View drawView, MainActPresenter mainActPresenter) {
        mDrawEditView = checkNotNull(drawView);
        mDrawEditView.setPresenter(this);

        mMainActPresenter = mainActPresenter;
    }


    @Override
    public void start() {

    }

    @Override
    public void cancelEditing() {

        mDrawEditView.hideUI();
        mMainActPresenter.refreshMainFragment();

    }

    @Override
    public void completeCreating() {

        mDrawEditView.takeNoteData();
        mDrawEditView.hideUI();
        mMainActPresenter.refreshMainFragment();

    }

    @Override
    public void completeEditing() {

        mDrawEditView.takeNoteData();
        mDrawEditView.hideUI();

//        mJotFragment = new JotFragment(note);
//        mJotPresenter = new JotPresenter(mJotFragment, mFragmentManager, mMainActPresenter, 0, null);

//        FragmentTransaction transaction = mFragmentManager.beginTransaction();
//        transaction.replace(R.id.whole_container, mJotFragment)
//                .show(mJotFragment)
//                .commit();

    }

    @Override
    public void insertNoteData(Context context, Note note) {
        Sqldatabase sql = new Sqldatabase(context);
        sql.insert(note);
    }
}
