package com.sandy.e3646.lifeblabla.account;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sandy.e3646.Sqldatabase;
import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.adapter.TagEditAdapter;
import com.sandy.e3646.lifeblabla.object.Account;
import com.sandy.e3646.lifeblabla.object.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

@SuppressLint("ValidFragment")
public class AccountEditFragment extends Fragment implements AccountEditContract.View, View.OnClickListener {

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
    private TagEditAdapter mTagAdapter;
    private ArrayList<Account> mAccoountList;
    private ArrayList<Note> mNoteList;
    private Account mAccount;
    private Note mNote;
    private String mMoneyAmount = ""; //set到mAmount
    private String mNoteId;
    private boolean isCreating;
    private boolean isCreatingItem;
    private int isRevenue = 3;
    private int isEditingRevenue = 3;
    private String mCategory = "0";
    private Integer mTotalRevenue = 0;
    private Integer mTotalExpense = 0;
    private Integer mTotalBalance = 0;
    private ConstraintLayout mBottomBar;

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

                if (isCreatingItem) {
                    isRevenue = 1;
                } else {
                    isEditingRevenue = 1;
                }
                mRevenue.setImageResource(R.drawable.button_account_is_select);
                mExpense.setImageResource(R.drawable.button_account_not_select);

            }
        });

        mExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCreatingItem) {
                    isRevenue = 0;
                } else {
                    isEditingRevenue = 0;
                }

                mRevenue.setImageResource(R.drawable.button_account_not_select);
                mExpense.setImageResource(R.drawable.button_account_is_select);

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

                        countTotalAmountWhenEditingItem();
                        takeAccountDataWhenEditing();
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
                    hideUi();
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

                ///要把收入或支出存下來


                Sqldatabase sql = new Sqldatabase(getContext());
                mAccoountList = sql.getAccounts(mNoteId);
                setAccountDatainDialog(mAccoountList.get((int)view.getTag()));
                mAccount = mAccoountList.get((int)view.getTag());

                if (mAccount.getRevenue() != null && !mAccount.getRevenue().equals("") && !mAccount.getRevenue().equals("0")) {
                    isRevenue = 1;
                    isEditingRevenue = 1;
                } else if (mAccount.getExpense() != null && !mAccount.getExpense().equals("") && !mAccount.getExpense().equals("0")) {
                    isRevenue = 0;
                    isEditingRevenue = 0;
                }
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
                        hideUi();
                    }
                    return false;
                }
                return false;
            }
        });
    }

    private void takeAccountDataWhenEditing() {

        mAccount.setCategory(mCategory);
        if (isEditingRevenue == 1) {
            mAccount.setRevenue(mMoneyAmount);
            mAccount.setExpense("");

        }
        if (isEditingRevenue == 0) {
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

        mRevenue = view.findViewById(R.id.button_revenue);
        mExpense = view.findViewById(R.id.button_expense);

        mCategoryOne = view.findViewById(R.id.button_category_1);
        mCategoryTwo = view.findViewById(R.id.button_category_2);
        mCategoryThree = view.findViewById(R.id.button_category_3);
        mCategoryFour = view.findViewById(R.id.button_category_4);
        mCategoryFive = view.findViewById(R.id.button_category_5);

        mCategoryOne.setOnClickListener(this);
        mCategoryTwo.setOnClickListener(this);
        mCategoryThree.setOnClickListener(this);
        mCategoryFour.setOnClickListener(this);
        mCategoryFive.setOnClickListener(this);

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

        mOneButton.setOnClickListener(this);
        mTwoButton.setOnClickListener(this);
        mThreeButton.setOnClickListener(this);
        mFourButton.setOnClickListener(this);
        mFiveButton.setOnClickListener(this);
        mSixButton.setOnClickListener(this);
        mSevenButton.setOnClickListener(this);
        mEightButton.setOnClickListener(this);
        mNineButton.setOnClickListener(this);
        mZeroButton.setOnClickListener(this);

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
            mNoteId = currentTimeForId();

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
        mTagAdapter = new TagEditAdapter(null, null, this, null);
        mTagRecyclerview.setAdapter(mTagAdapter);

        mBottomBar = view.findViewById(R.id.bottom_bar);
    }

    private static String currentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date curDate = new Date(System.currentTimeMillis()); // 獲取當前時間
        String str = formatter.format(curDate);

        return str;
    }


    @Override
    public void getTagEditFocus() {

        mBottomBar.setFitsSystemWindows(true);
    }

    @Override
    public void getTagEditUnFocus() {

        mBottomBar.setFitsSystemWindows(false);

    }

    private String currentTimeForId() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHssmm");
        Date curDate = new Date(System.currentTimeMillis()); // 獲取當前時間
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

            mAccount.setAccountId(currentTimeForId());

            mAccount.setNumber("1");
            mAccount.setCreatedTime(currentTime());
            mAccount.setDescription("");
            if (isRevenue == 1) {
                mAccount.setRevenue(mMoneyAmount);
                mAccount.setExpense("");
            }

            if (isRevenue == 0) {
                mAccount.setRevenue("");
                mAccount.setExpense(mMoneyAmount);
            }

            mAccount.setCategory(mCategory);
            mAccoountList.add(mAccount);
            mAccountAdapter.setAccountList(mAccoountList);
            mAccountAdapter.notifyDataSetChanged();

            Sqldatabase sql = new Sqldatabase(getContext());
            sql.insertAccount(mAccount);

        }

        countTotalAmount();

    }

    @Override
    public void takeNoteData() {

        if (isCreating) {
            mNote = new Note();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat formatterForMonth = new SimpleDateFormat("MM");
            SimpleDateFormat formatterForDay = new SimpleDateFormat("dd");
            SimpleDateFormat formatterForTime = new SimpleDateFormat("HH:mm");
            SimpleDateFormat formatterForWeek = new SimpleDateFormat("EEEE");
            SimpleDateFormat formateForId = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat formateForDaytime = new SimpleDateFormat("HH");
            Date curDate = new Date(System.currentTimeMillis());
            String currentTime = formatter.format(curDate);
            String month = formatterForMonth.format(curDate);
            String day = formatterForDay.format(curDate);
            String time = formatterForTime.format(curDate);
            String week = formatterForWeek.format(curDate);
            String id = formateForId.format(curDate);
            String daytime = formateForDaytime.format(curDate);
            
            if (Integer.parseInt(daytime) > 12) {
                mNote.setDayTime("下午");
            } else {
                mNote.setDayTime("上午");
            }

            mNote.setmCreatedTime(currentTime);

            mNote.setmId(mNoteId);

            mNote.setmTitle(mTitle.getText().toString());

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
            } else {
                Toast.makeText(getContext(), "no week day", Toast.LENGTH_SHORT).show();
            }

            mNote.setmUpdatedTime("");
            mNote.setmPlace("市政府");
            mNote.setClassification("account");

            mNote.setAccountRevenue(String.valueOf(mTotalRevenue));
            mNote.setAccountExpense(String.valueOf(mTotalExpense));
            mNote.setAccountBalance(String.valueOf(mTotalBalance));

            mNote.setmTag(mTagAdapter.TagList());
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
        isRevenue = 3;
        mCategory = "0";

    }

    @Override
    public void hideUi() {
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
        } else if (account.getCategory().equals("5")) {
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
        } else { //如果是刪除
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

    @Override
    public String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date curDate = new Date(System.currentTimeMillis()); // 獲取當前時間
        String str = formatter.format(curDate);
        return str;
    }

    @Override
    public void replaceUI() {

    }

    public void countTotalAmountWhenEditingItem() {

        if (isRevenue == 1) { // 原本是收入
            if (isEditingRevenue == 1) { //收入改收入
                if (mAccount.getRevenue().equals("")) {
                    mTotalRevenue = mTotalRevenue - Integer.parseInt("0") + Integer.parseInt(mMoneyAmount);
                    if (mAccount.getExpense().equals("")) {
                        mTotalExpense = mTotalExpense - Integer.parseInt("0") + Integer.parseInt(mMoneyAmount);
                    }
                } else {
                    mTotalExpense = mTotalExpense - Integer.parseInt(mAccount.getExpense()) + Integer.parseInt(mMoneyAmount);
                }
            }
            if (isEditingRevenue == 0) { //收入改支出
                mTotalExpense = mTotalExpense + Integer.parseInt(mMoneyAmount);
                mTotalRevenue = mTotalRevenue - Integer.parseInt(mAccount.getRevenue());
            }
        }

        String expense = mAccount.getExpense();
        if (isRevenue == 0) { //原本是支出
            if (isEditingRevenue == 1) { //支出改收入
                mTotalRevenue = mTotalRevenue + Integer.parseInt(mMoneyAmount);
                mTotalExpense = mTotalExpense - Integer.parseInt(expense.toString());
            }
            if (isEditingRevenue == 0) { // 支出改支出
                if (mAccount.getExpense().equals("")) {
                    mTotalExpense = mTotalExpense - Integer.parseInt("0") + Integer.parseInt(mMoneyAmount);
                    if (mAccount.getRevenue().equals("")) {
                        mTotalExpense = mTotalExpense - Integer.parseInt("0") + Integer.parseInt(mMoneyAmount);
                    }
                } else {
                    mTotalExpense = mTotalExpense - Integer.parseInt(mAccount.getExpense()) + Integer.parseInt(mMoneyAmount);
                }
            }
        }
        mTotalBalance = mTotalRevenue - mTotalExpense;
        mRevenueText.setText(String.valueOf(mTotalRevenue));
        mExpenseText.setText(String.valueOf(mTotalExpense));
        mBalanceText.setText(String.valueOf(mTotalBalance));
    }

    @Override
    public void onClick(View view) {
                    switch (view.getId()) {

                case R.id.button_category_1:
                    setCategoryBack(mCategoryOne, "1");
                    break;
                case R.id.button_category_2:
                    setCategoryBack(mCategoryTwo, "2");
                    break;
                case R.id.button_category_3:
                    setCategoryBack(mCategoryThree, "3");
                    break;
                case R.id.button_category_4:
                    setCategoryBack(mCategoryFour, "4");
                    break;
                case R.id.button_category_5:
                    setCategoryBack(mCategoryFive, "5");
                    break;

                case R.id.button_0:
                    setAmount("0");
                    break;
                case R.id.button_1:
                    setAmount("1");
                    break;
                case R.id.button_2:
                    setAmount("2");
                    break;
                case R.id.button_3:
                    setAmount("3");
                    break;
                case R.id.button_4:
                    setAmount("4");
                    break;
                case R.id.button_5:
                    setAmount("5");
                    break;
                case R.id.button_6:
                    setAmount("6");
                    break;
                case R.id.button_7:
                    setAmount("7");
                    break;
                case R.id.button_8:
                    setAmount("8");
                    break;
                case R.id.button_9:
                    setAmount("9");
                    break;
                }
    }
}
