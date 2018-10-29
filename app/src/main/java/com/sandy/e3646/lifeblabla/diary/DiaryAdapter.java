package com.sandy.e3646.lifeblabla.diary;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandy.e3646.lifeblabla.R;

import java.util.ArrayList;

public class DiaryAdapter extends RecyclerView.Adapter {

    private ArrayList<String> mTagList;

    private View.OnClickListener mListener;

    public DiaryAdapter(ArrayList<String> tagList) {

        mTagList = tagList;
        Log.d("tag list", "size: " + tagList.size());
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new DiaryAdapter.TagItemViewHolder(LayoutInflater.from(viewGroup
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
        viewHolder.itemView.setTag(i);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view);

            }
        });

        ViewGroup.LayoutParams backgorundParams = tagItemViewHolder.mTagBackground.getLayoutParams();

        int numofchinese = numOfChinese(mTagList.get(i));

        int numofenglish = mTagList.get(i).length() - numofchinese;
        backgorundParams.width = numofenglish*25 + numofchinese*60 + 20;

        tagItemViewHolder.mTagBackground.setLayoutParams(backgorundParams);

        tagItemViewHolder.mDeleteButton.setVisibility(View.INVISIBLE);

    }


    @Override
    public int getItemCount() {

        return mTagList.size();
    }


    public void setOnItemListener(View.OnClickListener listener) {
        this.mListener = listener;
    }

    private int numOfChinese(String tagText) {
        int numOfCh = 0;

        for (int i = 0; i<tagText.length(); i++) {
            String text = String.valueOf(tagText.charAt(i));

            if (text.matches("[\u4e00-\u9fa5]+")) {
                numOfCh += 1;
            }
        }
        return numOfCh;
    }

}
