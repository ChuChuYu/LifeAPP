package com.example.e3646.lifeblabla.diary;

import com.example.e3646.BasePresenter;
import com.example.e3646.BaseView;

public interface DiaryContract {

    interface View extends BaseView<Presenter> {

        void setPresenter(Presenter presenter);
    }

    interface Presenter extends BasePresenter {

        void goEditDiary();

        void setDiaryData();

    }
}
