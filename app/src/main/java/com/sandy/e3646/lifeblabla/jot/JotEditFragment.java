package com.sandy.e3646.lifeblabla.jot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.adapter.TagEditAdapter;
import com.sandy.e3646.lifeblabla.object.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

@SuppressLint("ValidFragment")
public class JotEditFragment extends Fragment implements JotEditContrat.View, View.OnClickListener {

    private JotEditContrat.Presenter mPresenter;
    private Context mContext;
    private boolean isCreating;
    private boolean isExpanded = false;
    private Note mNote;
    private ArrayList<Note> mNoteList;

    private Button mCancelButton;
    private Button mCompleteButton;
    private TextView mCreatedTime;
    private EditText mTitle;
    private EditText mText;
    private RecyclerView mTagRecyclerView;
    private TagEditAdapter mTagEditAdapter;
    private EditText mJotTag;
    private ImageView mImage;
    private BottomSheetBehavior mBottomSheetBehavior;
    private ConstraintLayout mBottomBar;

    private String mImagePath;
    private Uri mUri;


    public JotEditFragment(boolean isCreating, Note note, String imagePath, Uri uri) {
        this.isCreating = isCreating;
        mNote = note;
        mImagePath = imagePath;
        mUri = uri;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jotedit, container, false);

        mTitle = (EditText)view.findViewById(R.id.jot_title);
        mText = (EditText)view.findViewById(R.id.jot_text);
        mCreatedTime = (TextView)view.findViewById(R.id.jot_createdtime);

        mTagRecyclerView = (RecyclerView)view.findViewById(R.id.jot_tag_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mTagRecyclerView.setLayoutManager(linearLayoutManager);
        mTagEditAdapter = new TagEditAdapter(null, this, null, null);
        mTagRecyclerView.setAdapter(mTagEditAdapter);

        mJotTag = (EditText)view.findViewById(R.id.sample_title);
        mJotTag.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                return false;
            }
        });

        if (!isCreating && mNote != null) {
//            mTitle.setText(mNote.getmTitle());
            mText.setText(mNote.getmText());
            mCreatedTime.setText(mNote.getmCreatedTime());

        } else {
            mCreatedTime.setText(getCurrentTime());
        }

        mCancelButton = (Button)view.findViewById(R.id.button_cancel);
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

        mCompleteButton = (Button)view.findViewById(R.id.button_complete);
        mCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCreating) {

                    mPresenter.completeCreating();
                } else {

                    mPresenter.completeEditing(mNote);


                }
            }
        });

        mBottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet_layout));
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mImage = view.findViewById(R.id.diary_image);

        if (isCreating) {
            if (mUri == null || mUri.equals("")) {
                if (mImagePath == null || mImagePath.equals("")) {
                    mImage.setVisibility(View.GONE);
                } else {
                    Bitmap bitmap = BitmapFactory.decodeFile(mImagePath);
                    mImage.setImageBitmap(bitmap);
                }
            } else {
                mImage.setImageURI(mUri);
            }
        } else {
            if (mNote.getmPicture() != null && !mNote.getmPicture().equals("")) {
                Bitmap bitmap = BitmapFactory.decodeFile(mNote.getmPicture());
                mImage.setImageBitmap(bitmap);
            } else {
                mImage.setVisibility(View.GONE);
            }
        }

        mBottomBar = view.findViewById(R.id.bottom_bar);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (keyEvent.getAction() == keyEvent.ACTION_UP && i == keyEvent.KEYCODE_BACK) {

                    if (isCreating) {
                        mPresenter.cancelEditing();
                    } else {
                        hideUI();
                    }
                    return false;
                }

                return false;
            }


        });
    }

    private String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date curDate = new Date(System.currentTimeMillis()); // 獲取當前時間
        String str = formatter.format(curDate);

        return str;
    }



    @Override
    public void getTagEditFocus() {

        mBottomBar.setFitsSystemWindows(true);
    }

    @Override
    public void getTagEditUnFocus() {

        mBottomBar.setFitsSystemWindows(false);

    }

    @Override
    public void setPresenter(JotEditContrat.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void hideUI() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(JotEditFragment.this)
                .commit();
    }

    @Override
    public void takeJotData() {

        if (isCreating) {
            mNote = new Note();

//            if (mTitle.getText().toString() != null && !mTitle.getText().toString().equals("")) {
//                mNote.setmTitle(mTitle.getText().toString());
//            } else {
//                mNote.setmTitle("這是一則筆記");
//            }
            mNote.setmText(mText.getText().toString());

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

            if (mImagePath != null) {
                mNote.setmPicture(mImagePath);
            }

            if (mUri != null) {
                mNote.setPhotoFromCamera(mUri.toString());
            }

            mNote.setmUpdatedTime("");
            mNote.setmPlace("市政府");
            mNote.setClassification("jot");
            mNote.setmTag(mTagEditAdapter.TagList());

            mNoteList = new ArrayList<Note>();
            mNoteList.add(mNote);
            mPresenter.insertJotData(getContext(), mNote);
        } else { //isEditing

//            mNote.setmTitle(mTitle.getText().toString());
            mNote.setmText(mText.getText().toString());
            if (mUri != null) {
                mNote.setPhotoFromCamera(mUri.toString());
            } else if (mImagePath != null) {
                mNote.setmPicture(mImagePath);
            }
            mPresenter.updateJotData(getContext(), mNote);

        }

    }

    @Override
    public void onClick(View view) {

    }
}
