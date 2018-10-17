package com.example.e3646.lifeblabla.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

        return new MainListItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_list_new, null));

    }

    public class MainListItemViewHolder extends RecyclerView.ViewHolder {

//        private TextView mTitle;
//        private TextView mText;
//        private ImageView mType;
//        private ImageView mImage;
//        private ImageView mTagBackground;
//        private TextView mTagText;


        private TextView mMonth;
        private TextView mWeek;
        private TextView mDate;
        private TextView mSlot;
        private TextView mTime;
        private TextView mTag;
        private TextView mType;
        private TextView mText;

        private ImageView mDiaryEmotion;
        private ImageView mDiaryWeather;
        private ImageView mTagBackground;
        private ImageView mTypeBackground;


        public MainListItemViewHolder(@NonNull View itemView) {
            super(itemView);

//            mTitle = (TextView)itemView.findViewById(R.id.note_title);
//            mText = (TextView)itemView.findViewById(R.id.list_item_text);
//            mType = (ImageView)itemView.findViewById(R.id.list_item_type);
//            mImage = (ImageView)itemView.findViewById(R.id.list_item_image);
//            mTagBackground = (ImageView)itemView.findViewById(R.id.main_tag_backgroung);
//            mTagText = (TextView)itemView.findViewById(R.id.tag_text);

            mMonth = itemView.findViewById(R.id.note_month);
            mWeek = itemView.findViewById(R.id.note_day);
            mDate = itemView.findViewById(R.id.note_date);
            mSlot = itemView.findViewById(R.id.note_period);
            mTime = itemView.findViewById(R.id.note_time);
            mTag = itemView.findViewById(R.id.note_tag);
            mType = itemView.findViewById(R.id.note_type);
            mText = itemView.findViewById(R.id.note_text);

            mDiaryEmotion = itemView.findViewById(R.id.note_diary_emotion);
            mDiaryWeather = itemView.findViewById(R.id.note_diary_weather);
            mTagBackground = itemView.findViewById(R.id.note_background);
            mTypeBackground = itemView.findViewById(R.id.note_type_background);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        MainListItemViewHolder mainListItemViewHolder = (MainListItemViewHolder) viewHolder;
        viewHolder.itemView.setTag(mNoteList.size() - i - 1);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view);
            }
        });


        if (mNoteList.get(mNoteList.size() - i - 1).getmTag() != null && !mNoteList.get(mNoteList.size() - i - 1).getmTag().get(0).equals("") && !mNoteList.get(mNoteList.size() - i - 1).getmTag().get(0).equals("null")) {
            mainListItemViewHolder.mTag.setText(mNoteList.get(mNoteList.size() - i - 1).getmTag().get(0));
            ViewGroup.LayoutParams backgorundParams = mainListItemViewHolder.mTagBackground.getLayoutParams();
            backgorundParams.width = mNoteList.get(mNoteList.size() - i - 1).getmTag().get(0).length() * 30 + 20;
            mainListItemViewHolder.mTagBackground.setLayoutParams(backgorundParams);
        } else {
            mainListItemViewHolder.mTag.setVisibility(View.INVISIBLE);
            mainListItemViewHolder.mTagBackground.setVisibility(View.INVISIBLE);
        }


        if (mNoteList != null && mNoteList.get(mNoteList.size() - i - 1) != null) {
//            mainListItemViewHolder.mTitle.setText(mNoteList.get(mNoteList.size()-i-1).getmTitle());
            mainListItemViewHolder.mText.setText(mNoteList.get(mNoteList.size() - i - 1).getmText());

            if (mNoteList.get(mNoteList.size() - i - 1).getmClassification().equals("diary")) {
                mainListItemViewHolder.mTypeBackground.setImageResource(R.drawable.background_tag_diary);
                mainListItemViewHolder.mType.setText("日記");
            } else if (mNoteList.get(mNoteList.size() - i - 1).getmClassification().equals("account")) {
                mainListItemViewHolder.mTypeBackground.setImageResource(R.drawable.background_tag_account);
                mainListItemViewHolder.mType.setText("記帳");
            } else if (mNoteList.get(mNoteList.size() - i - 1).getmClassification().equals("jot")) {
                mainListItemViewHolder.mTypeBackground.setImageResource(R.drawable.background_tag_jot);
                mainListItemViewHolder.mType.setText("隨筆");
            }


            if (mNoteList.get(mNoteList.size() - i - 1).getmMind() != null) {
                if (mNoteList.get(mNoteList.size() - i - 1).getmMind().equals("1")) {
                    mainListItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.button_emotion);
                } else if (mNoteList.get(mNoteList.size() - i - 1).getmMind().equals("2")) {
                    mainListItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.emotion_2);
                } else if (mNoteList.get(mNoteList.size() - i - 1).getmMind().equals("3")) {
                    mainListItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.emotion_3);
                } else if (mNoteList.get(mNoteList.size() - i - 1).getmMind().equals("4")) {
                    mainListItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.emotion_4);
                } else if (mNoteList.get(mNoteList.size() - i - 1).getmMind().equals("5")) {
                    mainListItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.emotion_5);
                } else if (mNoteList.get(mNoteList.size() - i - 1).getmMind().equals("6")) {
                    mainListItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.emotion_6);
                }
            }

            if (mNoteList.get(mNoteList.size()-i-1).getmWeather() != null) {
                if (mNoteList.get(mNoteList.size()-i-1).getmWeather().equals("1")) {
                    mainListItemViewHolder.mDiaryWeather.setImageResource(R.drawable.weather_1);
                } else if (mNoteList.get(mNoteList.size()-i-1).getmWeather().equals("2")) {
                    mainListItemViewHolder.mDiaryWeather.setImageResource(R.drawable.button_weather);
                } else if (mNoteList.get(mNoteList.size()-i-1).getmWeather().equals("3")) {
                    mainListItemViewHolder.mDiaryWeather.setImageResource(R.drawable.weather_3);
                } else if (mNoteList.get(mNoteList.size()-i-1).getmWeather().equals("4")) {
                    mainListItemViewHolder.mDiaryWeather.setImageResource(R.drawable.weather_4);
                } else if (mNoteList.get(mNoteList.size()-i-1).getmWeather().equals("5")) {
                    mainListItemViewHolder.mDiaryWeather.setImageResource(R.drawable.weather_5);
                } else if (mNoteList.get(mNoteList.size()-i-1).getmWeather().equals("6")) {
                    mainListItemViewHolder.mDiaryWeather.setImageResource(R.drawable.weather_6);
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
