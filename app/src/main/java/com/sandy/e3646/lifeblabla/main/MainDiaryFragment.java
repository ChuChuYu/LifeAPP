package com.sandy.e3646.lifeblabla.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sandy.e3646.Sqldatabase;
import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.diary.DiaryFragment;
import com.sandy.e3646.lifeblabla.diary.DiaryPresenter;
import com.sandy.e3646.lifeblabla.mainactivity.MainActPresenter;
import com.sandy.e3646.lifeblabla.object.Note;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class MainDiaryFragment extends Fragment implements MainContract.View{

    private MainContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private MainAdapter mMainAdapter;
    private MainAdapterGrid mMainAdapterGrid;

    private DiaryFragment mDiaryFragment;
    private DiaryPresenter mDiaryPresenter;
    private FragmentManager mFragmentManager;
    private MainActPresenter mMainActPresenter;

    private boolean isListing;

    public MainDiaryFragment(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_diary, container, false);

        Sqldatabase sql = new Sqldatabase(getContext());
        ArrayList<Note> noteList = sql.getNotes();
        final ArrayList<Note> diaryList = new ArrayList<Note>();

        for (int i = 0; i < noteList.size(); i++) {
            if (noteList.get(i).getmClassification().equals("diary")) {
                diaryList.add(noteList.get(i));
            }
        }

        mRecyclerView = view.findViewById(R.id.main_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMainAdapter = new MainAdapter(getContext(), diaryList);
        mMainAdapterGrid = new MainAdapterGrid(getContext(), diaryList);

        mRecyclerView.setAdapter(mMainAdapter);

        mMainAdapter.setOnItemListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (diaryList.get((int)view.getTag()).getmClassification().equals("diary")) {
                    mPresenter.showdiary(diaryList.get((int)view.getTag()), isListing);
                }
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

        isListing = false;

    }

    @Override
    public void showListLayout() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mMainAdapter);

        isListing = true;

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
