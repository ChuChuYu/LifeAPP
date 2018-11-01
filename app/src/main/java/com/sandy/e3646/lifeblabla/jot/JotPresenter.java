package com.sandy.e3646.lifeblabla.jot;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.search.SearchFragment;
import com.sandy.e3646.lifeblabla.search.SearchPresenter;
import com.sandy.e3646.lifeblabla.dialogfragment.CheckDeleteFragment;
import com.sandy.e3646.lifeblabla.mainactivity.MainActPresenter;
import com.sandy.e3646.lifeblabla.object.Note;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class JotPresenter implements JotContract.Presenter {

    private JotContract.View mJotView;

    private JotEditFragment mJotEditFragment;
    private JotEditPresenter mJotEditPresenter;

    private SearchFragment mSearchFragment;
    private SearchPresenter mSearchPresenter;


    private FragmentManager mFragmentManager;
    private MainActPresenter mMainActPresenter;
    private CheckDeleteFragment mCheckDeleteFragment;
    private ArrayList<Note> mNoteList;
    private int mNotePosition;

    public JotPresenter(JotContract.View jotView, FragmentManager fragmentManager, MainActPresenter mainActPresenter, int i, ArrayList<Note> noteList) {

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
//        mMainActPresenter.backToMain();

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
        mCheckDeleteFragment = new CheckDeleteFragment(null, this, null,  mNoteList.get(mNotePosition).getmId(), 2);
        mCheckDeleteFragment.show(mFragmentManager, null);
    }

    @Override
    public void goEditJot(boolean isCreating) {
        Note note = mNoteList.get(mNotePosition);

        mJotEditFragment = new JotEditFragment(isCreating, note, note.getmPicture(), Uri.parse(note.getPhotoFromCamera()));
        mJotEditPresenter = new JotEditPresenter(mJotEditFragment, mFragmentManager, mMainActPresenter, this, false);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.diary_container, mJotEditFragment, "EDIT DIARY")
                .show(mJotEditFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goSearch(String tag) {
        mSearchFragment = new SearchFragment(tag);
        mSearchPresenter = new SearchPresenter(mSearchFragment);


        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.jot_container, mSearchFragment, "SEARCH")
                .show(mSearchFragment)
                .addToBackStack(null)
                .commit();
    }
}
