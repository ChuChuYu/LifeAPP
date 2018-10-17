package com.example.e3646.lifeblabla.dialogfragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.mainactivity.MainActPresenter;

@SuppressLint("ValidFragment")
public class BottomSheetDialogTemplateFragment extends BottomSheetDialogFragment {

    private ImageButton mDiaryButton;
    private ImageButton mAccountButton;
    private ImageButton mConferenceButton;
    private ImageButton mListButton;
    private MainActPresenter mMainActPresenter;

    public BottomSheetDialogTemplateFragment(MainActPresenter mainActPresenter) {
        mMainActPresenter = mainActPresenter;

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.bottom_sheet_dialog_template, container, false);




//        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
        Log.d("background color", ": "+ Color.TRANSPARENT);


        mDiaryButton = (ImageButton)view.findViewById(R.id.button_form_text);
        mDiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainActPresenter.goDiaryEdit();
                mMainActPresenter.hideComponent();
                mMainActPresenter.hideBottomNavigation();
                dismiss();
            }
        });

        mAccountButton = (ImageButton)view.findViewById(R.id.button_form_album);
        mAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainActPresenter.goAccountEdit();
                mMainActPresenter.hideComponent();
                mMainActPresenter.hideBottomNavigation();
                dismiss();
            }
        });

        mConferenceButton = (ImageButton)view.findViewById(R.id.button_form_camera);
        mConferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mListButton = (ImageButton)view.findViewById(R.id.button_type_list);
        mListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((View) getView().getParent()).setBackgroundColor(Color.TRANSPARENT);
    }
}
