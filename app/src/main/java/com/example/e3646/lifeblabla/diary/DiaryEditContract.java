package com.example.e3646.lifeblabla.diary;

import android.widget.ImageView;

import com.example.e3646.BasePresenter;
import com.example.e3646.BaseView;
import com.example.e3646.lifeblabla.object.Note;

import java.util.ArrayList;

public interface DiaryEditContract {

    interface View extends BaseView<Presenter> {

        void setPresenter(Presenter presenter);

        void showMindSelection(ImageView imageView);

        void takeDiaryData();

    }

    interface Presenter extends BasePresenter {

        void completeEditDiary();

        void cancelEditDiary();

        void saveDiaryData(ArrayList<Note> noteList);

    }
}
