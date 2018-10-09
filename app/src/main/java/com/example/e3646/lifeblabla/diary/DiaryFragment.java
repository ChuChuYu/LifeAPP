package com.example.e3646.lifeblabla.diary;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private Note mNote;

    private RecyclerView mTagRecyclerView;
    private DiaryAdapter mDiaryAdapter;
    private ImageView mTagBackground;

    public DiaryFragment(Note note) {
        mNote = note;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        mCreatedTime = (TextView)view.findViewById(R.id.diary_detail_created_time);
        mTitle = (TextView) view.findViewById(R.id.diary_detail_title);
        mText = (TextView)view.findViewById(R.id.diary_detail_text);
        mTagBackground = (ImageView)view.findViewById(R.id.tag_view_background);

        setNoteData(mNote);
//        mCreatedTime.setText(mNote.getmCreatedTime());
//        mTitle.setText(mNote.getmTitle());
//        mText.setText(mNote.getmText());

        if (mNote.getmTag() != null ) {
            mTagRecyclerView = (RecyclerView) view.findViewById(R.id.tag_recyclerview);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mTagRecyclerView.setLayoutManager(linearLayoutManager);
            mDiaryAdapter = new DiaryAdapter(mNote.getmTag());
            mTagRecyclerView.setAdapter(mDiaryAdapter);
        } else {
            mTagBackground.setVisibility(View.GONE);
//            mTagRecyclerView.setVisibility(View.GONE);

        }


        mPicture = (ImageView)view.findViewById(R.id.diary_picture);

        if (mNote.getmPicture() != null && !mNote.getmPicture().equals("")) {
            Bitmap bitmap = BitmapFactory.decodeFile(mNote.getmPicture());
            mPicture.setImageBitmap(bitmap);
        }

        mEditButton = (ImageButton) view.findViewById(R.id.button_edit);
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.goEditDiary(false);
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

        return view;
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
    public void refreshDetail() {

    }

    @Override
    public void hideUI() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(DiaryFragment.this)
                .commit();
    }

    @Override
    public void setNoteData(Note note) {
        mNote = note;

        mCreatedTime.setText(mNote.getmCreatedTime());
        mTitle.setText(mNote.getmTitle());
        mText.setText(mNote.getmText());

        //dosen't work
//        if (mNote.getmPicture() != null) {
//            Log.d("picture", ": " + mNote.getmPicture());
//            Bitmap bitmap = BitmapFactory.decodeFile(mNote.getmPicture());
//            mPicture.setImageBitmap(bitmap);
//        }

    }

    @Override
    public void deleteNoteData(String id) {
        Sqldatabase sql = new Sqldatabase(getContext());
        sql.deleteNote(id);
    }


}
