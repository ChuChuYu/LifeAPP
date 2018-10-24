package com.example.e3646.lifeblabla.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e3646.Sqldatabase;
import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.object.Note;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@SuppressLint("ValidFragment")
public class MainJotFragment extends Fragment implements MainContract.View {

    private MainContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private MainAdapter mMainAdapter;
    private MainAdapterGrid mMainAdapterGrid;

    private ArrayList<Note> mJotList;
    private boolean isListMode = true;

    public MainJotFragment(boolean islistmode) {
        isListMode = islistmode;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_jot, container, false);

        Sqldatabase sql = new Sqldatabase(getContext());
        ArrayList<Note> noteList = sql.getNotes();
        final ArrayList<Note> jotList = new ArrayList<Note>();

        for (int i = 0; i < noteList.size(); i++) {
            if (noteList.get(i).getmClassification().equals("jot")) {
                jotList.add(noteList.get(i));
            }
        }
        mJotList = jotList;

        mRecyclerView = view.findViewById(R.id.main_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMainAdapter = new MainAdapter(getContext(), jotList);
        mMainAdapterGrid = new MainAdapterGrid(getContext(), jotList);
        mRecyclerView.setAdapter(mMainAdapter);
        mMainAdapter.setOnItemListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.showdiary(jotList.get((int)view.getTag()));
            }
        });


        return view;
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showGridLayout() {

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mMainAdapterGrid);

    }

    @Override
    public void showListLayout() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mMainAdapter);
    }

    @Override
    public void showDiaryUI() {

    }

    @Override
    public void refreshList() {

    }

    @Override
    public void hideUI() {

    }
}
