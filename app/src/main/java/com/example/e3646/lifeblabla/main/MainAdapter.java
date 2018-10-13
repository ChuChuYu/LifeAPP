package com.example.e3646.lifeblabla.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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

import com.example.e3646.Sqldatabase;
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

        return new MainListItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_list, null));

    }

    public class MainListItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mText;
        private ImageView mType;
        private ImageView mImage;
        private ImageView mTagBackground;
        private TextView mTagText;

        public MainListItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = (TextView)itemView.findViewById(R.id.note_title);
            mText = (TextView)itemView.findViewById(R.id.list_item_text);
            mType = (ImageView)itemView.findViewById(R.id.list_item_type);
            mImage = (ImageView)itemView.findViewById(R.id.list_item_image);
            mTagBackground = (ImageView)itemView.findViewById(R.id.main_tag_backgroung);
            mTagText = (TextView)itemView.findViewById(R.id.tag_text);
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

        if (mNoteList.get(mNoteList.size()-i-1).getmTag()!= null && !mNoteList.get(mNoteList.size()-i-1).getmTag().get(0).equals("") && !mNoteList.get(mNoteList.size()-i-1).getmTag().get(0).equals("null")) {
            mainListItemViewHolder.mTagText.setText(mNoteList.get(mNoteList.size()-i-1).getmTag().get(0));
            ViewGroup.LayoutParams backgorundParams = mainListItemViewHolder.mTagBackground.getLayoutParams();
            backgorundParams.width = mNoteList.get(mNoteList.size()-i-1).getmTag().get(0).length() * 30 + 20;
            mainListItemViewHolder.mTagBackground.setLayoutParams(backgorundParams);
        } else {
            mainListItemViewHolder.mTagText.setVisibility(View.INVISIBLE);
            mainListItemViewHolder.mTagBackground.setVisibility(View.INVISIBLE);
        }


        if (mNoteList != null && mNoteList.get(mNoteList.size()-i-1) != null) {

            viewHolder.itemView.setTag(mNoteList.size()-i-1);

            mainListItemViewHolder.mTitle.setText(mNoteList.get(mNoteList.size()-i-1).getmTitle());
            mainListItemViewHolder.mText.setText(mNoteList.get(mNoteList.size()-i-1).getmText());

            if (mNoteList.get(mNoteList.size()-i-1).getmClassification().equals("diary")) {
                mainListItemViewHolder.mType.setImageResource(R.drawable.note_tag_diary);
            } else if (mNoteList.get(mNoteList.size()-i-1).getmClassification().equals("account")) {
                mainListItemViewHolder.mType.setImageResource(R.drawable.note_tag_account);
            } else if (mNoteList.get(mNoteList.size()-i-1).getmClassification().equals("conference")) {
                mainListItemViewHolder.mType.setImageResource(R.drawable.note_tag_conference);
            } else if (mNoteList.get(mNoteList.size()-i-1).getmClassification().equals("jot")) {
                mainListItemViewHolder.mType.setImageResource(R.drawable.note_tag_jot);
            }

            if (mNoteList.get(mNoteList.size()-i-1).getmPicture() != null && !mNoteList.get(mNoteList.size()-i-1).getmPicture().equals("")) {
                Bitmap bitmap = BitmapFactory.decodeFile(mNoteList.get(mNoteList.size()-i-1).getmPicture());
                mainListItemViewHolder.mImage.setImageBitmap(bitmap);
            } else {
                if (mNoteList.get(mNoteList.size()-i-1).getmClassification().equals("diary")) {
                    mainListItemViewHolder.mImage.setImageResource(R.drawable.image_diary);
                } else if (mNoteList.get(mNoteList.size()-i-1).getmClassification().equals("jot")) {
                    mainListItemViewHolder.mImage.setImageResource(R.drawable.image_jot);
                }

            }

        }

    }

    @Override
    public int getItemCount() {
        return  mNoteList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setOnItemListener(View.OnClickListener listener) {
        this.mListener = listener;
    }

}
