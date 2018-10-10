package com.example.e3646.lifeblabla.dialogfragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.bottom_sheet_dialog_template, null);
        dialog.setContentView(contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent())
                .getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.bottom_sheet_dialog_template, container, false);




//        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
//        Log.d("background color", ": "+ Color.TRANSPARENT);
//        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);

        mDiaryButton = (ImageButton)view.findViewById(R.id.button_type_diary);
        mDiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMainActPresenter.goDiaryEdit();
                mMainActPresenter.hideComponent();
                dismiss();

            }
        });

        mAccountButton = (ImageButton)view.findViewById(R.id.button_type_account);
        mAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mConferenceButton = (ImageButton)view.findViewById(R.id.button_type_conference);
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

}
