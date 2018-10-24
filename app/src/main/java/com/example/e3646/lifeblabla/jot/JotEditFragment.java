package com.example.e3646.lifeblabla.jot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.adapter.TitleAdapter;
import com.example.e3646.lifeblabla.diary.DiaryEditAdapter;
import com.example.e3646.lifeblabla.object.Note;

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
    private DiaryEditAdapter mDiaryEditAdapter;
    private EditText mJotTag;
    private RecyclerView mTitleRecycelrView;
    private TitleAdapter mTitleAdapter;
    private ImageButton mExpandButton;
    private ImageView mImage;

    private BottomSheetBehavior mBottomSheetBehavior;

    private Button mTitleOne;
    private Button mTitleTwo;
    private Button mTitleThree;

    private String mImagePath;
    private Uri mUri;


    public JotEditFragment(boolean isCreating, Note note, String imagePath, Uri uri) {
        this.isCreating = isCreating;
        mNote = note;
        mImagePath = imagePath;
        mUri = uri;
        Log.d("image path", "in jot: " + imagePath);
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
        mDiaryEditAdapter = new DiaryEditAdapter();
        mTagRecyclerView.setAdapter(mDiaryEditAdapter);

//        mTitleRecycelrView = (RecyclerView)view.findViewById(R.id.title_recyclerview);
//        mTitleRecycelrView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mTitleAdapter = new TitleAdapter();
//        mTitleRecycelrView.setAdapter(mTitleAdapter);
//        mTitleRecycelrView.getItemAnimator().setChangeDuration(300);
//        mTitleRecycelrView.getItemAnimator().setMoveDuration(300);


        mJotTag = (EditText)view.findViewById(R.id.sample_title);
        mJotTag.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                Log.d("done button", "click 2");
                return false;
            }
        });

        if (!isCreating && mNote != null) {
            mTitle.setText(mNote.getmTitle());
            mText.setText(mNote.getmText());
            mCreatedTime.setText(mNote.getmCreatedTime());

        } else {
            mCreatedTime.setText(currentTime());
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
//        mExpandButton = (ImageButton)view.findViewById(R.id.button_expand);
//        mExpandButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isExpanded == false) {
//                    mExpandButton.setImageResource(R.drawable.button_expand_less);
//                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                    isExpanded = true;
//                } else {
//                    mExpandButton.setImageResource(R.drawable.button_expand);
//                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                    isExpanded = false;
//                }
//
//            }
//        });

        mTitleOne = (Button)view.findViewById(R.id.button_title_1);
        mTitleOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("title", "select");
                mTitle.setText("用行動找回旅行「最初的感動」");
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        mTitleTwo = (Button)view.findViewById(R.id.button_title_2);
        mTitleTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTitle.setText("浪漫遊四國寫下少爺與公主的物語");
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        mTitleThree = (Button)view.findViewById(R.id.button_title_3);
        mTitleThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTitle.setText("溫暖您疲憊的心 道後靈湯");
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        mImage = view.findViewById(R.id.diary_image);

        if (mUri == null) {
            if (mImagePath == null || mImagePath.equals("")) {
                mImage.setVisibility(View.GONE);
            } else {
                Bitmap bitmap = BitmapFactory.decodeFile(mImagePath);
                mImage.setImageBitmap(bitmap);
            }
        } else {
            mImage.setImageURI(mUri);
        }



        return view;
    }

    private String currentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

        Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間

        String str = formatter.format(curDate);

        return str;
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
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//            SimpleDateFormat formateForID = new SimpleDateFormat("yyyyMMddHHmmss");
//            Date curDate = new Date(System.currentTimeMillis());
//            String currentTime = formatter.format(curDate);
//            String id = formateForID.format(curDate);

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

            if (Integer.parseInt(daytime) > 12 ) {
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
            mNote.setmTag(mDiaryEditAdapter.TagList());

            mNoteList = new ArrayList<Note>();
            mNoteList.add(mNote);
            mPresenter.insertJotData(getContext(), mNote);
        } else { //isEditing

            mNote.setmTitle(mTitle.getText().toString());
            mNote.setmText(mText.getText().toString());
//            mNote.setmPicture(mImagePath);
            mPresenter.updateJotData(getContext(), mNote);

        }

    }

    @Override
    public void onClick(View view) {

    }
}
