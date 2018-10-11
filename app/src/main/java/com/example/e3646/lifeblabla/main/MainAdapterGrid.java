package com.example.e3646.lifeblabla.main;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e3646.Sqldatabase;
import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.object.Note;
import com.example.e3646.lifeblabla.object.Tag;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainAdapterGrid extends RecyclerView.Adapter {

    private Context mContext;

    private View.OnClickListener mListener;
    private boolean isListLayout = false;
    private ArrayList<Note> mNoteList;

    private List<Integer> heights = new ArrayList<>();

    public MainAdapterGrid (Context context) {
        this.mContext = context;
        getRandomHeight(30);

        Sqldatabase sql = new Sqldatabase(mContext);
        mNoteList = sql.getNotes();

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
        return new MainAdapterGrid.MainGridItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_grid, null));
    }


    public class MainGridItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView mBackground;
        private ImageView mImage;
        private TextView mTitle;
        private TextView mText;


        public MainGridItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mBackground = (ImageView)itemView.findViewById(R.id.grid_item_background);
            mImage = (ImageView)itemView.findViewById(R.id.grid_item_image);
            mTitle = (TextView)itemView.findViewById(R.id.grid_item_title);
            mText = (TextView)itemView.findViewById(R.id.note_text);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        MainGridItemViewHolder mainGridItemViewHolder = (MainGridItemViewHolder)viewHolder;
        ViewGroup.LayoutParams layoutParams = mainGridItemViewHolder.mImage.getLayoutParams();
        layoutParams.height = heights.get(i);
        mainGridItemViewHolder.mImage.setLayoutParams(layoutParams);

        ViewGroup.LayoutParams backgroundParams = mainGridItemViewHolder.mBackground.getLayoutParams();
        backgroundParams.height = heights.get(i) + 240;
        mainGridItemViewHolder.mBackground.setLayoutParams(backgroundParams);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view);
            }
        });

        mainGridItemViewHolder.mTitle.setText(mNoteList.get(mNoteList.size()-i-1).getmTitle());
        mainGridItemViewHolder.mText.setText(mNoteList.get(mNoteList.size()-i-1).getmText());

    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setOnItemListener(View.OnClickListener listener) {
        this.mListener = listener;
    }
}
