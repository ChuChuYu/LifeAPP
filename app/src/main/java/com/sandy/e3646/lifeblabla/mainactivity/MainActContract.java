package com.sandy.e3646.lifeblabla.mainactivity;

import android.net.Uri;
import android.support.v4.app.Fragment;

import com.sandy.e3646.BasePresenter;
import com.sandy.e3646.BaseView;

public interface MainActContract {

    interface View extends BaseView<Presenter> {


        void setPresenter(Presenter presenter);

        void runTimePermission();

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

        void refreshMainPage(String id);

        void hideMainPage();

        void showMainPage();


    }

    interface Presenter extends BasePresenter {

        void init();

        void showMainFragment();

        void switchToGridLayout();

        void switchToListLayout();

        void backToMain();

        void goDiaryDetail();

        void goMain();

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

        void goJotEdit(String imagePath, Uri uri);

        void refreshList();

        void showJotBottomSheet();

        void goDraw();


    }
}
