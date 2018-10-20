package com.example.e3646.lifeblabla.guideactivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.mainactivity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private List<ImageView> mImageList = new ArrayList<ImageView>();
    private Button mSkipButton;
    private int[] mPics = new int[] {R.drawable.introduce_1, R.drawable.introduce_2, R.drawable.introduce_3};
    private int mNum;

    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        setImage();
        mViewPager = (ViewPager)findViewById(R.id.guide_viewpager);
        FirstTimeExpAdapter firstTimeExpAdapter = new FirstTimeExpAdapter();
        mViewPager.setAdapter(firstTimeExpAdapter);


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

    public void setImage() {
        ImageView imageView;
        View view;
        for (int pic: mPics) {
            imageView = new ImageView(GuideActivity.this);
            imageView.setBackgroundResource(pic);
            this.mImageList.add(imageView);
            Log.d("image",  mImageList.get(0).toString());
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mLinearLayout.getChildAt(mNum).setEnabled(false);
        mLinearLayout.getChildAt(position).setEnabled(true);
        mNum = position;

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onResume() {
        super.onResume();
//        mHandler.sendEmptyMessageDelayed(1,4000);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mHandler.removeMessages(1);
    }

//    private Handler mHandler = new Handler() {
//        public void handleMessage(android.os.Message msg) {
//            switch(msg.what) {
//                case 1:
//                    int totalcount = mImageList.size();//autoChangeViewPager.getChildCount();
//                    int currentItem = mViewPager.getCurrentItem();
//
//                    int toItem = currentItem + 1 == totalcount ? 0 : currentItem + 1;
//
//                    Log.d("aaa", "totalcount: " + totalcount + "   currentItem: " + currentItem + "   toItem: " + toItem);
//
//                    mViewPager.setCurrentItem(toItem, true);
//
//                    this.sendEmptyMessageDelayed(1, 4000);
//            }
//        }
//    };

    public class FirstTimeExpAdapter extends PagerAdapter {



        @Override
        public int getCount() {
            return mImageList.size();

        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            ImageView imageView = mImageList.get(position);
            container.addView(imageView);

            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mImageList.get(position));
        }
    }
}
