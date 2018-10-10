package com.example.e3646.lifeblabla.diary;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.e3646.Sqldatabase;
import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.addnote.AddNotePresenter;
import com.example.e3646.lifeblabla.dialogfragment.MindSelectFragment;
import com.example.e3646.lifeblabla.dialogfragment.WeatherSelectFragment;
import com.example.e3646.lifeblabla.main.MainFragment;
import com.example.e3646.lifeblabla.mainactivity.MainActPresenter;
import com.example.e3646.lifeblabla.object.Note;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class DiaryEditPresenter implements DiaryEditContract.Presenter {

    private Context mContext;

    private DiaryEditContract.View mDiaryEditView;
    private FragmentManager mFragmentManager;
    private MainFragment mMainFragment;
    private DiaryEditFragment mDiaryEditFragment;
    private MainActPresenter mMainActPresenter;
    private DiaryPresenter mDiaryPresenter;
    private AddNotePresenter mAddNotePresenter;

    private MindSelectFragment mMindSelectFragment;
    private WeatherSelectFragment mWeatherSelectFragment;

    private ArrayList<Note> mNoteList;
    private Note mNote;

    private boolean isCreating = false;

    public DiaryEditPresenter(DiaryEditContract.View diaryEditView, FragmentManager fragmentManager,
                              MainActPresenter mainActPresenter, DiaryPresenter diaryPresenter,
                              AddNotePresenter addNotePresenter, boolean iscreating) {

        mDiaryEditView = checkNotNull(diaryEditView);
        mDiaryEditView.setPresenter(this);
        mFragmentManager = fragmentManager;
        mMainActPresenter = mainActPresenter;
//        mDiaryEditFragment = diaryEditFragment;
        mDiaryPresenter = diaryPresenter;
        isCreating = iscreating;
        mAddNotePresenter = addNotePresenter;
    }


    @Override
    public void start() {

    }

    @Override
    public void completeEditDiary() {

//        mDiaryEditView.takeDiaryData();
        mDiaryEditView.hideUI();
    }

    @Override
    public void cancelEditDiary(Fragment fragment) {
        mMainActPresenter.hideFragment(fragment);
    }

    @Override
    public void saveDiaryData(ArrayList<Note> noteList, Note note) {

        mNoteList = noteList;
        mNote = note;
        Sqldatabase sql = new Sqldatabase(mContext);
        sql.insert(mNote);
    }

    @Override
    public void updateDiaryData(String id, Note note) {
        Sqldatabase sql = new Sqldatabase(mContext);
        sql.updateNotes(id, note);
    }

    @Override
    public void selectMind() {
        mMindSelectFragment = new MindSelectFragment(this);
        mMindSelectFragment.show(mFragmentManager, null);
    }

    @Override
    public void selectWeather() {
        mWeatherSelectFragment = new WeatherSelectFragment(this);
        mWeatherSelectFragment.show(mFragmentManager, null);

    }

    @Override
    public void completeCreating() {
        mDiaryEditView.takeDiaryData();
        mDiaryEditView.hideUI();
        mMainActPresenter.refreshMainFragment();
    }

    @Override
    public void completeEditing() {

    }

    @Override
    public void setMindSelection(String num) {

        mDiaryEditView.setMindSelection(num);
    }

    @Override
    public void setWeatherSelection(String num) {
        mDiaryEditView.setWeatherSelect(num);
    }

    @Override
    public void setContext(Context context) {

        this.mContext = context;
    }

    @Override
    public void setNoteList(Note note) {
        this.mNote = note;
        mDiaryEditView.setNote(mNote);
    }
}
