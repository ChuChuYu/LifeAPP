package com.example.e3646.lifeblabla.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.diary.DiaryAdapter;

public class TitleAdapter extends RecyclerView.Adapter {

    private int open = 0;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TitleAdapter.TitleItemViewHolder(LayoutInflater.from(viewGroup
                .getContext()).inflate(R.layout.item_title_select, null, false));
    }

    public class TitleItemViewHolder extends RecyclerView.ViewHolder {

        private EditText mTitle;
        private ImageButton mExpandButton;
        private LinearLayout mTitleList;

        public TitleItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = (EditText)itemView.findViewById(R.id.jot_title);
            mExpandButton = (ImageButton)itemView.findViewById(R.id.button_expand);
            mTitleList = (LinearLayout)itemView.findViewById(R.id.title_list);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


        final TitleItemViewHolder titleItemViewHolder = (TitleItemViewHolder)viewHolder;

        titleItemViewHolder.mExpandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (open == 0) {
                    titleItemViewHolder.mTitleList.setVisibility(View.VISIBLE);
                    open = 1;
                } else {
                    titleItemViewHolder.mTitleList.setVisibility(View.GONE);
                    open = 0;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
