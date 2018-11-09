package com.sandy.e3646.lifeblabla.dialogfragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.sandy.e3646.lifeblabla.R;

import com.sandy.e3646.lifeblabla.account.AccountPresenter;
import com.sandy.e3646.lifeblabla.diary.DiaryPresenter;
import com.sandy.e3646.lifeblabla.jot.JotPresenter;

@SuppressLint("ValidFragment")
public class CheckDeleteFragment extends DialogFragment {

    private ImageButton mCancelButton;
    private ImageButton mConfirmButton;

    private DiaryPresenter mDiaryPresenter;
    private JotPresenter mJotPresenter;
    private AccountPresenter mAccountPresenter;
    private String mId;

    private int NOTE_TYPE;

    public CheckDeleteFragment(DiaryPresenter diaryPresenter, JotPresenter jotPresenter, AccountPresenter accountPresenter,  String id, int noteType) {
        this.mDiaryPresenter = diaryPresenter;
        this.mJotPresenter = jotPresenter;
        this.mAccountPresenter = accountPresenter;
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
                } else if (NOTE_TYPE == 3) {
                    mAccountPresenter.deleteNoteData(mId);
                    mAccountPresenter.completeDeleting();
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
