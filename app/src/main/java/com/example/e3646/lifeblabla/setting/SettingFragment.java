package com.example.e3646.lifeblabla.setting;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.e3646.lifeblabla.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class SettingFragment extends Fragment implements SettingContract.View {

    private SettingContract.Presenter mPresenter;
    private BottomSheetBehavior mBottomSheetBehavior;
    private CoordinatorLayout mCoordinatorLayout;

    private ImageButton mAddButton;
    private boolean isExpanded = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//// 设置状态栏颜色
//            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//        else if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.KITKAT) {
//            // 通过设置背景色来修改状态栏颜色
//            mCoordinatorLayout.setBackgroundColor(Color.TRANSPARENT);
//
////            setStatusBarColor(Color.TRANSPARENT)
//        }


        mBottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet_layout));
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mAddButton = (ImageButton)view.findViewById(R.id.button_add);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpanded == false) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    isExpanded = true;
                } else {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    isExpanded = false;
                }

            }
        });

        return view;
    }

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);

    }
}
