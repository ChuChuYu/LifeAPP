package com.sandy.e3646.lifeblabla.dialogfragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.diary.DiaryEditPresenter;

@SuppressLint("ValidFragment")
public class MindSelectFragment extends DialogFragment {

    private ImageView mSelectOne;
    private ImageView mSelectTwo;
    private ImageView mSelectThree;
    private ImageView mSelectFour;
    private ImageView mSelectFive;
    private ImageView mSelectSix;

    private ImageButton mMindOneButton;
    private ImageButton mMindTwoButton;
    private ImageButton mMindThreeButton;
    private ImageButton mMindFourButton;
    private ImageButton mMindFiveButton;
    private ImageButton mMindSixButton;

    private DiaryEditPresenter mDiaryEditPresenter;

    public MindSelectFragment(DiaryEditPresenter diaryEditPresenter) {

        mDiaryEditPresenter = diaryEditPresenter;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mindselect, container, false);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mSelectOne = view.findViewById(R.id.select_one);
        mSelectTwo = view.findViewById(R.id.select_two);
        mSelectThree = view.findViewById(R.id.select_three);
        mSelectFour = view.findViewById(R.id.select_four);
        mSelectFive = view.findViewById(R.id.select_five);
        mSelectSix = view.findViewById(R.id.select_six);

        mSelectOne.setVisibility(View.INVISIBLE);
        mSelectTwo.setVisibility(View.INVISIBLE);
        mSelectThree.setVisibility(View.INVISIBLE);
        mSelectFour.setVisibility(View.INVISIBLE);
        mSelectFive.setVisibility(View.INVISIBLE);
        mSelectSix.setVisibility(View.INVISIBLE);

        mMindOneButton = view.findViewById(R.id.button_mind_one);
        mMindTwoButton = view.findViewById(R.id.button_mind_two);
        mMindThreeButton = view.findViewById(R.id.button_mind_three);
        mMindFourButton = view.findViewById(R.id.button_mind_four);
        mMindFiveButton = view.findViewById(R.id.button_mind_five);
        mMindSixButton = view.findViewById(R.id.button_mind_six);

        mMindOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectOne.setVisibility(View.VISIBLE);
                mSelectTwo.setVisibility(View.INVISIBLE);
                mSelectThree.setVisibility(View.INVISIBLE);
                mSelectFour.setVisibility(View.INVISIBLE);
                mSelectFive.setVisibility(View.INVISIBLE);
                mSelectSix.setVisibility(View.INVISIBLE);

                mDiaryEditPresenter.setMindSelection("1");
                dismiss();
            }
        });

        mMindTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectOne.setVisibility(View.INVISIBLE);
                mSelectTwo.setVisibility(View.VISIBLE);
                mSelectThree.setVisibility(View.INVISIBLE);
                mSelectFour.setVisibility(View.INVISIBLE);
                mSelectFive.setVisibility(View.INVISIBLE);
                mSelectSix.setVisibility(View.INVISIBLE);

                mDiaryEditPresenter.setMindSelection("2");
                dismiss();
            }
        });

        mMindThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectOne.setVisibility(View.INVISIBLE);
                mSelectTwo.setVisibility(View.INVISIBLE);
                mSelectThree.setVisibility(View.VISIBLE);
                mSelectFour.setVisibility(View.INVISIBLE);
                mSelectFive.setVisibility(View.INVISIBLE);
                mSelectSix.setVisibility(View.INVISIBLE);

                mDiaryEditPresenter.setMindSelection("3");
                dismiss();
            }
        });

        mMindFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectOne.setVisibility(View.INVISIBLE);
                mSelectTwo.setVisibility(View.INVISIBLE);
                mSelectThree.setVisibility(View.INVISIBLE);
                mSelectFour.setVisibility(View.VISIBLE);
                mSelectFive.setVisibility(View.INVISIBLE);
                mSelectSix.setVisibility(View.INVISIBLE);

                mDiaryEditPresenter.setMindSelection("4");
                dismiss();
            }
        });

        mMindFiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectOne.setVisibility(View.INVISIBLE);
                mSelectTwo.setVisibility(View.INVISIBLE);
                mSelectThree.setVisibility(View.INVISIBLE);
                mSelectFour.setVisibility(View.INVISIBLE);
                mSelectFive.setVisibility(View.VISIBLE);
                mSelectSix.setVisibility(View.INVISIBLE);

                mDiaryEditPresenter.setMindSelection("5");
                dismiss();
            }
        });

        mMindSixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectOne.setVisibility(View.INVISIBLE);
                mSelectTwo.setVisibility(View.INVISIBLE);
                mSelectThree.setVisibility(View.INVISIBLE);
                mSelectFour.setVisibility(View.INVISIBLE);
                mSelectFive.setVisibility(View.INVISIBLE);
                mSelectSix.setVisibility(View.VISIBLE);

                mDiaryEditPresenter.setMindSelection("6");
                dismiss();
            }
        });


        return view;
    }


}
