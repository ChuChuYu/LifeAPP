package com.example.e3646.lifeblabla.jot;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.e3646.Sqldatabase;
import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.object.Note;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

@SuppressLint("ValidFragment")
public class JotEditFragment extends Fragment implements JotEditContrat.View {

    private JotEditContrat.Presenter mPresenter;
    private boolean isCreating;
    private Note mNote;
    private ArrayList<Note> mNoteList;

    private Button mCancelButton;
    private Button mCompleteButton;
    private TextView mCreatedTime;
    private EditText mTitle;
    private EditText mText;

    public JotEditFragment(boolean isCreating, Note note) {
        this.isCreating = isCreating;
        mNote = note;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jotedit, container, false);

        mTitle = (EditText)view.findViewById(R.id.jot_title);
        mText = (EditText)view.findViewById(R.id.jot_text);
        mCreatedTime = (TextView)view.findViewById(R.id.jot_createdtime);

        if (isCreating && mNote != null) {
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

                    mPresenter.completeEditing();


                }
            }
        });

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
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat formateForID = new SimpleDateFormat("yyyyMMddHHmmss");
            Date curDate = new Date(System.currentTimeMillis());
            String currentTime = formatter.format(curDate);
            String id = formateForID.format(curDate);

            mNote = new Note();
            mNote.setmId(id);
            if (mTitle.getText().toString() != null && !mTitle.getText().toString().equals("")) {
                mNote.setmTitle(mTitle.getText().toString());
            } else {
                mNote.setmTitle("這是一則筆記");
            }
            mNote.setmText(mText.getText().toString());
            mNote.setmCreatedTime(currentTime);
            mNote.setmUpdatedTime("");
            mNote.setmPlace("市政府");
            mNote.setClassification("jot");
//
//            Log.d("set image path", ": " + mImagePath);
//            mNote.setmPicture(mImagePath);

//            mNote.setmTag(mDiaryEditAdapter.TagList());



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
}
