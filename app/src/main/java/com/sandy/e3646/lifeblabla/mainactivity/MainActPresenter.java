package com.sandy.e3646.lifeblabla.mainactivity;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.account.AccountEditFragment;
import com.sandy.e3646.lifeblabla.account.AccountEditPresenter;

import com.sandy.e3646.lifeblabla.dialogfragment.BottomSheetDialogJotFragment;
import com.sandy.e3646.lifeblabla.dialogfragment.BottomSheetDialogTemplateFragment;
import com.sandy.e3646.lifeblabla.diary.DiaryEditFragment;
import com.sandy.e3646.lifeblabla.diary.DiaryEditPresenter;
import com.sandy.e3646.lifeblabla.diary.DiaryFragment;
import com.sandy.e3646.lifeblabla.diary.DiaryPresenter;
import com.sandy.e3646.lifeblabla.draw.DrawEditFragment;
import com.sandy.e3646.lifeblabla.draw.DrawEditPresenter;
import com.sandy.e3646.lifeblabla.jot.JotEditFragment;
import com.sandy.e3646.lifeblabla.jot.JotEditPresenter;
import com.sandy.e3646.lifeblabla.main.MainAccountFragment;
import com.sandy.e3646.lifeblabla.main.MainDiaryFragment;
import com.sandy.e3646.lifeblabla.main.MainFragment;
import com.sandy.e3646.lifeblabla.main.MainJotFragment;
import com.sandy.e3646.lifeblabla.main.MainPresenter;

import com.sandy.e3646.lifeblabla.object.Note;
import com.sandy.e3646.lifeblabla.setting.SettingFragment;
import com.sandy.e3646.lifeblabla.setting.SettingPresenter;


