package com.example.e3646.lifeblabla.jot;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.diary.DiaryAdapter;
import com.example.e3646.lifeblabla.diary.DiaryFragment;
import com.example.e3646.lifeblabla.object.Note;

import static com.google.common.base.Preconditions.checkNotNull;

@SuppressLint("ValidFragment")
public class JotFragment extends Fragment implements JotContract.View {

    private JotContract.Presenter mPresenter;
    private ImageButton mBackButton;
    private ImageButton mEditButton;
    private ImageButton mDeleteButton;

    private RecyclerView mTagRecyclerView;
    private JotAdapter mJotAdapter;
    private ImageView mTagBackground;



    private Note mNote;

    public JotFragment(Note note) {
        mNote = note;

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jot, container, false);

        mTagBackground = (ImageView)view.findViewById(R.id.tag_view_background);

        if (mNote.getmTag() != null && ! mNote.getmTag().get(0).equals("") && ! mNote.getmTag().get(0).equals("null")) {
            mTagRecyclerView = (RecyclerView) view.findViewById(R.id.tag_recyclerview);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mTagRecyclerView.setLayoutManager(linearLayoutManager);
            mJotAdapter = new JotAdapter(mNote.getmTag());
            mTagRecyclerView.setAdapter(mJotAdapter);
        } else if (mNote.getmTag() == null || mNote.getmTag().get(0).equals("") || mNote.getmTag().get(0).equals("null")) {
            mTagBackground.setVisibility(View.GONE);
//            mTagRecyclerView.setVisibility(View.GONE);

        }

        mBackButton = (ImageButton)view.findViewById(R.id.button_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.backToMain();
            }
        });


        mEditButton = (ImageButton)view.findViewById(R.id.button_edit);
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mDeleteButton = (ImageButton)view.findViewById(R.id.button_delete);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return view;
    }

    @Override
    public void setPresenter(JotContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);

    }

    @Override
    public void hideUI() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.detach(JotFragment.this)
                .commit();
    }
}
