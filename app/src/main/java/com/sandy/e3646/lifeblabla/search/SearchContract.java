package com.sandy.e3646.lifeblabla.search;

import android.content.Context;

import com.sandy.e3646.BasePresenter;
import com.sandy.e3646.BaseView;
import com.sandy.e3646.lifeblabla.object.Note;

import java.util.ArrayList;

public class SearchContract {

    interface View extends BaseView<Presenter> {

        void setPresenter(Presenter presenter);

        void hideUI();



    }

    interface Presenter extends BasePresenter {

        ArrayList<Note> getSearchList(Context context, String tag);

    }
}
