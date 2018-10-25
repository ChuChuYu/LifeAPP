package com.example.e3646.lifeblabla.account;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e3646.Sqldatabase;
import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.diary.DiaryEditAdapter;
import com.example.e3646.lifeblabla.diary.DiaryEditFragment;
import com.example.e3646.lifeblabla.object.Account;
import com.example.e3646.lifeblabla.object.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

@SuppressLint("ValidFragment")
public class AccountEditFragment extends Fragment implements AccountEditContract.View {

    private AccountEditContract.Presenter mPresenter;

    private BottomSheetBehavior mBottomSheetBehavior;
    private ImageButton mAddItemButton;
    private ImageButton mHideButton;
    private ImageButton mDeleteNumberButton;
    private ImageButton mDeleteItemButton;
    private ImageButton mCompleteAccountButton;
    private ImageButton mCategoryOne;
    private ImageButton mCategoryTwo;
    private ImageButton mCategoryThree;
    private ImageButton mCategoryFour;
    private ImageButton mCategoryFive;
    private ImageButton mRevenue;
    private ImageButton mExpense;
    private Button mClearButton;
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
    private Button mCancelButton;
    private Button mCompleteButton;
    private TextView mAmount;
    private TextView mRevenueText;
    private TextView mExpenseText;
    private TextView mBalanceText;
    private TextView mCreatedTime;
    private EditText mTitle;

    private RecyclerView mRecyclerView;
    private AccountAdapter mAccountAdapter;
    private RecyclerView mTagRecyclerview;
    private DiaryEditAdapter mTagAdapter;

    private ArrayList<Account> mAccoountList;
    private ArrayList<Note> mNoteList;
    private Account mAccount;
    private Note mNote;

    private String mMoneyAmount = "";
    private String mNoteId;
    private boolean isCreating;
    private boolean isCreatingItem;
    private int isRevenue = 3;
    private String mCategory = "0";
    private Integer mTotalRevenue = 0;
    private Integer mTotalExpense = 0;
    private Integer mTotalBalance = 0;


