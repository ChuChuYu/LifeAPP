package com.example.e3646.lifeblabla.account;

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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.e3646.Sqldatabase;
import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.object.Account;
import com.example.e3646.lifeblabla.object.Note;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

@SuppressLint("ValidFragment")
public class AccountFragment extends Fragment implements AccountContract.View {

    private AccountContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;
    private AccountAdapter mAccountAdapter;

    private TextView mTitle;

    private ImageButton mBackButton;
    private ImageButton mEditButton;
    private ImageButton 

    private ArrayList<Account> mAccountList;

    private Note mNote;

    public AccountFragment(Note note) {

        mNote = note;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        init(view);
        return view;
    }

    private void init(View view) {

        Sqldatabase sql = new Sqldatabase(getContext());
        mAccountList = sql.getAccounts(mNote.getmId());

        mRecyclerView = view.findViewById(R.id.account_item_recyclerview);
        mRecyclerView = view.findViewById(R.id.account_item_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAccountAdapter = new AccountAdapter(mAccountList, false);
        mRecyclerView.setAdapter(mAccountAdapter);

        mTitle = view.findViewById(R.id.account_title);

        setNoteData();
    }



    @Override
    public void setPresenter(AccountContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);

    }

    @Override
    public void setNoteData() {
        mTitle.setText(mNote.getmTitle());
    }
}
