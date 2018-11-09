package com.sandy.e3646.lifeblabla.draw;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.adapter.TagEditAdapter;
import com.sandy.e3646.lifeblabla.object.Note;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

@SuppressLint("ValidFragment")
public class DrawEditFragment extends Fragment implements DrawEditContract.View {

    private DrawEditContract.Presenter mPresenter;

    private ImageView mDrawImage;
    private Bitmap mBitmap;
    private Canvas canvas;
    private Paint paint;

    private Button mCancelButton;
    private Button mCompleteButton;

    private Note mNote;

    private boolean isCreating;

    private RecyclerView mTagRecyclerView;
    private TagEditAdapter mTagEditAdapter;
    private ConstraintLayout mBottombar;


    public DrawEditFragment(boolean iscreating) {
        isCreating = iscreating;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_draw, container, false);



        mDrawImage = view.findViewById(R.id.draw_image);
        showImage();

        mCancelButton = view.findViewById(R.id.button_cancel);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isCreating) {
                    mPresenter.cancelEditing();
                } else {
                    hideUI();
                }

            }
        });

        mCompleteButton = view.findViewById(R.id.button_complete);
        mCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isCreating) {
                    mPresenter.completeCreating();
                } else {
                    mPresenter.cancelEditing();
                }

            }
        });

        mTagRecyclerView = view.findViewById(R.id.jot_tag_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mTagRecyclerView.setLayoutManager(linearLayoutManager);
        mTagEditAdapter = new TagEditAdapter(null, null, null, this);
        mTagRecyclerView.setAdapter(mTagEditAdapter);

        mBottombar = view.findViewById(R.id.bottom_bar);

        return view;
    }

    private void showImage() {
        ViewGroup.LayoutParams imageParams = mDrawImage.getLayoutParams();
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);

        mBitmap = Bitmap.createBitmap(windowManager.getDefaultDisplay().getWidth(), imageParams.height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(mBitmap);
        canvas.drawColor(Color.WHITE);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        canvas.drawBitmap(mBitmap, new Matrix(), paint);
        mDrawImage.setImageBitmap(mBitmap);

        mDrawImage.setOnTouchListener(new View.OnTouchListener() {
            int startX;
            int startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        int endX = (int) event.getX();
                        int endY = (int) event.getY();

                        canvas.drawLine(startX, startY, endX, endY, paint);

                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        mDrawImage.setImageBitmap(mBitmap);
                        break;
                }
                return true;
            }
        });

    }



    @Override
    public void getTagEditFocus() {

        mBottombar.setFitsSystemWindows(true);

    }

    @Override
    public void getTagEditUnFocus() {

        mBottombar.setFitsSystemWindows(false);

    }


    @Override
    public void setPresenter(DrawEditContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);

    }

    @Override
    public void takeNoteData() {

        if (isCreating) {
            mNote = new Note();

//            if (mTitle.getText().toString() != null && !mTitle.getText().toString().equals("")) {
//                mNote.setmTitle(mTitle.getText().toString());
//            } else {
//                mNote.setmTitle("這是一則筆記");
//            }
            mNote.setmText("");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat formatterForMonth = new SimpleDateFormat("MM");
            SimpleDateFormat formatterForDay = new SimpleDateFormat("dd");
            SimpleDateFormat formatterForTime = new SimpleDateFormat("HH:mm");
            SimpleDateFormat formatterForWeek = new SimpleDateFormat("EEEE");
            SimpleDateFormat formateForID = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat formateForDaytime = new SimpleDateFormat("HH");
            Date curDate = new Date(System.currentTimeMillis());
            String currentTime = formatter.format(curDate);
            String month = formatterForMonth.format(curDate);
            String day = formatterForDay.format(curDate);
            String time = formatterForTime.format(curDate);
            String week = formatterForWeek.format(curDate);
            String id = formateForID.format(curDate);
            String daytime = formateForDaytime.format(curDate);

            if (Integer.parseInt(daytime) > 12) {
                mNote.setDayTime("下午");
            } else {
                mNote.setDayTime("上午");
            }

            mNote.setmCreatedTime(currentTime);

            mNote.setmId(id);

            mNote.setMonth(month);
            mNote.setDay(day);
            mNote.setTime(time);
            if (week.equals("Monday")) {
                mNote.setWeek("MON");
            } else if (week.equals("Tuesday")) {
                mNote.setWeek("TUE");
            } else if (week.equals("Wednesday")) {
                mNote.setWeek("WED");
            } else if (week.equals("Thursday")) {
                mNote.setWeek("THUR");
            } else if (week.equals("Friday")) {
                mNote.setWeek("FRI");
            } else if (week.equals("Saturday")) {
                mNote.setWeek("SAT");
            } else if (week.equals("Sunday")) {
                mNote.setWeek("SUN");
            }

            mNote.setmId(id);

            File file = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");
            OutputStream stream;
            try {
                stream = new FileOutputStream(file);
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


//            if (mImagePath != null) {
//                mNote.setmPicture(mImagePath);
//            }
//
//            if (mUri != null) {
//                mNote.setPhotoFromCamera(mUri.toString());
//            }

            mNote.setmPicture(file.getPath());

            Log.d("draw path", "save: " + file.getPath());

            mNote.setmUpdatedTime("");
            mNote.setmPlace("市政府");
            mNote.setClassification("jot");
            mNote.setmTag(mTagEditAdapter.TagList());

//            mNoteList = new ArrayList<Note>();
//            mNoteList.add(mNote);
            mPresenter.insertNoteData(getContext(), mNote);
        } else { //isEditing

//            mNote.setmTitle(mTitle.getText().toString());
//            mNote.setmText(mText.getText().toString());
//            if (mUri != null) {
//                mNote.setPhotoFromCamera(mUri.toString());
//            } else if (mImagePath != null) {
//                mNote.setmPicture(mImagePath);
//            }
//            mPresenter.updateJotData(getContext(), mNote);

        }

    }

    @Override
    public void hideUI() {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(DrawEditFragment.this)
                .commit();



    }
}
