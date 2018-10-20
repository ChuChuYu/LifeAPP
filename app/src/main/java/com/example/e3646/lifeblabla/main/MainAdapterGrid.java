package com.example.e3646.lifeblabla.main;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.support.annotation.NonNull;
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

public class MainAdapterGrid extends RecyclerView.Adapter {

    private Context mContext;
    private int TYPE_DIARY = 1;
    private int TYPE_ACCOUNT = 2;

    private View.OnClickListener mListener;
    private boolean isListLayout = false;
    private ArrayList<Note> mNoteList;

    private List<Integer> heights = new ArrayList<>();

    public MainAdapterGrid (Context context) {
        this.mContext = context;
        getRandomHeight(30);

        Sqldatabase sql = new Sqldatabase(mContext);
        mNoteList = sql.getNotes();
        Log.d("note", "size" + mNoteList.size());

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

        if (getItemViewType(i) == TYPE_DIARY) {
            return new MainAdapterGrid.MainGridItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_grid_diary, null));

        } else if (getItemViewType(i) == TYPE_ACCOUNT) {
            return new MainAdapterGrid.MainGridItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_grid_account, null));
        } else {
            return null;
        }
    }

    public class MainGridItemViewHolder extends RecyclerView.ViewHolder {

//        private ImageView mBackground;
//        private ImageView mImage;
//        private TextView mTitle;
//        private TextView mText;

        private ImageView mImage;
        private ImageView mImageBack;

        private ImageView mDiaryEmotion;
        private ImageView mDiaryWeather;
        private ImageView mTagBackground;
        private ImageView mTypeBackground;

        private TextView mTag;
        private TextView mType;
        private TextView mText;
        private TextView mDate;
        private TextView mDayTime;
        private TextView mTime;

        private CardView mCardView;



        public MainGridItemViewHolder(@NonNull View itemView) {
            super(itemView);

//            mBackground = (ImageView)itemView.findViewById(R.id.grid_item_background);
//            mImage = (ImageView)itemView.findViewById(R.id.grid_item_image);
//            mTitle = (TextView)itemView.findViewById(R.id.grid_item_title);
//            mText = (TextView)itemView.findViewById(R.id.note_text);

            mImage = itemView.findViewById(R.id.note_image);
            mImageBack = itemView.findViewById(R.id.note_image_back);
            mDiaryEmotion = itemView.findViewById(R.id.note_diary_emotion);
            mDiaryWeather = itemView.findViewById(R.id.note_diary_weather);
            mTagBackground = itemView.findViewById(R.id.note_tag_background);
            mTypeBackground = itemView.findViewById(R.id.note_type_background);
            mTag = itemView.findViewById(R.id.note_tag);
            mType = itemView.findViewById(R.id.note_type);
            mText = itemView.findViewById(R.id.note_text);
            mDate = itemView.findViewById(R.id.note_week);
            mDayTime = itemView.findViewById(R.id.note_daytime);
            mTime = itemView.findViewById(R.id.note_time);

            mCardView = itemView.findViewById(R.id.cardView);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        MainGridItemViewHolder mainGridItemViewHolder = (MainGridItemViewHolder)viewHolder;

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view);
            }
        });

        if (mNoteList != null && mNoteList.get(mNoteList.size()-i-1) != null) {
            if (mNoteList.get(mNoteList.size()-i-1).getmClassification().equals("diary")) {

                if (mNoteList.get(mNoteList.size() - i - 1).getmMind() != null) {
                    if (mNoteList.get(mNoteList.size() - i - 1).getmMind().equals("1")) {
                        mainGridItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.button_emotion);
                    } else if (mNoteList.get(mNoteList.size() - i - 1).getmMind().equals("2")) {
                        mainGridItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.emotion_2);
                    } else if (mNoteList.get(mNoteList.size() - i - 1).getmMind().equals("3")) {
                        mainGridItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.emotion_3);
                    } else if (mNoteList.get(mNoteList.size() - i - 1).getmMind().equals("4")) {
                        mainGridItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.emotion_4);
                    } else if (mNoteList.get(mNoteList.size() - i - 1).getmMind().equals("5")) {
                        mainGridItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.emotion_5);
                    } else if (mNoteList.get(mNoteList.size() - i - 1).getmMind().equals("6")) {
                        mainGridItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.emotion_6);
                    }
                }

                if (mNoteList.get(mNoteList.size()-i-1).getmWeather() != null) {
                    if (mNoteList.get(mNoteList.size()-i-1).getmWeather().equals("1")) {
                        mainGridItemViewHolder.mDiaryWeather.setImageResource(R.drawable.weather_1);
                    } else if (mNoteList.get(mNoteList.size()-i-1).getmWeather().equals("2")) {
                        mainGridItemViewHolder.mDiaryWeather.setImageResource(R.drawable.button_weather);
                    } else if (mNoteList.get(mNoteList.size()-i-1).getmWeather().equals("3")) {
                        mainGridItemViewHolder.mDiaryWeather.setImageResource(R.drawable.weather_3);
                    } else if (mNoteList.get(mNoteList.size()-i-1).getmWeather().equals("4")) {
                        mainGridItemViewHolder.mDiaryWeather.setImageResource(R.drawable.weather_4);
                    } else if (mNoteList.get(mNoteList.size()-i-1).getmWeather().equals("5")) {
                        mainGridItemViewHolder.mDiaryWeather.setImageResource(R.drawable.weather_5);
                    } else if (mNoteList.get(mNoteList.size()-i-1).getmWeather().equals("6")) {
                        mainGridItemViewHolder.mDiaryWeather.setImageResource(R.drawable.weather_6);
                    }

                }

            } else if (mNoteList.get(mNoteList.size()-i-1).getmClassification().equals("jot")) {

                mainGridItemViewHolder.mDiaryEmotion.setVisibility(View.INVISIBLE);
                mainGridItemViewHolder.mDiaryWeather.setVisibility(View.INVISIBLE);
                mainGridItemViewHolder.mTypeBackground.setImageResource(R.drawable.background_tag_jot);
                mainGridItemViewHolder.mType.setText("隨筆");

            }


            if (!mNoteList.get(mNoteList.size()-i-1).getmText().equals("")) {
                mainGridItemViewHolder.mText.setText(mNoteList.get(mNoteList.size() - i - 1).getmText());
            } else {
                mainGridItemViewHolder.mText.setText("還沒想到要寫些什麼...");
            }
            if (mNoteList.get(mNoteList.size()-i-1).getmPicture() != null && !mNoteList.get(mNoteList.size()-i-1).getmPicture().equals("")) {
                Bitmap bitmap = BitmapFactory.decodeFile(mNoteList.get(mNoteList.size()-i-1).getmPicture());
                mainGridItemViewHolder.mImage.setImageBitmap(bitmap);

                Log.d("bitmap", "path: " + mNoteList.get(mNoteList.size()-i-1).getmPicture());

            } else {

//                mainGridItemViewHolder.mImage.setVisibility(View.GONE);
//                mainGridItemViewHolder.mImageBack.setVisibility(View.GONE);
//                mainGridItemViewHolder.mCardView.setVisibility(View.GONE);
            }

        }

    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
//        return mNoteList.size();
    }

    @Override
    public int getItemViewType(int position) {

        super.getItemViewType(position);

        if (mNoteList.get(mNoteList.size()-position-1).getmClassification().equals("diary") || mNoteList.get(mNoteList.size()-position-1).getmClassification().equals("jot")) {
            return TYPE_DIARY;
        } else if (mNoteList.get(mNoteList.size()-position-1).getmClassification().equals("account")) {
            return TYPE_ACCOUNT;
        } else {
            return 100;
        }



    }

    public void setOnItemListener(View.OnClickListener listener) {
        this.mListener = listener;
    }


}
