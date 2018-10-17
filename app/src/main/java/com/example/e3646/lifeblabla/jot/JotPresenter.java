package com.example.e3646.lifeblabla.jot;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.dialogfragment.CheckDeleteFragment;
import com.example.e3646.lifeblabla.mainactivity.MainActPresenter;
import com.example.e3646.lifeblabla.object.Note;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class JotPresenter implements JotContract.Presenter {

    private JotContract.View mJotView;

    private JotEditFragment mJotEditFragment;
    private JotEditPresenter mJotEditPresenter;


    private FragmentManager mFragmentManager;
    private MainActPresenter mMainActPresenter;
    private CheckDeleteFragment mCheckDeleteFragment;
    private ArrayList<Note> mNoteList;
    private int mNotePosition;

    public JotPresenter (JotContract.View jotView, FragmentManager fragmentManager, MainActPresenter mainActPresenter, int i, ArrayList<Note> noteList) {

        mJotView = checkNotNull(jotView);
        mJotView.setPresenter(this);

        mFragmentManager = fragmentManager;
        mMainActPresenter = mainActPresenter;
        mNotePosition = i;
        mNoteList = noteList;

    }

    @Override
    public void start() {

    }

    @Override
    public void backToMain() {

        mJotView.hideUI();
        mMainActPresenter.refreshMainFragment();
        mMainActPresenter.backToMain();

    }

    @Override
    public void deleteNoteData(String id) {
        mJotView.deleteNoteData(id);
    }

    @Override
    public void completeDeleting() {
        backToMain();

    }

    @Override
    public void showCheckDeleteDialog() {
        mCheckDeleteFragment = new CheckDeleteFragment(null, this,  mNoteList.get(mNotePosition).getmId(), 2);
        mCheckDeleteFragment.show(mFragmentManager, null);
    }

    @Override
    public void goEditJot(boolean isCreating) {
        Note note = mNoteList.get(mNotePosition);

        mJotEditFragment = new JotEditFragment(isCreating, note);
        mJotEditPresenter = new JotEditPresenter(mJotEditFragment, mFragmentManager, mMainActPresenter, this, false);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.diary_container, mJotEditFragment, "EDIT DIARY")
                .show(mJotEditFragment)
                .addToBackStack(null)
                .commit();
    }
}