    public AccountEditFragment(boolean iscreating, Note note) {
        isCreating = iscreating;
        mNote = note;

        if (!isCreating) {
            mTotalRevenue = Integer.parseInt(mNote.getAccountRevenue());
            mTotalExpense = Integer.parseInt(mNote.getAccountExpense());
            mTotalBalance = Integer.parseInt(mNote.getAccountBalance());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_edit, container, false);
        init(view);

        mRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRevenue = 1;
                mRevenue.setImageResource(R.drawable.button_account_is_select);
                mExpense.setImageResource(R.drawable.button_account_not_select);
            }
        });

        mExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRevenue = 0;
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
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAmount.setText("0");
                mMoneyAmount = "";

            }
        });

        mCompleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isRevenue == 3 || mCategory.equals("0") || mMoneyAmount.equals("")) {
                    Toast.makeText(getContext(), "Please set content.", Toast.LENGTH_SHORT).show();
                } else {
                    if (isCreatingItem) {
                        takeAccountData();
                    } else {
                        takeAccountDataWhenEditing();
                        countTotalAmountWhenEditingItem();
                    }
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    clearAddItemButtonsheet();
                }
                mMoneyAmount = "";
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isCreating) {
                    mPresenter.cancelEditing();
                } else {
                    hideUI();
                }

            }
        });

        mCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isCreating) {
                    mPresenter.completeCreating();

                } else {

                    takeNoteData();
                    mPresenter.completeEditing(mNote);

                }
            }
        });

        mAccountAdapter.setOnItemListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCreatingItem = false;

                Sqldatabase sql = new Sqldatabase(getContext());
                mAccoountList = sql.getAccounts(mNoteId);
                setAccountDatainDialog(mAccoountList.get((int)view.getTag()));
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });

        mDeleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sqldatabase sql = new Sqldatabase(getContext());
                sql.deleteAccount(mAccount.getAccountId());

                mAccoountList = sql.getAccounts(mNoteId);
                mAccountAdapter.setAccountList(mAccoountList);
                mAccountAdapter.notifyDataSetChanged();
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                countTotalAmount();

            }
        });

        mCreatedTime = view.findViewById(R.id.created_time);

        if (!isCreating && mNote != null) {

            mCreatedTime.setText(mNote.getmCreatedTime());
        } else {

            mCreatedTime.setText(currentTime());
        }

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

                    if (isCreating) {
                        mPresenter.cancelEditing();
                    } else {
                        hideUI();
                    }
                    return false;
                }

                return false;
            }


        });
    }

    private void takeAccountDataWhenEditing() {

        mAccount.setCategory(mCategory);
        if (isRevenue == 1) {
            mAccount.setRevenue(mMoneyAmount);
            mAccount.setExpense("");

        }
        if (isRevenue == 0) {
            mAccount.setRevenue("");
            mAccount.setExpense(mMoneyAmount);
        }


        Sqldatabase sql = new Sqldatabase(getContext());
        sql.updateAccount(mNoteId, mAccount.getAccountId(), mAccount);

        mAccoountList = sql.getAccounts(mNoteId);
        mAccountAdapter.setAccountList(mAccoountList);
        mAccountAdapter.notifyDataSetChanged();

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
        mClearButton = view.findViewById(R.id.button_clear);
        mDeleteItemButton = view.findViewById(R.id.button_delete_item);

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
        mTitle = view.findViewById(R.id.account_title);
        mRecyclerView = view.findViewById(R.id.account_item_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (isCreating == false) {
            Sqldatabase sql = new Sqldatabase(getContext());
            mAccoountList = sql.getAccounts(mNote.getmId());
        }
        mAccountAdapter = new AccountAdapter(mAccoountList, true);
        mRecyclerView.setAdapter(mAccountAdapter);

        mCompleteAccountButton = view.findViewById(R.id.button_complete_account);

        mCancelButton = view.findViewById(R.id.button_cancel);
        mCompleteButton = view.findViewById(R.id.button_complete);

        mRevenueText = view.findViewById(R.id.account_revenue);
        mExpenseText = view.findViewById(R.id.account_expense);
        mBalanceText = view.findViewById(R.id.account_balance);


        if (isCreating) {
            mAccoountList = new ArrayList<Account>();
            mNoteId = currentTimeForID();

        } else {
            mNoteId = mNote.getmId();
            mTitle.setText(mNote.getmTitle());
            mRevenueText.setText(mNote.getAccountRevenue());
            mExpenseText.setText(mNote.getAccountExpense());
            mBalanceText.setText(mNote.getAccountBalance());

        }


        mTagRecyclerview = (RecyclerView)view.findViewById(R.id.tag_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mTagRecyclerview.setLayoutManager(linearLayoutManager);
        mTagAdapter = new DiaryEditAdapter(null);
        mTagRecyclerview.setAdapter(mTagAdapter);
    }

    private String currentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
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
            if (isRevenue == 1) {
                mAccount.setRevenue(mMoneyAmount);
                mAccount.setExpense("");

            }

            if(isRevenue == 0) {
                mAccount.setRevenue("");
                mAccount.setExpense(mMoneyAmount);
            }

            mAccount.setCategory(mCategory);
            mAccoountList.add(mAccount);
            mAccountAdapter.setAccountList(mAccoountList);
            mAccountAdapter.notifyDataSetChanged();

            Sqldatabase sql = new Sqldatabase(getContext());
            sql.insertAccount(mAccount);

        } else {

        }
        countTotalAmount();

    }

    @Override
    public void takeNoteData() {

        if (isCreating) {
//            mPresenter.setContext(mContext);
            mNote = new Note();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat formatterForMonth = new SimpleDateFormat("MM");
            SimpleDateFormat formatterForDay = new SimpleDateFormat("dd");
            SimpleDateFormat formatterForTime = new SimpleDateFormat("HH:mm");
            SimpleDateFormat formatterForWeek = new SimpleDateFormat("EEEE");
            SimpleDateFormat formateForID = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat formateForDaytime = new SimpleDateFormat("HH");
            Date curDate = new Date(System.currentTimeMillis());
            String currentTime = formatter.format(curDate);
            String month = formatterForMonth.format(curDate);
            String day = formatterForDay.format(curDate);
            String time = formatterForTime.format(curDate);
            String week = formatterForWeek.format(curDate);
            String id = formateForID.format(curDate);
            String daytime = formateForDaytime.format(curDate);

            if (Integer.parseInt(daytime) > 12 ) {
                mNote.setDayTime("下午");
            } else {
                mNote.setDayTime("上午");
            }

            mNote.setmCreatedTime(currentTime);

            mNote.setmId(mNoteId);

            mNote.setMonth(month);
            mNote.setDay(day);
            mNote.setTime(time);
            if (week.equals("Monday")) {
                mNote.setWeek("MON");
            } else if (week.equals("Tuesday")) {
                mNote.setWeek("TUE");
            } else if (week.equals("Wednesday")) {
                mNote.setWeek("WED");
            } else if (week.equals("Thursday")) {
                mNote.setWeek("THUR");
            } else if (week.equals("Friday")) {
                mNote.setWeek("FRI");
            } else if (week.equals("Saturday")) {
                mNote.setWeek("SAT");
            } else if (week.equals("Sunday")) {
                mNote.setWeek("SUN");
            }

            mNote.setmUpdatedTime("");
            mNote.setmPlace("市政府");
            mNote.setClassification("account");

            mNote.setAccountRevenue(String.valueOf(mTotalRevenue));
            mNote.setAccountExpense(String.valueOf(mTotalExpense));
            mNote.setAccountBalance(String.valueOf(mTotalBalance));

            mNote.setmTag(mTagAdapter.TagList());
            Log.d("tag", "list size: " + mTagAdapter.TagList().size() );

            mNoteList = new ArrayList<Note>();
            mNoteList.add(mNote);

            mPresenter.saveNoteData(getContext(), mNote);
        } else { //isEditing

            mNote.setmTitle(mTitle.getText().toString());

            Sqldatabase sql = new Sqldatabase(getContext());
            sql.updateNotes(mNote.getmId(), mNote);
        }
    }

    @Override
    public void clearAddItemButtonsheet() {

        mRevenue.setImageResource(R.drawable.button_account_not_select);
        mExpense.setImageResource(R.drawable.button_account_not_select);
        mCategoryOne.setImageResource(R.drawable.button_account_not_select);
        mCategoryTwo.setImageResource(R.drawable.button_account_not_select);
        mCategoryThree.setImageResource(R.drawable.button_account_not_select);
        mCategoryFour.setImageResource(R.drawable.button_account_not_select);
        mCategoryFive.setImageResource(R.drawable.button_account_not_select);
        mAmount.setText("0");

    }

    @Override
    public void hideUI() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(AccountEditFragment.this)
                .commit();
    }

    @Override
    public void setAccountDatainDialog(Account account) {

        mAccount = account;
        if (account.getExpense() != null) {
            mExpense.setImageResource(R.drawable.button_account_is_select);
            mRevenue.setImageResource(R.drawable.button_account_not_select);
            mAmount.setText(account.getExpense());
            mMoneyAmount = account.getExpense();
        } else {
            mExpense.setImageResource(R.drawable.button_account_not_select);
            mRevenue.setImageResource(R.drawable.button_account_is_select);
            mAmount.setText(account.getRevenue());
            mMoneyAmount = account.getRevenue();
        }

        if (account.getCategory().equals("1")) {
            setCategoryBack(mCategoryOne, "1");
            mCategory = "1";
        } else if (account.getCategory().equals("2")) {
            setCategoryBack(mCategoryTwo, "2");
            mCategory = "2";
        } else if (account.getCategory().equals("3")) {
            setCategoryBack(mCategoryThree, "3");
            mCategory = "3";
        } else if (account.getCategory().equals("4")) {
            setCategoryBack(mCategoryFour, "4");
            mCategory = "4";
        }else if (account.getCategory().equals("5")) {
            setCategoryBack(mCategoryFour, "5");
            mCategory = "5";
        }

    }

    @Override
    public void countTotalAmount() {

        if (isCreatingItem) {

            if (isRevenue == 1) {
                mTotalRevenue = mTotalRevenue + Integer.parseInt(mMoneyAmount);
            } else if (isRevenue == 0) {
                mTotalExpense = mTotalExpense + Integer.parseInt(mMoneyAmount);
            }

            mTotalBalance = mTotalRevenue - mTotalExpense;

        } else {

            if (isRevenue == 1) {
                mTotalRevenue = mTotalRevenue - Integer.parseInt(mMoneyAmount);
            } else if (isRevenue == 0) {
                mTotalExpense = mTotalExpense - Integer.parseInt(mMoneyAmount);
            }

            mTotalBalance = mTotalRevenue - mTotalExpense;

        }

        mRevenueText.setText(String.valueOf(mTotalRevenue));
        mExpenseText.setText(String.valueOf(mTotalExpense));
        mBalanceText.setText(String.valueOf(mTotalBalance));

    }

    public void countTotalAmountWhenEditingItem() {

        if (isRevenue == 1) {
            mTotalRevenue = mTotalRevenue - Integer.parseInt(mAccount.getRevenue()) + Integer.parseInt(mMoneyAmount);

        }
        if (isRevenue == 0) {
            mTotalRevenue = mTotalExpense - Integer.parseInt(mAccount.getExpense()) + Integer.parseInt(mMoneyAmount);
        }

        mTotalBalance = mTotalRevenue - mTotalExpense;
        mRevenueText.setText(String.valueOf(mTotalRevenue));
        mExpenseText.setText(String.valueOf(mTotalExpense));
        mBalanceText.setText(String.valueOf(mTotalBalance));
    }

    public void countTotalAmountWhenDeletingItem() {

    }
}
