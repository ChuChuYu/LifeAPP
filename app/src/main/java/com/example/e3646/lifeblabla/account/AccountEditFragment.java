package com.example.e3646.lifeblabla.account;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.e3646.Sqldatabase;
import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.object.Account;
import com.example.e3646.lifeblabla.object.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

public class AccountEditFragment extends Fragment implements AccountEditContract.View {

    private AccountEditContract.Presenter mPresenter;
    private BottomSheetBehavior mBottomSheetBehavior;

    private ImageButton mAddItemButton;
    private ImageButton mHideButton;
    private ImageButton mDeleteNumberButton;
    private ImageButton mDeleteItemButton;
    private ImageButton mCompleteAccountButton;
    private Button mOneButton;
    private Button mTwoButton;
    private Button mThreeButton;
    private Button mFourButton;
    private Button mFiveButton;
    private Button mSixButton;
    private Button mSevenButton;
    private Button mEightButton;
    private Button mNineButton;
    private Button mZeroButton;

    private ImageButton mCategoryOne;
    private ImageButton mCategoryTwo;
    private ImageButton mCategoryThree;
    private ImageButton mCategoryFour;
    private ImageButton mCategoryFive;
    private ImageButton mRevenue;
    private ImageButton mExpense;

    private Button mCancelButton;
    private Button mCompleteButton;

    private RecyclerView mRecyclerView;
    private AccountAdapter mAccountAdapter;

    private TextView mAmount;

    private String mMoneyAmount = "";

    private String mNoteId;

    private ArrayList<Account> mAccoountList;
    private Account mAccount;

    private boolean isCreatingItem;
    private boolean isRevenue;
    private String mCategory = "0";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_edit, container, false);
        init(view);

        mRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRevenue = true;
                mRevenue.setImageResource(R.drawable.button_account_is_select);
                mExpense.setImageResource(R.drawable.button_account_not_select);
            }
        });

        mExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRevenue = false;
                mRevenue.setImageResource(R.drawable.button_account_not_select);
                mExpense.setImageResource(R.drawable.button_account_is_select);
            }
        });

        mCategoryOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategoryBack(mCategoryOne, "1");
            }
        });
        mCategoryTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategoryBack(mCategoryTwo, "2");
            }
        });
        mCategoryThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategoryBack(mCategoryThree, "3");
            }
        });
        mCategoryFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategoryBack(mCategoryFour, "4");
            }
        });
        mCategoryFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategoryBack(mCategoryFive, "5");
            }
        });

        mAddItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCreatingItem = true;
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                    mDeleteItemButton.setVisibility(View.INVISIBLE);
            }
        });

        mHideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        mOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAmount("1");

            }
        });
        mTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAmount("2");
            }
        });
        mThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAmount("3");
            }
        });
        mFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAmount("4");
            }
        });
        mFiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAmount("5");
            }
        });
        mSixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAmount("6");
            }
        });
        mSevenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAmount("7");
            }
        });
        mEightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAmount("8");
            }
        });
        mNineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAmount("9");
            }
        });
        mZeroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAmount("0");
            }
        });
        mDeleteNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMoneyAmount.length() > 0) {
                    mMoneyAmount = mMoneyAmount.substring(0, mMoneyAmount.length() - 1);
                    mAmount.setText(mMoneyAmount);
                }
            }
        });

        mCompleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeAccountData();
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return view;
    }

    private void init(View view) {


        mBottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet_layout));
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        mDeleteItemButton = view.findViewById(R.id.button_delete);
        mCategoryOne = view.findViewById(R.id.button_category_1);
        mCategoryTwo = view.findViewById(R.id.button_category_2);

        mRevenue = view.findViewById(R.id.button_revenue);
        mExpense = view.findViewById(R.id.button_expense);

        mCategoryTwo = view.findViewById(R.id.button_category_2);
        mCategoryThree = view.findViewById(R.id.button_category_3);
        mCategoryFour = view.findViewById(R.id.button_category_4);
        mCategoryFive = view.findViewById(R.id.button_category_5);

        mDeleteNumberButton = view.findViewById(R.id.button_delete_number);

        mAmount = view.findViewById(R.id.amount);
        mHideButton = (ImageButton)view.findViewById(R.id.button_hide);
        mAddItemButton = view.findViewById(R.id.button_add_item);

        mOneButton = view.findViewById(R.id.button_1);
        mTwoButton = view.findViewById(R.id.button_2);
        mThreeButton = view.findViewById(R.id.button_3);
        mFourButton = view.findViewById(R.id.button_4);
        mFiveButton = view.findViewById(R.id.button_5);
        mSixButton = view.findViewById(R.id.button_6);
        mSevenButton = view.findViewById(R.id.button_7);
        mEightButton = view.findViewById(R.id.button_8);
        mNineButton = view.findViewById(R.id.button_9);
        mZeroButton = view.findViewById(R.id.button_0);

        mRecyclerView = view.findViewById(R.id.account_item_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAccountAdapter = new AccountAdapter(mAccoountList);
        mRecyclerView.setAdapter(mAccountAdapter);
        mCompleteAccountButton = view.findViewById(R.id.button_complete_account);

        mCancelButton = view.findViewById(R.id.button_cancel);
        mCompleteButton = view.findViewById(R.id.button_complete);

        mAccoountList = new ArrayList<Account>();
        mNoteId = currentTimeForID();

    }

    private String currentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:ss:mm");
        Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
        String str = formatter.format(curDate);

        return str;
    }
    private String currentTimeForID() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHssmm");
        Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
        String str = formatter.format(curDate);

        return str;
    }


    @Override
    public void setPresenter(AccountEditContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);

    }

    @Override
    public void setAmount(String num) {
        mMoneyAmount = mMoneyAmount + num;
        mAmount.setText(mMoneyAmount);
    }

    @Override
    public void setCategoryBack(ImageButton imageButton, String i) {

        mCategory = i;

        mCategoryOne.setImageResource(R.drawable.button_account_not_select);
        mCategoryTwo.setImageResource(R.drawable.button_account_not_select);
        mCategoryThree.setImageResource(R.drawable.button_account_not_select);
        mCategoryFour.setImageResource(R.drawable.button_account_not_select);
        mCategoryFive.setImageResource(R.drawable.button_account_not_select);

        imageButton.setImageResource(R.drawable.button_account_is_select);
    }

    @Override
    public void takeAccountData() {
        if (isCreatingItem) {

            mAccount = new Account();
            mAccount.setId(mNoteId);
            mAccount.setAccountId(currentTimeForID());
            mAccount.setNumber("1");
            mAccount.setCreatedTime(currentTime());
            mAccount.setDescription("");
            if (isRevenue == true) {
                mAccount.setRevenue(mMoneyAmount);
                mAccount.setExpense("");

            }

            if(isRevenue == false) {
                mAccount.setRevenue("");
                mAccount.setExpense(mMoneyAmount);
            }

            Log.d("revenue in fragment", "amount: " + mMoneyAmount);

            mAccount.setCategory(mCategory);
            mAccoountList.add(mAccount);
            mAccountAdapter.setAccountList(mAccoountList);
            mAccountAdapter.notifyDataSetChanged();

            Sqldatabase sql = new Sqldatabase(getContext());
            sql.insertAccount(mAccount);

        } else { //isEditing



        }
    }

    @Override
    public void takeNoteData() {

    }
}
