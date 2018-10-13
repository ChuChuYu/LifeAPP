package com.example.e3646.lifeblabla.account;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.e3646.lifeblabla.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class AccountEditFragment extends Fragment implements AccountEditContract.View {

    private AccountEditContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;
    private AccountAdapter mAccountAdapter;

    private RecyclerView mAccountAddRecyclerView;
    private AccountAddAdapter mAccountAddAdapter;

    private ImageButton mAddButton;
    private int ACCOUNT_SIZE = 1;


//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_accountedit, container, false);
//
//        mRecyclerView = view.findViewById(R.id.account_edit_recyclerview);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mAccountAdapter = new AccountAdapter();
//        mRecyclerView.setAdapter(mAccountAdapter);
//
//        mAccountAddRecyclerView = view.findViewById(R.id.account_add_recyclerview);
//        mAccountAddRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mAccountAddAdapter = new AccountAddAdapter(ACCOUNT_SIZE);
//        mAccountAddRecyclerView.setAdapter(mAccountAddAdapter);
//
//        mAddButton = view.findViewById(R.id.button_add_account_item);
//        mAddButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Log.d("add account button", "click1: " + ACCOUNT_SIZE);
//                ACCOUNT_SIZE += 1;
//                mAccountAddAdapter.setACCOUNTNUMBER(ACCOUNT_SIZE);
//                mAccountAddAdapter.notifyDataSetChanged();
//
//                Log.d("add account button", "click2: " + ACCOUNT_SIZE);
//            }
//        });
//
//        return view;
//    }

    @Override
    public void setPresenter(AccountEditContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);

    }
}
