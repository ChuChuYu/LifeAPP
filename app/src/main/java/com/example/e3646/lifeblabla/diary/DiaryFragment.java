package com.example.e3646.lifeblabla.diary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.e3646.lifeblabla.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class DiaryFragment extends Fragment implements DiaryContract.View {

    private DiaryContract.Presenter mPresenter;
    private ImageButton mEditButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        mEditButton = (ImageButton) view.findViewById(R.id.button_edit);
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.goEditDiary();
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setPresenter(DiaryContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
