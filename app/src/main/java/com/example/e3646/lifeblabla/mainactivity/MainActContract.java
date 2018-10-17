package com.example.e3646.lifeblabla.mainactivity;

import android.support.v4.app.Fragment;

import com.example.e3646.BasePresenter;
import com.example.e3646.BaseView;
import com.example.e3646.lifeblabla.object.Note;

import java.util.ArrayList;

public interface MainActContract {

    interface View extends BaseView<Presenter> {


        void setPresenter(Presenter presenter);

        void showMainUI();

        void hideToggleButton();

        void hideBottomNavigationBar();

        void hideToolBar();

        void hideAddNoteButton();

        void showBottomNaviagtion();

        void showToggleButton();

        void showToolBar();

        void showAddNoteButton();

        void goDiaryDetail();


    }

    interface Presenter extends BasePresenter {

        void init();

        void showMainFragment();

        void switchToGridLayout();

        void switchToListLayout();

        void backToMain();

        void goDiaryDetail();

        void goMain();

        void goMap();

        void goCalendar();

        void gosetting();

        void completeEdit();

        void cancelEdit();

        void hideFragment(Fragment fragment);

        void refreshMainFragment();

        void showBottomSheet();

        void hideBottomNavigation();

        void hideComponent();

        void goDiaryEdit();

        void goAccountEdit();

        void goJotEdit();

        void refreshList();

        void showJotBottomSheet();


    }
}
