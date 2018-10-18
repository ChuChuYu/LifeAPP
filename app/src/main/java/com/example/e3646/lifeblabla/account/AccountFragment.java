package com.example.e3646.lifeblabla.account;

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
    private ImageButton mDeleteButton;

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

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.backToMain();
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.showCheckDeleteDialog();
            }
        });

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

        mBackButton = view.findViewById(R.id.button_back);
        mEditButton = view.findViewById(R.id.button_edit);
        mDeleteButton = view.findViewById(R.id.button_delete);


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

    @Override
    public void hideUI() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(AccountFragment.this)
                .commit();
    }

    @Override
    public void deleteNoteData(String id) {
        Sqldatabase sql = new Sqldatabase(getContext());
        sql.deleteNote(id);
        sql.deleteAllAccount(id);
        //同時也要刪除account
    }
}
