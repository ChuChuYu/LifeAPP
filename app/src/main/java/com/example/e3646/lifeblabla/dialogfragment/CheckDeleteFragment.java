package com.example.e3646.lifeblabla.dialogfragment;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

@SuppressLint("ValidFragment")
public class CheckDeleteFragment extends DialogFragment {

    private ImageButton mCancelButton;
    private ImageButton mConfirmButton;

    private DiaryPresenter mDiaryPresenter;
    private String mId;

    public CheckDeleteFragment(DiaryPresenter diaryPresenter, String id) {
        this.mDiaryPresenter = diaryPresenter;
        this.mId = id;
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

                mDiaryPresenter.deleteNoteData(mId);
                mDiaryPresenter.completeDeleting();
                getDialog().dismiss();
            }
        });




        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
