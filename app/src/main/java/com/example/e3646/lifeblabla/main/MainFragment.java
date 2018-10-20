package com.example.e3646.lifeblabla.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e3646.Sqldatabase;
import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.mainactivity.MainActivity;
import com.example.e3646.lifeblabla.object.Note;
import com.example.e3646.lifeblabla.object.SpacesItemDecoration;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;


@SuppressLint("ValidFragment")
public class MainFragment extends Fragment implements MainContract.View {

    private Context mContext;

    private MainContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private MainAdapter mMainAdapter;
    private MainAdapterGrid mMainAdapterGrid;
    private ArrayList<Note> mNoteList;
//    private boolean isListMode = false;

    public MainFragment(ArrayList<Note> noteList, boolean islistMode) {

        mNoteList = noteList;

        if (mNoteList != null) {
            mMainAdapter.notifyDataSetChanged();
            Log.d("NoteList", "title" + mNoteList.get(0).getmTitle());
        }
//        isListMode = islistMode;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mContext = getContext();
        mPresenter.setContext(mContext);


        Sqldatabase sql = new Sqldatabase(mContext);
        mNoteList = sql.getNotes();


        mRecyclerView = (RecyclerView)view.findViewById(R.id.main_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mRecyclerView.addItemDecoration(new SpacesItemDecoration(20));
        mMainAdapter = new MainAdapter(getContext(), mNoteList);
        mMainAdapterGrid = new MainAdapterGrid(getContext());
        mRecyclerView.setAdapter(mMainAdapter);


        mMainAdapter.setOnItemListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mPresenter.takeNoteListPosition((int)view.getTag());
                mPresenter.showFragment((int)view.getTag());
//                showDiaryUI();
            }
        });

//        if (!isListMode) {
//            mMainAdapter.setOnItemListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    showDiaryUI();
//                }
//            });
//        } else {
//            mMainAdapterGrid.setOnItemListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    showDiaryUI();
//                }
//            });
//
//        }

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void showGridLayout() {

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        mRecyclerView.addItemDecoration(new SpacesItemDecoration(-20));
        mRecyclerView.setAdapter(mMainAdapterGrid);

    }

    @Override
    public void showListLayout() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mRecyclerView.addItemDecoration(new SpacesItemDecoration(20));
        mRecyclerView.setAdapter(mMainAdapter);

    }

    @Override
    public void showDiaryUI() {

        ((MainActivity) getActivity()).goDiaryDetail();

    }


    @Override
    public void refreshList() {
        mMainAdapterGrid.notifyDataSetChanged();
        mMainAdapter.notifyDataSetChanged();
    }


    @Override
    public void hideUI() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(MainFragment.this)
                .commit();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
