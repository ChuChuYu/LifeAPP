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

        void showBottomNaviagtion();

        void showToggleButton();

        void goDiaryDetail();

        void goConferenceDetail();

        void goJotDetail();

        void goAccountDetail();

        void goTodolistDetail();

        void goEditDiary();



    }

    interface Presenter extends BasePresenter {

        void showMainFragment();

        void switchToGridLayout();

        void switchToListLayout();

        void setViewandPresenter();

        void showBottomNavigation();

        void showToggleButton();

        void goDiaryDetail();

        void goConferenceDetail();

        void goJotDetail();

        void goAccountDetail();

        void goTodolistDetail();

        void goEditDiary();

        void goAddNotePost();

        void completeEdit(ArrayList<Note> noteList);

        void cancelEdit();


    }
}
