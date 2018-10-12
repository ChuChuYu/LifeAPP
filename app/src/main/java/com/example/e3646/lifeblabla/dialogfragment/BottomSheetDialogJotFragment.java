package com.example.e3646.lifeblabla.dialogfragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.diary.DiaryFragment;
import com.example.e3646.lifeblabla.mainactivity.MainActPresenter;

@SuppressLint("ValidFragment")
public class BottomSheetDialogJotFragment extends BottomSheetDialogFragment {

    private ImageButton mTextButton;
    private ImageButton mAlbumButton;
    private ImageButton mCameraButton;
    private MainActPresenter mMainActPresenter;

    public BottomSheetDialogJotFragment(MainActPresenter mainActPresenter) {
        mMainActPresenter = mainActPresenter;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.bottom_sheet_dialog_jot, container, false);

        mTextButton = (ImageButton)view.findViewById(R.id.button_form_text);
        mTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMainActPresenter.goJotEdit();
                mMainActPresenter.hideBottomNavigation();
                mMainActPresenter.hideComponent();
                dismiss();


            }
        });

        mAlbumButton = (ImageButton)view.findViewById(R.id.button_form_album);
        mAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mCameraButton = (ImageButton)view.findViewById(R.id.button_form_camera);
        mCameraButton.setOnClickListener(new View.OnClickListener() {
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
