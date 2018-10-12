package com.example.e3646.lifeblabla.diary;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.example.e3646.BasePresenter;
import com.example.e3646.BaseView;
import com.example.e3646.lifeblabla.object.Note;

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


    }

    interface Presenter extends BasePresenter {

        void cancelEditDiary(Fragment fragment);

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


    }
}
