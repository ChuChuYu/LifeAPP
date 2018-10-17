package com.example.e3646.lifeblabla.dialogfragment;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.e3646.lifeblabla.R;

import com.example.e3646.lifeblabla.diary.DiaryPresenter;
import com.example.e3646.lifeblabla.jot.JotPresenter;

@SuppressLint("ValidFragment")
public class CheckDeleteFragment extends DialogFragment {

    private ImageButton mCancelButton;
    private ImageButton mConfirmButton;

    private DiaryPresenter mDiaryPresenter;
    private JotPresenter mJotPresenter;
    private String mId;

    private int NOTE_TYPE;

    public CheckDeleteFragment(DiaryPresenter diaryPresenter, JotPresenter jotPresenter, String id, int noteType) {
        this.mDiaryPresenter = diaryPresenter;
        this.mJotPresenter = jotPresenter;
        this.mId = id;
        this.NOTE_TYPE = noteType;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkdelete, container, false);

        mCancelButton = (ImageButton)view.findViewById(R.id.button_cancel);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        mConfirmButton = (ImageButton)view.findViewById(R.id.button_confirm);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (NOTE_TYPE == 1) {
                    mDiaryPresenter.deleteNoteData(mId);
                    mDiaryPresenter.completeDeleting();
                    getDialog().dismiss();
                } else if (NOTE_TYPE == 2) {
                    mJotPresenter.deleteNoteData(mId);
                    mJotPresenter.completeDeleting();
                    getDialog().dismiss();
                }
            }
        });




        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
