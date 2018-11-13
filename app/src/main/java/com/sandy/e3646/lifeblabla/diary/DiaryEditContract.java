package com.sandy.e3646.lifeblabla.diary;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.sandy.e3646.BasePresenter;
import com.sandy.e3646.BaseView;
import com.sandy.e3646.lifeblabla.object.Note;

import java.util.ArrayList;

public interface DiaryEditContract {

    interface View extends BaseView<Presenter> {

        void setPresenter(Presenter presenter);

        void takeDiaryData();

        void hideUI();

        void setMindSelection(String num);

        void setWeatherSelect(String num);

        void setNote(Note note);

        void getPhotoFromGallery();

        void getPhotoFromCamera();

        void getTagEditFocus();

        void getTagEditUnFocus();


    }

    interface Presenter extends BasePresenter {

        void cancelEditing(Fragment fragment);

        void saveDiaryData(ArrayList<Note> noteList, Note note);

        void updateDiaryData(String id, Note note);

        void selectMind();

        void selectWeather();

        void completeCreating();

        void completeEditing(Note note);

        void setMindSelection(String num);

        void setWeatherSelection(String num);

        void setContext(Context context);

        void setNoteList(Note note);

        void showLayout(boolean isListing);


    }
}
