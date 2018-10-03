package com.example.e3646.lifeblabla.mainactivity;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.account.AccountFragment;
import com.example.e3646.lifeblabla.conference.ConferenceFragment;
import com.example.e3646.lifeblabla.diary.DiaryFragment;
import com.example.e3646.lifeblabla.jot.JotFragment;
import com.example.e3646.lifeblabla.main.MainAdapter;
import com.example.e3646.lifeblabla.main.MainContract;
import com.example.e3646.lifeblabla.main.MainFragment;
import com.example.e3646.lifeblabla.main.MainPresenter;
import com.example.e3646.lifeblabla.object.Note;
import com.example.e3646.lifeblabla.todolist.TodolistFragment;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainActivity extends AppCompatActivity implements MainActContract.View {

    private MainActContract.Presenter mPresenter;
    private FragmentManager mFragmentManager;

    private MainFragment mMainFragment;
    private DiaryFragment mDiaryFragment;
    private ConferenceFragment mConferenceFragment;
    private JotFragment mJotFragment;
    private TodolistFragment mTodolistFragment;
    private AccountFragment mAccountFragment;

    private MainContract.Presenter mMainPresenter;

    private ToggleButton mToggleButton;
    private ImageButton mCreateDiaryButton;
    private ImageButton mAddNotesButton;
    private BottomNavigationView mBottomNav;
    private android.support.v7.widget.Toolbar mToolbar;
    private ConstraintLayout mLayout;

    private ArrayList<Note> mNoteList;

    private boolean isListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();

        mLayout = (ConstraintLayout)findViewById(R.id.whole_container);

        mToggleButton = (ToggleButton)findViewById(R.id.button_switch_layout);
        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    mToggleButton.setButtonDrawable(R.drawable.button_layout_list);
                    mPresenter.switchToGridLayout();


                } else {

                    mToggleButton.setButtonDrawable(R.drawable.button_grid_layout);
                    mPresenter.switchToListLayout();


                }
            }
        });

        mAddNotesButton = (ImageButton)findViewById(R.id.button_add_notes);
        mAddNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.goAddNotePost();
                hideBottomNavigationBar();
                hideToggleButton();
            }
        });

        //加上listener後點選bottom nav bar，icon不會變色
        mBottomNav = (BottomNavigationView)findViewById(R.id.main_bottom_navigation);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.main_main:

                        break;

                    case R.id.main_post:


                        mPresenter.goAddNotePost();

                        hideBottomNavigationBar();
                        hideToggleButton();


                        break;

                    default:
                }
               return false;
            }
        });

        mToolbar = findViewById(R.id.toolbar);

//        mCreateDiaryButton = (ImageButton)findViewById(R.id.create_diary_button);
//        mCreateDiaryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPresenter.goEditDiary();
//            }
//        });
    }

    public void init() {

        mMainFragment = new MainFragment(mNoteList);
        mFragmentManager = getSupportFragmentManager();
        mPresenter = new MainActPresenter(this, mFragmentManager, mMainFragment);
        mPresenter.start();

    }

    @Override
    public void setPresenter(MainActContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showMainUI() {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.main_activity_container, mMainFragment)
                .show(mMainFragment)
                .commit();

    }

    @Override
    public void hideToggleButton() {
        mToggleButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideBottomNavigationBar() {
        mBottomNav.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideToolBar() {

        mToolbar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void showBottomNaviagtion() {
        mBottomNav.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToggleButton() {
        mToggleButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void goDiaryDetail() {

        mPresenter.goDiaryDetail();
    }

    @Override
    public void goConferenceDetail() {

        mPresenter.goConferenceDetail();

    }

    @Override
    public void goJotDetail() {

        mPresenter.goJotDetail();

    }

    @Override
    public void goAccountDetail() {

        mPresenter.goAccountDetail();

    }

    @Override
    public void goTodolistDetail() {

        mPresenter.goTodolistDetail();

    }

    @Override
    public void goEditDiary() {

        mPresenter.goEditDiary();

    }


}
