package com.sandy.e3646.lifeblabla.guideactivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.mainactivity.MainActivity;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private Button mSkipButton;
    private GuideFirstFragment mGuideFirstFragment;
    private GuideSecondFragment mGuideSecondFragment;
    private GuideThirdFragment mGuideThirdFragment;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        mGuideFirstFragment = new GuideFirstFragment();
        mGuideSecondFragment = new GuideSecondFragment();
        mGuideThirdFragment = new GuideThirdFragment();

        mViewPager = (ViewPager)findViewById(R.id.guide_viewpager);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                switch (i) {
                    case 0:
                        return mGuideFirstFragment;
                    case 1:
                        return mGuideSecondFragment;
                    case 2:
                        return mGuideThirdFragment;
                    default:
                }

                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });

        mSkipButton = (Button)findViewById(R.id.button_skip);
        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

}
