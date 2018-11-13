package com.sandy.e3646.lifeblabla.jot;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.sandy.e3646.Sqldatabase;
import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.mainactivity.MainActPresenter;
import com.sandy.e3646.lifeblabla.object.Note;

import static com.google.common.base.Preconditions.checkNotNull;

public class JotEditPresenter implements JotEditContrat.Presenter {

    private JotEditContrat.View mJotEditView;
    private FragmentManager mFragmentManager;
    private MainActPresenter mMainActPresenter;
    private JotPresenter mJotPresenter;
    private boolean isCreating;

    private JotFragment mJotFragment;

    public JotEditPresenter(JotEditContrat.View jotEditView, FragmentManager fragmentManager, MainActPresenter mainActPresenter, JotPresenter jotPresenter, boolean isCreating) {

        mJotEditView = checkNotNull(jotEditView);
        mJotEditView.setPresenter(this);
        mFragmentManager = fragmentManager;
        mMainActPresenter = mainActPresenter;
        mJotPresenter = jotPresenter;
        this.isCreating = isCreating;

    }

    @Override
    public void start() {

    }

    @Override
    public void cancelEditing() {

        mJotEditView.hideUI();
        mMainActPresenter.refreshMainFragment();

    }

    @Override
    public void completeCreating(boolean islisting) {

        mJotEditView.takeJotData();
        mJotEditView.hideUI();
        mMainActPresenter.refreshMainFragment();

        if (islisting == true) {
            mMainActPresenter.switchToListLayout();
        } else {
            mMainActPresenter.switchToGridLayout();
        }

    }

    @Override
    public void completeEditing(Note note, boolean islisting) {
        mJotEditView.takeJotData();
        mJotEditView.hideUI();

        mJotFragment = new JotFragment(note, islisting);
        mJotPresenter = new JotPresenter(mJotFragment, mFragmentManager, mMainActPresenter, 0, null, note);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.whole_container, mJotFragment)
                .show(mJotFragment)
                .commit();
    }

    @Override
    public void insertJotData(Context context, Note note) {
        Sqldatabase sql = new Sqldatabase(context);
        sql.insert(note);
    }

    @Override
    public void updateJotData(Context context, Note note) {
        Sqldatabase sql = new Sqldatabase(context);
        sql.updateNotes(note.getmId(), note);
    }
}
