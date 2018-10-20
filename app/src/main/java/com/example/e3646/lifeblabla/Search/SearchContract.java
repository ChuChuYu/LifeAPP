package com.example.e3646.lifeblabla.Search;

import com.example.e3646.BasePresenter;
import com.example.e3646.BaseView;

public class SearchContract {

    interface View extends BaseView<Presenter> {

        void setPresenter(Presenter presenter);

        void hideUI();



    }

    interface Presenter extends BasePresenter {


    }
}
