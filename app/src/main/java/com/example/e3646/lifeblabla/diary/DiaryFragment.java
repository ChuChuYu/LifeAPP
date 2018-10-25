package com.example.e3646.lifeblabla.diary;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e3646.Sqldatabase;
import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.object.Note;

import static com.google.common.base.Preconditions.checkNotNull;

@SuppressLint("ValidFragment")
public class DiaryFragment extends Fragment implements DiaryContract.View {

    private DiaryContract.Presenter mPresenter;
    private ImageButton mEditButton;
    private ImageButton mBackButton;
    private ImageButton mDeleteButton;
    private TextView mCreatedTime;
    private TextView mTitle;
    private TextView mText;
    private ImageView mPicture;
    private ImageView mEmotion;
    private ImageView mWeather;
    private Note mNote;

    private RecyclerView mTagRecyclerView;
    private DiaryAdapter mDiaryAdapter;

    public DiaryFragment(Note note) {
        mNote = note;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        mCreatedTime = (TextView)view.findViewById(R.id.diary_detail_created_time);
        mTitle = (TextView) view.findViewById(R.id.jot_title);
        mText = (TextView)view.findViewById(R.id.jot_text);
        mEmotion = (ImageView)view.findViewById(R.id.note_diary_emotion);
        mWeather = (ImageView)view.findViewById(R.id.note_diary_weather);
        mPicture = (ImageView)view.findViewById(R.id.diary_image);
        mTagRecyclerView = (RecyclerView) view.findViewById(R.id.tag_recyclerview);

        mEditButton = (ImageButton) view.findViewById(R.id.button_edit);
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.goEditDiary(false, mNote);
            }
        });

        mBackButton = (ImageButton)view.findViewById(R.id.button_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPresenter.backToMain();
            }
        });

        mDeleteButton = (ImageButton)view.findViewById(R.id.button_delete);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.showCheckDeleteDialog();

            }
        });

        setNoteData(mNote);

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

                    mPresenter.backToMain();
                    return false;
                }

                return false;
            }


        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setPresenter(DiaryContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void hideUI() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.detach(DiaryFragment.this)
                .commit();
    }

    @Override
    public void setNoteData(Note note) {
        mNote = note;

        mCreatedTime.setText(note.getmCreatedTime());
        mTitle.setText(note.getmTitle());
        mText.setText(note.getmText());

        if (mNote.getmTag() != null && ! mNote.getmTag().get(0).equals("")) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mTagRecyclerView.setLayoutManager(linearLayoutManager);
            mDiaryAdapter = new DiaryAdapter(mNote.getmTag());
            mTagRecyclerView.setAdapter(mDiaryAdapter);

            mDiaryAdapter.setOnItemListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mPresenter.goSearch(mNote.getmTag().get((int)view.getTag()));

                }
            });
        } else if (mNote.getmTag() == null || mNote.getmTag().get(0).equals("")) {
            mTagRecyclerView.setVisibility(View.GONE);

        }

        if (mNote.getmMind() != null) {

            if (mNote.getmMind().equals("1")) {
                mEmotion.setImageResource(R.drawable.button_emotion);
            } else if (mNote.getmMind().equals("2")) {
                mEmotion.setImageResource(R.drawable.emotion_2);
            } else if (mNote.getmMind().equals("3")) {
                mEmotion.setImageResource(R.drawable.emotion_3);
            } else if (mNote.getmMind().equals("4")) {
                mEmotion.setImageResource(R.drawable.emotion_4);
            } else if (mNote.getmMind().equals("5")) {
                mEmotion.setImageResource(R.drawable.emotion_5);
            } else if (mNote.getmMind().equals("6")) {
                mEmotion.setImageResource(R.drawable.emotion_6);
            }
        }

        if(mNote.getmWeather() != null) {
            if (mNote.getmWeather().equals("1")) {
                mWeather.setImageResource(R.drawable.weather_1);
            } else if (mNote.getmWeather().equals("2")) {
                mWeather.setImageResource(R.drawable.button_weather);
            } else if (mNote.getmWeather().equals("3")) {
                mWeather.setImageResource(R.drawable.weather_3);
            } else if (mNote.getmWeather().equals("4")) {
                mWeather.setImageResource(R.drawable.weather_4);
            } else if (mNote.getmWeather().equals("5")) {
                mWeather.setImageResource(R.drawable.weather_5);
            } else if (mNote.getmWeather().equals("6")) {
                mWeather.setImageResource(R.drawable.weather_6);
            }
        }

        if (mNote.getPhotoFromCamera() == null || mNote.getPhotoFromCamera().equals("")) {

            if (mNote.getmPicture() == null || mNote.getmPicture().equals("")) {
                mPicture.setVisibility(View.GONE);
            } else {
                Bitmap bitmap = BitmapFactory.decodeFile(mNote.getmPicture());
                mPicture.setImageBitmap(bitmap);
            }

        } else {

            mPicture.setImageURI(Uri.parse(mNote.getPhotoFromCamera()));

        }



    }

    @Override
    public void deleteNoteData(String id) {
        Sqldatabase sql = new Sqldatabase(getContext());
        sql.deleteNote(id);
    }

    @Override
    public void parseNote(Note note) {
        this.mNote = note;
    }

}
