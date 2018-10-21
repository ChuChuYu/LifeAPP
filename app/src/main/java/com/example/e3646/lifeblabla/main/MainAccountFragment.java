package com.example.e3646.lifeblabla.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e3646.Sqldatabase;
import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.object.Note;

import java.util.ArrayList;

public class MainAccountFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MainAdapter mMainAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_account, container, false);

        Sqldatabase sql = new Sqldatabase(getContext());
        ArrayList<Note> noteList = sql.getNotes();
        ArrayList<Note> accountList = new ArrayList<Note>();

        for (int i = 0; i < noteList.size(); i++) {
            if (noteList.get(i).getmClassification().equals("account")) {
                accountList.add(noteList.get(i));
            }
        }

        mRecyclerView = view.findViewById(R.id.main_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMainAdapter = new MainAdapter(getContext(), accountList);
        mRecyclerView.setAdapter(mMainAdapter);

        return view;
    }
}
