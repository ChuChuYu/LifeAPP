package com.example.e3646.lifeblabla.addnote;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.e3646.lifeblabla.R;

@SuppressLint("ValidFragment")
public class ModeSelectionFragment extends DialogFragment {

    private Button mDiaryButton;
    private Button mJotButton;
    private Button mAccountButton;
    private Button mConferenceButton;
    private Button mTodolistButton;
    private Bundle mModeSelection;

    private AddNoteFragment mAddNoteFragment;

    public ModeSelectionFragment(AddNoteFragment addNoteFragment) {
     mAddNoteFragment = addNoteFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modeselection, container, false);

        mModeSelection = new Bundle();

        mDiaryButton = view.findViewById(R.id.button_diary);
        mDiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAddNoteFragment.setModeSelection("日記");
                getDialog().dismiss();

            }
        });

        mJotButton = view.findViewById(R.id.button_jot);
        mJotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAddNoteFragment.setModeSelection("隨手");
                getDialog().dismiss();
            }
        });

        mAccountButton = view.findViewById(R.id.button_account);
        mAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAddNoteFragment.setModeSelection("記帳");
                getDialog().dismiss();
            }
        });

        mConferenceButton = view.findViewById(R.id.button_conference);
        mConferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAddNoteFragment.setModeSelection("會議");
                getDialog().dismiss();
            }
        });

        mTodolistButton = view.findViewById(R.id.button_todolist);
        mTodolistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAddNoteFragment.setModeSelection("待辦");
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
