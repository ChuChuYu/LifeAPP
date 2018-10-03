package com.example.e3646.lifeblabla.jot;

import android.widget.ImageView;

import com.example.e3646.BasePresenter;
import com.example.e3646.BaseView;
import com.example.e3646.lifeblabla.diary.DiaryEditContract;
import com.example.e3646.lifeblabla.object.Note;

import java.util.ArrayList;

public interface JotContract {

    interface View extends BaseView<Presenter> {

        void setPresenter(Presenter presenter);



    }

    interface Presenter extends BasePresenter {



    }
}
