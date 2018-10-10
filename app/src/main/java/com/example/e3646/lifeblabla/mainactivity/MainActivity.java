package com.example.e3646.lifeblabla.mainactivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.account.AccountFragment;
import com.example.e3646.lifeblabla.conference.ConferenceFragment;
import com.example.e3646.lifeblabla.dialogfragment.BottomSheetDialogTemplateFragment;
import com.example.e3646.lifeblabla.diary.DiaryFragment;
import com.example.e3646.lifeblabla.jot.JotFragment;
import com.example.e3646.lifeblabla.main.MainAdapter;
import com.example.e3646.lifeblabla.main.MainContract;
import com.example.e3646.lifeblabla.main.MainFragment;
import com.example.e3646.lifeblabla.main.MainPresenter;
import com.example.e3646.lifeblabla.map.MapFragment;
import com.example.e3646.lifeblabla.map.MapPresenter;
import com.example.e3646.lifeblabla.object.Note;
import com.example.e3646.lifeblabla.todolist.TodolistFragment;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainActivity extends AppCompatActivity implements MainActContract.View {

    private Context mContext;

    private MainActContract.Presenter mPresenter;
    private FragmentManager mFragmentManager;

    private MainFragment mMainFragment;
    private DiaryFragment mDiaryFragment;
    private ConferenceFragment mConferenceFragment;
    private JotFragment mJotFragment;
    private TodolistFragment mTodolistFragment;
    private AccountFragment mAccountFragment;
    private MapFragment mMapFragment;
    private MapPresenter mMapPresenter;

    private MainContract.Presenter mMainPresenter;

    private ToggleButton mToggleButton;
    private ImageButton mAddNotesButton;
    private BottomNavigationView mBottomNav;
    private android.support.v7.widget.Toolbar mToolbar;

    private ArrayList<Note> mNoteList;

    private boolean isListLayout;


    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = getWindow().getDecorView();
        int i = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        view.setSystemUiVisibility(i);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        init();

        mToggleButton = (ToggleButton)findViewById(R.id.button_switch_layout);
        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    mToggleButton.setButtonDrawable(R.drawable.button_layout_list);
                    mPresenter.switchToGridLayout();
                    mMainFragment.setIsListMode(b);


                } else {

                    mToggleButton.setButtonDrawable(R.drawable.button_grid_layout);
                    mPresenter.switchToListLayout();
                    mMainFragment.setIsListMode(b);


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

                        mPresenter.goMain();

                        break;
                    case R.id.main_map:
                        mPresenter.goMap();

                        break;

                    case R.id.main_post:


//                        mPresenter.goAddNotePost();
//
//                        hideBottomNavigationBar();
//                        hideToggleButton();



                        mPresenter.showBottomSheet();

                        break;
                    case R.id.main_calendar:

                        mPresenter.goCalendar();
                        break;

                    case R.id.main_setting:
                        mPresenter.gosetting();
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

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.CAMERA)) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("我真的沒有要做壞事, 給我權限吧?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.CAMERA},
                                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .show();
            } else {

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
    }




    public void init() {
        mMainFragment = new MainFragment(mNoteList, true);
        mFragmentManager = getSupportFragmentManager();
        mPresenter = new MainActPresenter(this, mFragmentManager, mMainFragment);

        mPresenter.start();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public int checkUriPermission(Uri uri, String readPermission, String writePermission, int pid, int uid, int modeFlags) {
        return super.checkUriPermission(uri, readPermission, writePermission, pid, uid, modeFlags);
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


}
