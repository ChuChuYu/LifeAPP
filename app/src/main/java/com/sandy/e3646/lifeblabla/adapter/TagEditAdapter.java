package com.sandy.e3646.lifeblabla.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.account.AccountEditFragment;
import com.sandy.e3646.lifeblabla.diary.DiaryEditFragment;
import com.sandy.e3646.lifeblabla.draw.DrawEditFragment;
import com.sandy.e3646.lifeblabla.jot.JotEditFragment;

import java.util.ArrayList;

public class TagEditAdapter extends RecyclerView.Adapter {

    private int TYPE_TAG = 1;
    private int TYPE_EDIT = 2;
    private ArrayList<String> mTagList = new ArrayList<String>();
    private DiaryEditFragment mDiaryEditFragment;

    private JotEditFragment mJotEditFragment;
    private AccountEditFragment mAccountEditFragment;
    private DrawEditFragment mDrawEditFragment;

    public TagEditAdapter(DiaryEditFragment diaryEditFragment, JotEditFragment jotEditFragment, AccountEditFragment accountEditFragment, DrawEditFragment drawEditFragment) {
        mDiaryEditFragment = diaryEditFragment;
        mJotEditFragment = jotEditFragment;
        mAccountEditFragment = accountEditFragment;
        mDrawEditFragment = drawEditFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == TYPE_TAG) {
            RecyclerView.ViewHolder view = new TagItemViewHolder(LayoutInflater.from(viewGroup
                    .getContext()).inflate(R.layout.item_diary_tag, null, false));
            return view;
        } else if (i == TYPE_EDIT) {
            RecyclerView.ViewHolder view = new EditItemViewHolder(LayoutInflater.from(viewGroup
                    .getContext()).inflate(R.layout.item_diary_edit_tag, null, false));
            return view;
        }
        return null;
    }

    public class TagItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTagText;
        private ImageButton mDeleteButton;
        private ImageView mTagBackground;

        public TagItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mTagText = (TextView)itemView.findViewById(R.id.tag_text);
            mDeleteButton = (ImageButton)itemView.findViewById(R.id.button_delete_tag);
            mTagBackground = (ImageView)itemView.findViewById(R.id.tag_backgournd);
        }
    }

    public class EditItemViewHolder extends RecyclerView.ViewHolder {

        private EditText mTagEdit;

        public EditItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mTagEdit = (EditText)itemView.findViewById(R.id.tag_edit);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof EditItemViewHolder) {
            initLayoutEdit((EditItemViewHolder) viewHolder, i);
        } else if (viewHolder instanceof TagItemViewHolder) {
            initLayoutTag((TagItemViewHolder)viewHolder, i);
        }
    }

    private void initLayoutEdit(EditItemViewHolder viewHolder, int i) {

        final EditItemViewHolder editItemViewHolder = (EditItemViewHolder)viewHolder;

        editItemViewHolder.mTagEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (mDiaryEditFragment != null) {
                    if (b) {
                        mDiaryEditFragment.getTagEditFocus();
                    } else {
                        mDiaryEditFragment.getTagEditUnFocus();
                    }
                } else if (mAccountEditFragment != null) {
                    if (b) {
                        mAccountEditFragment.getTagEditFocus();
                    } else {
                        mAccountEditFragment.getTagEditUnFocus();
                    }
                } else if (mJotEditFragment != null) {
                    if (b) {
                        mJotEditFragment.getTagEditFocus();
                    } else {
                        mJotEditFragment.getTagEditUnFocus();
                    }
                } else if (mDrawEditFragment != null) {
                    if (b) {
                        mDrawEditFragment.getTagEditFocus();
                    } else {
                        mDrawEditFragment.getTagEditUnFocus();
                    }
                }
             }
        });

        editItemViewHolder.mTagEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                mTagList.add(editItemViewHolder.mTagEdit.getText().toString());
                editItemViewHolder.mTagEdit.setText("");
                InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                return false;
            }
        });

    }

    private void initLayoutTag(TagItemViewHolder holder, final int i) {
        final TagItemViewHolder tagItemViewHolder = (TagItemViewHolder)holder;

        ViewGroup.LayoutParams backgroundParams = tagItemViewHolder.mTagBackground.getLayoutParams();
        int numofchinese = numOfChinese(mTagList.get(i));
        int numofenglish = mTagList.get(i).length() - numofchinese;
        backgroundParams.width = numofenglish * 25 + numofchinese * 60 + 20;
        tagItemViewHolder.mTagBackground.setLayoutParams(backgroundParams);
        tagItemViewHolder.mTagText.setText(mTagList.get(i));

        tagItemViewHolder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mTagList.size() >= 1) {
                    mTagList.remove(i);
                    notifyItemRemoved(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mTagList != null) {
            return mTagList.size() + 1;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        if (position == getItemCount() - 1) {
            return TYPE_EDIT;
        } else {
            return TYPE_TAG;
        }
    }

    private int numOfChinese(String tagText) {
        int numOfCh = 0;

        for (int i = 0; i < tagText.length(); i++) {
            String text = String.valueOf(tagText.charAt(i));
            if (text.matches("[\u4e00-\u9fa5]+")) {
                numOfCh += 1;
            }
        }
        return numOfCh;
    }

    public ArrayList<String> TagList() {
        return this.mTagList;
    }
}