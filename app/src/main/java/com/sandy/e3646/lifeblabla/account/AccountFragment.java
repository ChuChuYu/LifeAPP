package com.sandy.e3646.lifeblabla.account;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sandy.e3646.Sqldatabase;
import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.adapter.TagAdapter;
import com.sandy.e3646.lifeblabla.object.Account;
import com.sandy.e3646.lifeblabla.object.Note;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

@SuppressLint("ValidFragment")
public class AccountFragment extends Fragment implements AccountContract.View {

    private AccountContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;
    private AccountAdapter mAccountAdapter;

    private TextView mTitle;

    private TextView mRevenueText;
    private TextView mExpenseText;
    private TextView mBalanceText;

    private TextView mCreatedTime;


    private ImageButton mBackButton;
    private ImageButton mEditButton;
    private ImageButton mDeleteButton;

    private ArrayList<Account> mAccountList;

    private Note mNote;

    private RecyclerView mTagRecycelrview;
    private TagAdapter mTagAdapter;
    private boolean isListing;

    public AccountFragment(Note note, boolean islisting) {

        mNote = note;
        isListing = islisting;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);


        init(view);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.backToMain(isListing);
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.showCheckDeleteDialog();
            }
        });

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPresenter.goEditAccount(false, mNote, isListing);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (keyEvent.getAction() == keyEvent.ACTION_UP && i == keyEvent.KEYCODE_BACK) {

                    mPresenter.backToMain(isListing);
                    return false;
                }

                return false;
            }


        });
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

        mRevenueText = view.findViewById(R.id.account_revenue);
        mExpenseText = view.findViewById(R.id.account_expense);
        mBalanceText = view.findViewById(R.id.account_balance);

        mTagRecycelrview = (RecyclerView) view.findViewById(R.id.tag_recyclerview);



        if (mNote.getmTag() != null && ! mNote.getmTag().get(0).equals("")) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mTagRecycelrview.setLayoutManager(linearLayoutManager);
            mTagAdapter = new TagAdapter(mNote.getmTag());
            mTagRecycelrview.setAdapter(mTagAdapter);
            mTagAdapter.setOnItemListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.goSearch(mNote.getmTag().get((int)view.getTag()));
                }
            });

        } else if (mNote.getmTag() == null || mNote.getmTag().get(0).equals("")) {
            mTagRecycelrview.setVisibility(View.GONE);
        }



        setNoteData();

        mCreatedTime = (TextView)view.findViewById(R.id.created_time);

        mCreatedTime.setText(mNote.getmCreatedTime());

    }



    @Override
    public void setPresenter(AccountContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);

    }

    @Override
    public void setNoteData() {
        mTitle.setText(mNote.getmTitle());
        mRevenueText.setText(mNote.getAccountRevenue());
        mExpenseText.setText(mNote.getAccountExpense());
        mBalanceText.setText(mNote.getAccountBalance());
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
