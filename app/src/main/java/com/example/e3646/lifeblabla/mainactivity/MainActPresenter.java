package com.example.e3646.lifeblabla.mainactivity;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.account.AccountEditFragment;
import com.example.e3646.lifeblabla.account.AccountEditPresenter;
import com.example.e3646.lifeblabla.account.AccountFragment;
import com.example.e3646.lifeblabla.account.AccountPresenter;

import com.example.e3646.lifeblabla.calendar.CalendarFragment;
import com.example.e3646.lifeblabla.calendar.CalendarPresenter;
import com.example.e3646.lifeblabla.conference.ConferenceFragment;
import com.example.e3646.lifeblabla.conference.ConferencePresenter;
import com.example.e3646.lifeblabla.dialogfragment.BottomSheetDialogJotFragment;
import com.example.e3646.lifeblabla.dialogfragment.BottomSheetDialogTemplateFragment;
import com.example.e3646.lifeblabla.diary.DiaryEditFragment;
import com.example.e3646.lifeblabla.diary.DiaryEditPresenter;
import com.example.e3646.lifeblabla.diary.DiaryFragment;
import com.example.e3646.lifeblabla.diary.DiaryPresenter;
import com.example.e3646.lifeblabla.jot.JotEditFragment;
import com.example.e3646.lifeblabla.jot.JotEditPresenter;
import com.example.e3646.lifeblabla.jot.JotFragment;
import com.example.e3646.lifeblabla.jot.JotPresenter;
import com.example.e3646.lifeblabla.main.MainFragment;
import com.example.e3646.lifeblabla.main.MainPresenter;
import com.example.e3646.lifeblabla.map.MapFragment;
import com.example.e3646.lifeblabla.map.MapPresenter;
import com.example.e3646.lifeblabla.object.Note;
import com.example.e3646.lifeblabla.setting.SettingFragment;
import com.example.e3646.lifeblabla.setting.SettingPresenter;
import com.example.e3646.lifeblabla.todolist.TodolistFragment;
import com.example.e3646.lifeblabla.todolist.TodolistPresenter;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainActPresenter implements MainActContract.Presenter {

    private MainActContract.View mMainActView;
    private Context mContext;

    private MainFragment mMainFragment;
    private DiaryFragment mDiaryFragment;
    private DiaryEditFragment mDiaryEditFragment;
    private JotEditFragment mJotEditFragment;
    private AccountEditFragment mAccountEditFragment;

    private CalendarFragment mCalendarFragment;
    private MapFragment mMapFragment;
    private SettingFragment mSettingFragment;

    private MainPresenter mMainPresenter;

    private DiaryPresenter mDiaryPresenter;
    private DiaryEditPresenter mDiaryEditPresenter;
    private JotEditPresenter mJotEditPresenter;
    private AccountEditPresenter mAccountEditPresenter;


    private CalendarPresenter mCalendarPresenter;
    private MapPresenter mMapPresenter;
    private SettingPresenter mSettingPresenter;

    private FragmentManager mFragmentManager;

    private ArrayList<Note> mNoteList;
    private Note mNote;

    public MainActPresenter(MainActContract.View mainActView, FragmentManager fragmentManager, MainFragment mainFragment) {

        mMainActView = checkNotNull(mainActView);
        mMainActView.setPresenter(this);
        mFragmentManager = fragmentManager;
        mMainFragment = mainFragment;

    }

    @Override
    public void start() {

        init();
        showMainFragment();
    }

    @Override
    public void init() {
        if (mMapFragment == null) {
            mMapFragment = new MapFragment();
        }

        if (mMapPresenter == null) {
            mMapPresenter = new MapPresenter(mMapFragment);
        }

        if (mCalendarFragment == null) {
            mCalendarFragment = new CalendarFragment();
        }

        if (mCalendarPresenter == null) {
            mCalendarPresenter = new CalendarPresenter(mCalendarFragment);
        }

        if (mSettingFragment == null) {
            mSettingFragment = new SettingFragment();
        }

        if (mSettingPresenter == null) {
            mSettingPresenter = new SettingPresenter(mSettingFragment);
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.whole_container, mCalendarFragment, "CALENDAR")
                .add(R.id.whole_container, mMapFragment, "MAP")
                .add(R.id.whole_container, mSettingFragment, "SETTING")
                .hide(mCalendarFragment)
                .hide(mMapFragment)
                .hide(mSettingFragment)
                .commit();
    }

    @Override
    public void showMainFragment() {
        mMainActView.showMainUI();
        if (mMainPresenter == null) {
            mMainPresenter = new MainPresenter(mMainFragment, mFragmentManager, this);
        }
    }

    @Override
    public void backToMain() {

        mMainActView.showToggleButton();
        mMainActView.showAddNoteButton();
        mMainActView.showToolBar();
        mMainActView.showBottomNaviagtion();

        FragmentTransaction transaction =  mFragmentManager.beginTransaction();
        transaction.show(mMainFragment)
                .commit();

    }

    @Override
    public void goDiaryDetail() {

        mMainActView.hideToggleButton();
        mMainActView.hideBottomNavigationBar();
        mMainActView.hideToolBar();

    }

    @Override
    public void goMain() {



        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction
                .show(mMainFragment)
                .hide(mCalendarFragment)
                .hide(mSettingFragment)
                .hide(mMapFragment)
                .addToBackStack(null)
                .commit();

        mMainActView.showToolBar();
        mMainActView.showToggleButton();
        mMainActView.showAddNoteButton();
        mMainActView.showBottomNaviagtion();

    }

    @Override
    public void goMap() {

        if (mMapFragment == null) {
            mMapFragment = new MapFragment();
        }

        if (mMapPresenter == null) {
            mMapPresenter = new MapPresenter(mMapFragment);
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction
                .replace(R.id.whole_container, mMapFragment)
                .show(mMapFragment)
                .commit();

        hideComponent();
        mMainActView.showBottomNaviagtion();
    }

    @Override
    public void goCalendar() {
        if (mCalendarFragment == null) {
            mCalendarFragment = new CalendarFragment();
        }

        if (mCalendarPresenter == null) {
            mCalendarPresenter = new CalendarPresenter(mCalendarFragment);
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction
                .replace(R.id.whole_container, mCalendarFragment)
                .show(mCalendarFragment)
                .commit();

        hideComponent();
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

        mMainFragment = new MainFragment(null, true);
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

    @Override
    public void refreshMainFragment() {

        mMainFragment = new MainFragment(mNoteList, true);
        mMainPresenter = new MainPresenter(mMainFragment, mFragmentManager, this);

        FragmentTransaction transaction =  mFragmentManager.beginTransaction();
        transaction.add(R.id.main_activity_container, mMainFragment)
                .show(mMainFragment)
                .commit();

        mMainActView.showToggleButton();
        mMainActView.showAddNoteButton();
        mMainActView.showToolBar();
        mMainActView.showBottomNaviagtion();
    }

    @Override
    public void showBottomSheet() {
        BottomSheetDialogTemplateFragment bottomSheetDialogFragment = new BottomSheetDialogTemplateFragment(this);

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
    }

    @Override
    public void goDiaryEdit() {

        mDiaryEditFragment = new DiaryEditFragment(true, null);
        mDiaryEditPresenter = new DiaryEditPresenter(mDiaryEditFragment, mFragmentManager, this, null, false);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.whole_container, mDiaryEditFragment, "EDIT DIARY")
                .show(mDiaryEditFragment)
                .hide(mMainFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goAccountEdit() {

        mAccountEditFragment = new AccountEditFragment(true, null);
        mAccountEditPresenter = new AccountEditPresenter(mAccountEditFragment, mFragmentManager, this, true);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.whole_container, mAccountEditFragment, "EDIT ACCOUNT")
                .show(mAccountEditFragment)
                .hide(mMainFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goJotEdit() {

        mJotEditFragment = new JotEditFragment(true, null);
        mJotEditPresenter = new JotEditPresenter(mJotEditFragment, mFragmentManager, this, null, true);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.whole_container, mJotEditFragment, "EDIT JOT")
                .show(mJotEditFragment)
                .hide(mMainFragment)
                .addToBackStack(null)
                .commit();


    }

    @Override
    public void refreshList() {
        mMainPresenter.refreshList();
    }

    @Override
    public void showJotBottomSheet() {
        BottomSheetDialogJotFragment bottomSheetDialogJotFragment = new BottomSheetDialogJotFragment(this);
        bottomSheetDialogJotFragment.show(mFragmentManager, bottomSheetDialogJotFragment.getTag());
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
