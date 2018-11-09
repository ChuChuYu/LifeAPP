package com.sandy.e3646.lifeblabla.search;

import static com.google.common.base.Preconditions.checkNotNull;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.main.MainAdapter;
import com.sandy.e3646.lifeblabla.object.Note;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class SearchFragment extends Fragment implements SearchContract.View {

    private SearchContract.Presenter mPresenter;
    private String mTag;
    private ArrayList<Note> mNoteList;

    private TextView mSearchTag;
    private TextView mSearchNumber;
    private ImageButton mBackButton;
    private RecyclerView mRecyclerView;
    private SearchAdapter mSearchAdapter;

    public SearchFragment(String tag) {
        mTag = tag;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        init(view);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideUI();
            }
        });

        return view;
    }

    private void init(View view) {

        mNoteList = mPresenter.getSearchList(getContext(), mTag);
        mSearchTag = view.findViewById(R.id.search_tag);
        mSearchTag.setText(mTag);

        mSearchNumber = view.findViewById(R.id.search_number);
        mSearchNumber.setText(String.valueOf(mNoteList.size()));

        mBackButton = view.findViewById(R.id.button_back);

        mRecyclerView = view.findViewById(R.id.search_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mSearchAdapter = new SearchAdapter(getContext(), mNoteList);
        mRecyclerView.setAdapter(mSearchAdapter);
//        mMainAdapter.setOnItemListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPresenter.showFragment(mNoteList.get((int)view.getTag()));
//            }
//        });

    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);

    }

    @Override
    public void hideUI() {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.detach(SearchFragment.this)
                .commit();

    }
}
