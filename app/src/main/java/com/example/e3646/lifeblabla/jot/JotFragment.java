package com.example.e3646.lifeblabla.jot;

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
import com.example.e3646.lifeblabla.diary.DiaryAdapter;
import com.example.e3646.lifeblabla.object.Note;

import static com.google.common.base.Preconditions.checkNotNull;

@SuppressLint("ValidFragment")
public class JotFragment extends Fragment implements JotContract.View {

    private JotContract.Presenter mPresenter;
    private ImageButton mBackButton;
    private ImageButton mEditButton;
    private ImageButton mDeleteButton;

    private ImageView mImage;

    private RecyclerView mTagRecyclerView;
    private JotAdapter mJotAdapter;

    private DiaryAdapter mTagAdapter;
    private ImageView mTagBackground;

    private TextView mCreatedTime;
    private TextView mTitle;
    private TextView mText;



    private Note mNote;

    public JotFragment(Note note) {
        mNote = note;

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jot, container, false);

        mTagBackground = (ImageView)view.findViewById(R.id.tag_view_background);
        mCreatedTime = (TextView)view.findViewById(R.id.jot_createdtime);
        mTitle = (TextView)view.findViewById(R.id.jot_title);
        mText = (TextView)view.findViewById(R.id.jot_text);
        mImage = view.findViewById(R.id.diary_image);

        setNoteData(mNote);

        if (mNote.getmTag() != null && ! mNote.getmTag().get(0).equals("") && ! mNote.getmTag().get(0).equals("null")) {
            mTagRecyclerView = (RecyclerView) view.findViewById(R.id.tag_recyclerview);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mTagRecyclerView.setLayoutManager(linearLayoutManager);
            mTagAdapter = new DiaryAdapter(mNote.getmTag());
            mTagRecyclerView.setAdapter(mTagAdapter);
            mTagAdapter.setOnItemListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.goSearch(mNote.getmTag().get((int)view.getTag()));
                }
            });

        } else if (mNote.getmTag() == null || mNote.getmTag().get(0).equals("") || mNote.getmTag().get(0).equals("null")) {
            mTagBackground.setVisibility(View.GONE);
        }

        mBackButton = (ImageButton)view.findViewById(R.id.button_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.backToMain();
            }
        });


        mEditButton = (ImageButton)view.findViewById(R.id.button_edit);
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.goEditJot(false);

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
    public void setPresenter(JotContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);

    }

    @Override
    public void setNoteData(Note note) {
        mNote = note;
        mCreatedTime.setText(note.getmCreatedTime());
        mTitle.setText(note.getmTitle());
        mText.setText(note.getmText());

        if (mNote.getPhotoFromCamera() == null || mNote.getPhotoFromCamera().equals("")) {
            if (mNote.getmPicture() == null || mNote.getmPicture().equals("")) {
                mImage.setVisibility(View.GONE);
            } else {
                Bitmap bitmap = BitmapFactory.decodeFile(mNote.getmPicture());
                mImage.setImageBitmap(bitmap);
            }
        } else {
            mImage.setImageURI(Uri.parse(mNote.getPhotoFromCamera()));
        }

    }

    @Override
    public void hideUI() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.detach(JotFragment.this)
                .commit();
    }

    @Override
    public void deleteNoteData(String id) {
        Sqldatabase sql = new Sqldatabase(getContext());
        sql.deleteNote(id);
    }
}
