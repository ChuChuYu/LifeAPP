package com.example.e3646.lifeblabla.mainactivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.account.AccountFragment;
import com.example.e3646.lifeblabla.account.AccountPresenter;
import com.example.e3646.lifeblabla.addnote.AddNoteContract;
import com.example.e3646.lifeblabla.addnote.AddNoteFragment;
import com.example.e3646.lifeblabla.addnote.AddNotePresenter;
import com.example.e3646.lifeblabla.calendar.CalendarFragment;
import com.example.e3646.lifeblabla.calendar.CalendarPresenter;
import com.example.e3646.lifeblabla.conference.ConferenceFragment;
import com.example.e3646.lifeblabla.conference.ConferencePresenter;
import com.example.e3646.lifeblabla.diary.DiaryEditFragment;
import com.example.e3646.lifeblabla.diary.DiaryEditPresenter;
import com.example.e3646.lifeblabla.diary.DiaryFragment;
import com.example.e3646.lifeblabla.diary.DiaryPresenter;
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

    private MainFragment mMainFragment;
    private DiaryFragment mDiaryFragment;
    private ConferenceFragment mConferenceFragment;
    private JotFragment mJotFragment;
    private TodolistFragment mTodolistFragment;
    private AccountFragment mAccountFragment;
    private DiaryEditFragment mDiaryEditFragment;
    private AddNoteFragment mAddNoteFragment;

    private CalendarFragment mCalendarFragment;
    private MapFragment mMapFragment;
    private SettingFragment mSettingFragment;

    private MainPresenter mMainPresenter;
    private DiaryPresenter mDiaryPresenter;
    private ConferencePresenter mConferencePresenter;
    private JotPresenter mJotPresenter;
    private TodolistPresenter mTodolistPresenter;
    private AccountPresenter mAccountPresenter;
    private DiaryEditPresenter mDiaryEditPresenter;
    private AddNotePresenter mAddNotePresenter;

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
        setViewandPresenter();


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
    public void setViewandPresenter() {

//        mDiaryFragment = new DiaryFragment();
//        if (mDiaryPresenter == null) {
//            mDiaryPresenter = new DiaryPresenter(mDiaryFragment, mFragmentManager, this);
//        }

        mConferenceFragment = new ConferenceFragment();
        if (mConferencePresenter == null) {
            mConferencePresenter = new ConferencePresenter();
        }

//        mJotFragment = new JotFragment();
//        mAccountFragment = new AccountFragment();
//        mTodolistFragment = new TodolistFragment();
//        mDiaryEditFragment = new DiaryEditFragment();

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

//        FragmentTransaction transaction = mFragmentManager.beginTransaction();
//        transaction.add(R.id.whole_container, mDiaryFragment, "DIARY")
//                .hide(mMainFragment)
//                .show(mDiaryFragment)
//                .addToBackStack(null)
//                .commit();

        mMainActView.hideToggleButton();
        mMainActView.hideBottomNavigationBar();
        mMainActView.hideToolBar();

    }

    @Override
    public void goConferenceDetail() {


    }

    @Override
    public void goJotDetail() {

    }

    @Override
    public void goAccountDetail() {

    }

    @Override
    public void goTodolistDetail() {

    }

    @Override
    public void goEditDiary() {

//        mDiaryEditPresenter = new DiaryEditPresenter(mDiaryEditFragment, mFragmentManager, this, mDiaryEditFragment);
//        FragmentTransaction transaction = mFragmentManager.beginTransaction();
//        transaction.add(R.id.whole_container, mDiaryEditFragment, "CREATE DIARY")
//                .hide(mMainFragment)
//                .show(mDiaryEditFragment)
//                .commit();
//
//        mMainActView.hideToggleButton();

    }

    @Override
    public void goAddNotePost() {

        if (mAddNoteFragment == null) {
            mAddNoteFragment = new AddNoteFragment();
        }

        if (mAddNotePresenter == null) {
            mAddNotePresenter = new AddNotePresenter(mAddNoteFragment, this, mFragmentManager);
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.whole_container, mAddNoteFragment, "ADD NOTE")
                .hide(mMainFragment)
                .show(mAddNoteFragment)
                .commit();

        mMainActView.hideToolBar();
        mMainActView.hideToggleButton();
        mMainActView.hideBottomNavigationBar();
        mMainActView.hideAddNoteButton();
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

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction
                .hide(mMainFragment)
                .hide(mCalendarFragment)
                .hide(mSettingFragment)
                .show(mMapFragment)
                .commit();

        mMainActView.hideToolBar();
        mMainActView.hideToggleButton();
        mMainActView.hideBottomNavigationBar();
        mMainActView.showBottomNaviagtion();
    }

    @Override
    public void goCalendar() {


        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction
                .hide(mMainFragment)
                .show(mCalendarFragment)
                .hide(mSettingFragment)
                .hide(mMapFragment)
                .commit();

        mMainActView.hideToolBar();
        mMainActView.hideToggleButton();
        mMainActView.hideBottomNavigationBar();
        mMainActView.showBottomNaviagtion();

    }

    @Override
    public void gosetting() {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction
                .hide(mMainFragment)
                .hide(mCalendarFragment)
                .show(mSettingFragment)
                .hide(mMapFragment)
                .commit();

        mMainActView.hideToolBar();
        mMainActView.hideToggleButton();
        mMainActView.hideBottomNavigationBar();
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
    public void switchToGridLayout() {
        mMainPresenter.switchToGridLayout();
    }

    @Override
    public void switchToListLayout() {
        mMainPresenter.switchToListLayout();
    }
}