import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainActPresenter implements MainActContract.Presenter {

    private MainActContract.View mMainActView;
    private Context mContext;

    private MainFragment mMainFragment;
    private MainDiaryFragment mMainDiaryFragment;
    private MainJotFragment mMainJotFragment;
    private MainAccountFragment mMainAccountFragment;

    private DiaryFragment mDiaryFragment;
    private DiaryEditFragment mDiaryEditFragment;
    private JotEditFragment mJotEditFragment;
    private AccountEditFragment mAccountEditFragment;

    private DrawEditFragment mDrawEditFragment;
    private DrawEditPresenter mDrawEditPresenter;

    private SettingFragment mSettingFragment;

    private MainPresenter mMainPresenter;

    private DiaryPresenter mDiaryPresenter;
    private DiaryEditPresenter mDiaryEditPresenter;
    private JotEditPresenter mJotEditPresenter;
    private AccountEditPresenter mAccountEditPresenter;

    private SettingPresenter mSettingPresenter;

    private FragmentManager mFragmentManager;

    private ArrayList<Note> mNoteList;
    private Note mNote;

    public MainActPresenter(MainActContract.View mainActView, FragmentManager fragmentManager, MainFragment mainFragment, MainDiaryFragment mainDiaryFragment, MainJotFragment mainJotFragment, MainAccountFragment mainAccountFragment) {

        mMainActView = checkNotNull(mainActView);
        mMainActView.setPresenter(this);
        mFragmentManager = fragmentManager;
        mMainFragment = mainFragment;
        mMainDiaryFragment = mainDiaryFragment;
        mMainJotFragment = mainJotFragment;
        mMainAccountFragment = mainAccountFragment;

    }

    @Override
    public void start() {

        init();
        showMainFragment();
    }

    @Override
    public void init() {

        if (mSettingFragment == null) {
            mSettingFragment = new SettingFragment();
        }
        if (mSettingPresenter == null) {
            mSettingPresenter = new SettingPresenter(mSettingFragment);
        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.whole_container, mSettingFragment, "SETTING")
                .hide(mSettingFragment)
                .commit();
    }

    @Override
    public void showMainFragment() {
        mMainActView.showMainUI();
        if (mMainPresenter == null) {
            mMainPresenter = new MainPresenter(mMainFragment, mMainDiaryFragment, mMainAccountFragment, mMainJotFragment, mFragmentManager, this);
        }
    }

    @Override
    public void backToMain() {
        
        FragmentTransaction transaction =  mFragmentManager.beginTransaction();
        transaction.show(mMainFragment)
                .commit();

        mMainActView.showToggleButton();
        mMainActView.showAddNoteButton();
        mMainActView.showToolBar();
        mMainActView.showBottomNaviagtion();


    }

    //////

    @Override
    public void goDiaryDetail() {

        mMainActView.hideToggleButton();
        mMainActView.hideBottomNavigationBar();
        mMainActView.hideToolBar();

    }

    @Override
    public void goMain() {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.hide(mSettingFragment)
                .commit();

        mMainActView.showMainUI();
        mMainActView.showMainPage();
        mMainActView.refreshMainPage("");
        mMainActView.showToggleButton();
        mMainActView.showAddNoteButton();
        mMainActView.showToolBar();
        mMainActView.showBottomNaviagtion();

    }

    @Override
    public void gosetting() {
        if (mSettingFragment == null) {
            mSettingFragment = new SettingFragment();
        }

        if (mSettingPresenter == null) {
            mSettingPresenter = new SettingPresenter(mSettingFragment);
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction
                .replace(R.id.whole_container, mSettingFragment)
                .show(mSettingFragment)
                .commit();

        hideComponent();
        mMainActView.showBottomNaviagtion();

    }


    @Override
    public void completeEdit() {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        mMainFragment = new MainFragment();
        transaction.replace(R.id.main_activity_container, mMainFragment)
                .detach(mDiaryEditFragment)
                .commit();

        mMainPresenter.refreshList();

    }

    @Override
    public void cancelEdit() {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.show(mMainFragment)
                .detach(mDiaryEditFragment)
                .commit();
    }

    @Override
    public void hideFragment(Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.hide(fragment)
                .commit();
    }

    ////////

    @Override
    public void refreshMainFragment() {

        mMainActView.showAddNoteButton();
        mMainActView.showToolBar();
        mMainActView.showBottomNaviagtion();
        mMainActView.showToggleButton();

        mMainActView.showMainUI();
        mMainActView.showMainPage();
        mMainActView.refreshMainPage("");
    }

    @Override
    public void showBottomSheet(boolean isListing) {
        BottomSheetDialogTemplateFragment bottomSheetDialogFragment = new BottomSheetDialogTemplateFragment(this, isListing);
        bottomSheetDialogFragment.show(mFragmentManager, bottomSheetDialogFragment.getTag());
    }

    @Override
    public void hideBottomNavigation() {
        mMainActView.hideBottomNavigationBar();
    }

    @Override
    public void hideComponent() {
        mMainActView.hideToolBar();
        mMainActView.hideToggleButton();
        mMainActView.hideAddNoteButton();
        mMainActView.hideMainPage();
    }

    @Override
    public void goDiaryEdit(boolean islisting) {

        mDiaryEditFragment = new DiaryEditFragment(true, null, islisting);
        mDiaryEditPresenter = new DiaryEditPresenter(mDiaryEditFragment, mFragmentManager, this, null, false);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.whole_container, mDiaryEditFragment, "EDIT DIARY")
                .show(mDiaryEditFragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void goAccountEdit(boolean islisting) {

        mAccountEditFragment = new AccountEditFragment(true, null, islisting);
        mAccountEditPresenter = new AccountEditPresenter(mAccountEditFragment, mFragmentManager, this, true);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.whole_container, mAccountEditFragment, "EDIT ACCOUNT")
                .show(mAccountEditFragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void goJotEdit(String imagePath, Uri uri, boolean islisting) {

        mJotEditFragment = new JotEditFragment(true, null, imagePath, uri, islisting);
        mJotEditPresenter = new JotEditPresenter(mJotEditFragment, mFragmentManager, this, null, true);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.whole_container, mJotEditFragment, "EDIT JOT")
                .show(mJotEditFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void refreshList() {
        mMainPresenter.refreshList();
    }

    @Override
    public void showJotBottomSheet(boolean islisting) {
        BottomSheetDialogJotFragment bottomSheetDialogJotFragment = new BottomSheetDialogJotFragment(this, islisting);
        bottomSheetDialogJotFragment.show(mFragmentManager, bottomSheetDialogJotFragment.getTag());
    }

    @Override
    public void goDraw() {
        mDrawEditFragment = new DrawEditFragment(true);
        mDrawEditPresenter = new DrawEditPresenter(mDrawEditFragment, this);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.whole_container, mDrawEditFragment)
                .show(mDrawEditFragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void switchToGridLayout() {
        mMainPresenter.switchToGridLayout();
    }

    @Override
    public void switchToListLayout() {
        mMainPresenter.switchToListLayout();
    }
}
