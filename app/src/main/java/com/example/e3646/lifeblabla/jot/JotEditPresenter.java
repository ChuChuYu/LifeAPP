package com.example.e3646.lifeblabla.jot;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.e3646.Sqldatabase;
import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.mainactivity.MainActPresenter;
import com.example.e3646.lifeblabla.object.Note;

import org.json.JSONObject;

import static com.google.common.base.Preconditions.checkNotNull;

public class JotEditPresenter implements JotEditContrat.Presenter {


    private JotEditContrat.View mJotEditView;
    private FragmentManager mFragmentManager;
    private MainActPresenter mMainActPresenter;
    private JotPresenter mJotPresenter;
    private boolean isCreating;

    private JotFragment mJotFragment;

    public JotEditPresenter (JotEditContrat.View jotEditView, FragmentManager fragmentManager, MainActPresenter mainActPresenter, JotPresenter jotPresenter, boolean isCreating) {

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
        mMainActPresenter.backToMain();

    }

    @Override
    public void completeCreating() {

        mJotEditView.takeJotData();
        mJotEditView.hideUI();
        mMainActPresenter.refreshMainFragment();

    }

    @Override
    public void completeEditing(Note note) {
        mJotEditView.takeJotData();
        mJotEditView.hideUI();

        mJotFragment = new JotFragment(note);
        mJotPresenter = new JotPresenter(mJotFragment, mFragmentManager, mMainActPresenter, 0, null);

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
