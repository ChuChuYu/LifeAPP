package com.example.e3646.lifeblabla.Search;

import static com.google.common.base.Preconditions.checkNotNull;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.main.MainAdapter;

@SuppressLint("ValidFragment")
public class SearchFragment extends Fragment implements SearchContract.View {

    private SearchContract.Presenter mPresenter;
    private String mTag;

    private TextView mSearchTag;
    private TextView mSearchNumber;
    private RecyclerView mRecyclerView;
    private MainAdapter mMainAdapter;

    public SearchFragment(String tag) {
        mTag = tag;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        init(view);

        return view;
    }

    private void init(View view) {

        mSearchTag = view.findViewById(R.id.search_tag);
        mSearchTag.setText(mTag);

        mSearchNumber = view.findViewById(R.id.search_number);
        mRecyclerView = view.findViewById(R.id.search_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMainAdapter = new MainAdapter(getContext(), null);
        mRecyclerView.setAdapter(mMainAdapter);

    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);

    }

    @Override
    public void hideUI() {

    }
}
