package com.example.e3646.lifeblabla.addnote;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.e3646.lifeblabla.R;

import com.example.e3646.lifeblabla.account.AccountEditFragment;
import com.example.e3646.lifeblabla.account.AccountEditPresenter;
import com.example.e3646.lifeblabla.diary.DiaryEditFragment;
import com.example.e3646.lifeblabla.diary.DiaryEditPresenter;
import com.example.e3646.lifeblabla.diary.DiaryFragment;
import com.example.e3646.lifeblabla.jot.JotEditFragment;
import com.example.e3646.lifeblabla.jot.JotEditPresenter;
import com.example.e3646.lifeblabla.main.MainFragment;

import static com.google.common.base.Preconditions.checkNotNull;

@SuppressLint("ValidFragment")
public class AddNoteFragment extends Fragment implements AddNoteContract.View {

    private AddNoteContract.Presenter mPresenter;

    private Button mNextStepButton;
    private Button mCancelButton;
    private Button mSelectModeButton;
    private ConstraintLayout mAddNoteHeader;
    private String mModeSelection;

    private FragmentManager mFragmentManager;
    private MainFragment mMainFragment;

    private DiaryEditFragment mDiaryEditFragment;
    private DiaryEditPresenter mDiaryEditPresenter;
    private JotEditFragment mJotEditFragment;
    private JotEditPresenter mJotEditPresenter;
    private AccountEditFragment mAccountEditFragment;
    private AccountEditPresenter mAccountEditPresenter;

    private ImageButton mDiaryModeButton;
    private ImageButton mConferenceModeButton;
    private ImageButton mAccountModeButton;
    private ImageButton mListModeButton;


    public AddNoteFragment() {


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addnote, container, false);

        mFragmentManager = getActivity().getSupportFragmentManager();

        mAddNoteHeader = (ConstraintLayout)view.findViewById(R.id.add_note_header);

//        mModeSelectionFragment = new ModeSelectionFragment(this);

        mDiaryModeButton = view.findViewById(R.id.button_mode_diary);
        mDiaryModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.goCreateDiary();

            }
        });

        mConferenceModeButton = view.findViewById(R.id.button_mode_conference);
        mConferenceModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.goCreateConference();

            }
        });
        mAccountModeButton = view.findViewById(R.id.button_mode_account);
        mAccountModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mPresenter.goCreateAccount();

            }
        });

        mListModeButton = view.findViewById(R.id.button_mode_list);
        mListModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.goCreateTodolist();
            }
        });

        mNextStepButton = (Button)view.findViewById(R.id.button_next);
        mNextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getModeSelection();

                if (mModeSelection.equals("日記")) {
                    mPresenter.goCreateDiary();
                } if (mModeSelection.equals("隨手")) {
                    mPresenter.goCreateJot();
                } if (mModeSelection.equals("會議")) {
                    mPresenter.goCreateConference();
                } if (mModeSelection.equals("記帳")) {
                    mPresenter.goCreateAccount();
                } if (mModeSelection.equals("待辦")) {
                    mPresenter.goCreateAccount();
                }

            }
        });

        mCancelButton = (Button)view.findViewById(R.id.button_cancel);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.backToMain();

            }
        });

        mSelectModeButton = (Button)view.findViewById(R.id.button_select_mode);
        mSelectModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                mModeSelectionFragment.show(getFragmentManager(), null);

            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setPresenter(AddNoteContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void goCreateDiary() {
//
//        mDiaryEditFragment = new DiaryEditFragment(true);
//        mDiaryEditPresenter = new DiaryEditPresenter(mDiaryEditFragment, null, null, null,mPresenter, true);
//
//        FragmentTransaction transaction = mFragmentManager.beginTransaction();
//        transaction.replace(R.id.add_note_container, mDiaryEditFragment, "CREATE DIARY")
//                .addToBackStack(null)
//                .commit();

    }

    @Override
    public void goCreateJot() {

        mJotEditFragment = new JotEditFragment();
        mJotEditPresenter = new JotEditPresenter(mJotEditFragment);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.whole_container, mJotEditFragment, "CREATE JOT")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void goCreateAccount() {

        mAccountEditFragment = new AccountEditFragment();
        mAccountEditPresenter = new AccountEditPresenter(mAccountEditFragment);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.whole_container, mAccountEditFragment, "CREATE ACCOUNT")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void backToMain() {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.detach(this)
                .commit();

    }

    @Override
    public void setModeSelection(String modeSelection) {

        mSelectModeButton.setText(modeSelection);

    }

    @Override
    public void getModeSelection() {
        mModeSelection = mSelectModeButton.getText().toString();
    }

    @Override
    public void hideUI() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(AddNoteFragment.this)
                .commit();
    }


}
