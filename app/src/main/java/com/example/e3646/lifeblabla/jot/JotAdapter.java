package com.example.e3646.lifeblabla.jot;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e3646.lifeblabla.R;

import java.util.ArrayList;

public class JotAdapter extends RecyclerView.Adapter {

    private ArrayList<String> mTagList;

    public JotAdapter(ArrayList<String> tagList) {

        mTagList = tagList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new JotAdapter.TagItemViewHolder(LayoutInflater.from(viewGroup
                .getContext()).inflate(R.layout.item_diary_tag, null, false));
    }


    public class TagItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTagText;
        private ImageView mTagBackground;
        private ImageButton mDeleteButton;

        public TagItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mTagText = (TextView)itemView.findViewById(R.id.tag_text);
            mTagBackground = (ImageView)itemView.findViewById(R.id.tag_backgournd);
            mDeleteButton = (ImageButton)itemView.findViewById(R.id.button_delete_tag);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        TagItemViewHolder tagItemViewHolder = (TagItemViewHolder)viewHolder;
        tagItemViewHolder.mTagText.setText(mTagList.get(i));

        ViewGroup.LayoutParams backgorundParams = tagItemViewHolder.mTagBackground.getLayoutParams();
        backgorundParams.width = mTagList.get(i).length() * 60 + 20;
        tagItemViewHolder.mTagBackground.setLayoutParams(backgorundParams);

        tagItemViewHolder.mDeleteButton.setVisibility(View.INVISIBLE);

    }


    @Override
    public int getItemCount() {

        return mTagList.size();
    }

}
