package com.example.e3646.lifeblabla.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.object.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private View.OnClickListener mListener;
    private List<Integer> heights = new ArrayList<>();
    private ArrayList<Note> mNoteList;

    public MainAdapter(Context context, ArrayList<Note> noteList) {
        this.mContext = context;
        this.mNoteList = noteList;
        getRandomHeight(30);

    }

    private void getRandomHeight(int n){
        heights = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            heights.add((int)( 400 + Math.random() * 400));
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new MainListItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_list, null));

    }

    public class MainListItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mCreatedTime;
        private TextView mTitle;

        public MainListItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mCreatedTime = (TextView)itemView.findViewById(R.id.note_created_time);
            mTitle = (TextView)itemView.findViewById(R.id.note_title);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        MainListItemViewHolder mainListItemViewHolder = (MainListItemViewHolder)viewHolder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view);
            }
        });

        if (mNoteList != null && mNoteList.get(i) != null) {

            ((MainListItemViewHolder) viewHolder).mCreatedTime.setText(mNoteList.get(i).getmCreatedTime());
            ((MainListItemViewHolder) viewHolder).mTitle.setText(mNoteList.get(i).getmTitle());

        }
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setOnItemListener(View.OnClickListener listener) {
        this.mListener = listener;
    }

}
