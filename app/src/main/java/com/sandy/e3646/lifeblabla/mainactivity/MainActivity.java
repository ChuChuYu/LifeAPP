package com.sandy.e3646.lifeblabla.mainactivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.account.AccountFragment;

import com.sandy.e3646.lifeblabla.diary.DiaryFragment;
import com.sandy.e3646.lifeblabla.guideactivity.GuideActivity;
import com.sandy.e3646.lifeblabla.jot.JotFragment;
import com.sandy.e3646.lifeblabla.main.MainAccountFragment;
import com.sandy.e3646.lifeblabla.main.MainContract;
import com.sandy.e3646.lifeblabla.main.MainDiaryFragment;
import com.sandy.e3646.lifeblabla.main.MainFragment;
import com.sandy.e3646.lifeblabla.main.MainJotFragment;

import com.sandy.e3646.lifeblabla.object.Note;


import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainActivity extends AppCompatActivity implements MainActContract.View, ViewPager.OnPageChangeListener, TabLayout.BaseOnTabSelectedListener {

    private Context mContext;

    private MainActContract.Presenter mPresenter;
    private FragmentManager mFragmentManager;

    private MainFragment mMainFragment;
    private MainDiaryFragment mMainDiaryFragment;
    private MainJotFragment mMainJotFragment;
    private MainAccountFragment mMainAccountFragment;

    private DiaryFragment mDiaryFragment;
    private JotFragment mJotFragment;
    private AccountFragment mAccountFragment;

    private MainContract.Presenter mMainPresenter;

    private ToggleButton mToggleButton;
    private ImageButton mAddNotesButton;
    private BottomNavigationView mBottomNav;
    private android.support.v7.widget.Toolbar mToolbar;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private ArrayList<Note> mNoteList;
    private NavigationView mBottomNavigation;
    private static final int MY_PERMISSIONS_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        View view = getWindow().getDecorView();
        int i = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        view.setSystemUiVisibility(i);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        SharedPreferences sharedPreferences = this.getSharedPreferences("isFirst", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean isFirst = sharedPreferences.getBoolean("isFirst", true);

        if (isFirst) {
            editor.putBoolean("isFirst", false);
            editor.commit();
            Intent intent = new Intent(MainActivity.this, GuideActivity.class);
            startActivity(intent);
            finish();
        }

        init();

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
                mPresenter.showJotBottomSheet();

            }
        });

        mBottomNav = (BottomNavigationView)findViewById(R.id.main_bottom_navigation);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.main_main:
                        mPresenter.goMain();
                        break;
                    case R.id.main_post:
                        mPresenter.showBottomSheet();
                        break;
                    case R.id.main_setting:
                        mPresenter.gosetting();
                        break;
                    default:
                }
               return true;
            }
        });

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) mBottomNav.getChildAt(0);
        for (int a = 0; a < menuView.getChildCount(); a++) {
            final View iconView = menuView.getChildAt(a).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, displayMetrics);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }

        mToolbar = findViewById(R.id.toolbar);
        runTimePermission();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }

        }
    }

    public void init() {

        mMainFragment = new MainFragment(mNoteList);
        mMainDiaryFragment = new MainDiaryFragment(mFragmentManager);
        mMainJotFragment = new MainJotFragment();
        mMainAccountFragment = new MainAccountFragment();
        mFragmentManager = getSupportFragmentManager();
        mPresenter = new MainActPresenter(this, mFragmentManager, mMainFragment, mMainDiaryFragment, mMainJotFragment, mMainAccountFragment);
        mPresenter.start();

    }

    @Override
    public void setPresenter(MainActContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void runTimePermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST);

            }
        } else {

        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST);

            }
        } else {

        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST);

            }
        } else {

        }
    }

    @Override
    public void showMainUI() {

        mTabLayout = findViewById(R.id.tab_layout);
        mTabLayout.addOnTabSelectedListener(this);

        mViewPager = findViewById(R.id.viewpager);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                switch (i) {
                    case 0:
                        return mMainFragment;
                    case 1:
                        return mMainDiaryFragment;
                    case 2:
                        return mMainJotFragment;
                    case 3:
                        return mMainAccountFragment;
                }

                return null;
            }

            @Override
            public int getCount() {
                return 4;
            }
        });

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
    public void hideAddNoteButton() {
        mAddNotesButton.setVisibility(View.INVISIBLE);
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
    public void showToolBar() {
        mToolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAddNoteButton() {

        mAddNotesButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void goDiaryDetail() {

        mPresenter.goDiaryDetail();
    }

    @Override
    public void refreshMainPage(String id) {
        mMainFragment.refreshList();
    }

    @Override
    public void hideMainPage() {
        mViewPager.setVisibility(View.INVISIBLE);
        mTabLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMainPage() {
        mViewPager.setVisibility(View.VISIBLE);
        mTabLayout.setVisibility(View.VISIBLE);
    }

    /////////////

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        mTabLayout.getTabAt(i).select();

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        mViewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}
