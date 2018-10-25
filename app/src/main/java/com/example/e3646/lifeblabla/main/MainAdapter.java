package com.example.e3646.lifeblabla.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.e3646.Sqldatabase;
import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.object.Account;
import com.example.e3646.lifeblabla.object.Note;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private View.OnClickListener mListener;
    private ArrayList<Note> mNoteList;

    private int opened = -1;

    public MainAdapter(Context context, ArrayList<Note> noteList) {
        this.mContext = context;
        this.mNoteList = noteList;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new MainListItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_list_new_new, null));

    }

    public class MainListItemViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        private TextView mMonth;
        private TextView mWeek;
        private TextView mDay;
        private TextView mDayTime;
        private TextView mTime;
        private TextView mTag;
        private TextView mType;
        private TextView mTitle;
        private TextView mText;
        private TextView mTextOne;
        private TextView mTextTwo;
        private TextView mAccountItemNumber;

        private ImageView mDiaryEmotion;
        private ImageView mDiaryWeather;
        private ImageView mTagBackground;
        private ImageView mTypeBackground;

        private LinearLayout mLinearLayout;
        private RelativeLayout mRelativeLayout;

        public MainListItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mMonth = itemView.findViewById(R.id.note_month);
            mWeek = itemView.findViewById(R.id.note_date);
            mDay = itemView.findViewById(R.id.note_day);
            mDayTime = itemView.findViewById(R.id.note_daytime);
            mTime = itemView.findViewById(R.id.note_time);
            mTag = itemView.findViewById(R.id.note_tag);
            mType = itemView.findViewById(R.id.note_type);
            mTitle = itemView.findViewById(R.id.note_title);
            mText = itemView.findViewById(R.id.note_text);
            mTextOne = itemView.findViewById(R.id.text_one);
            mTextTwo = itemView.findViewById(R.id.text_two);
            mAccountItemNumber = itemView.findViewById(R.id.account_item_number);

            mDiaryEmotion = itemView.findViewById(R.id.note_diary_emotion);
            mDiaryWeather = itemView.findViewById(R.id.note_diary_weather);
            mTagBackground = itemView.findViewById(R.id.note_background);
            mTypeBackground = itemView.findViewById(R.id.note_type_background);

            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.msg_ll);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative_layout);

            mRelativeLayout.setOnLongClickListener(this);

        }

        @Override
        public boolean onLongClick(View view) {

//            Toast.makeText(mContext, "Long click", Toast.LENGTH_SHORT).show();

            if (opened == getAdapterPosition()) {
                opened = -1;
                notifyItemChanged(getAdapterPosition());
            } else {
                int oldOpened = opened;
                opened = getAdapterPosition();
                notifyItemChanged(oldOpened);
                notifyItemChanged(opened);
            }

            return true;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        MainListItemViewHolder mainListItemViewHolder = (MainListItemViewHolder) viewHolder;

        int no = mNoteList.size()-i-1;

        if (i == opened){
            mainListItemViewHolder.mLinearLayout.setVisibility(View.VISIBLE);
        } else{
            mainListItemViewHolder.mLinearLayout.setVisibility(View.GONE);
        }

        mainListItemViewHolder.mRelativeLayout.setTag(no);
        mainListItemViewHolder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view);
            }
        });


        if (mNoteList.get(no).getmTag() != null && !mNoteList.get(no).getmTag().get(0).equals("") && !mNoteList.get(no).getmTag().get(0).equals("null")) {
            mainListItemViewHolder.mTag.setText(mNoteList.get(no).getmTag().get(0));
            ViewGroup.LayoutParams backgorundParams = mainListItemViewHolder.mTagBackground.getLayoutParams();
            int numofchinese = numOfChinese(mNoteList.get(no).getmTag().get(0));
            Log.d("num of ", "chinese " + numofchinese);
            int numofenglish = mNoteList.get(no).getmTag().get(0).length() - numofchinese;
            backgorundParams.width = numofenglish*25 + numofchinese*60 + 20;
            mainListItemViewHolder.mTagBackground.setLayoutParams(backgorundParams);
        } else {
            mainListItemViewHolder.mTag.setVisibility(View.INVISIBLE);
            mainListItemViewHolder.mTagBackground.setVisibility(View.INVISIBLE);
        }

            if (mNoteList.get(no).getmClassification().equals("diary")) {



            } else if (mNoteList.get(no).getmClassification().equals("account")) {

                mainListItemViewHolder.mTypeBackground.setImageResource(R.drawable.background_tag_account);

                if (mNoteList.get(no).getmTitle() != null && !mNoteList.get(no).getmTitle().equals("")) {
                    mainListItemViewHolder.mTitle.setText(mNoteList.get(no).getmTitle());

                } else {
                    mainListItemViewHolder.mTitle.setText("今日收支紀錄");
                }

                mainListItemViewHolder.mType.setText("記帳");
                mainListItemViewHolder.mDiaryEmotion.setVisibility(View.INVISIBLE);
                mainListItemViewHolder.mDiaryWeather.setVisibility(View.INVISIBLE);
                Sqldatabase sql = new Sqldatabase(mContext);
                ArrayList<Account> accountList = sql.getAccounts(mNoteList.get(no).getmId());
                mainListItemViewHolder.mAccountItemNumber.setText(String.valueOf(accountList.size()));


                if (mNoteList.get(no).getAccountRevenue() != null) {

                    mainListItemViewHolder.mText.setText("收入：" + mNoteList.get(no).getAccountRevenue() + "  支出：" + mNoteList.get(no).getAccountExpense()
                            + "  小計：" + mNoteList.get(no).getAccountBalance());

                    Sqldatabase sqldb = new Sqldatabase(mContext);
                    if (sqldb.getAccounts(sqldb.getNotes().get(no).getmId()).size() > 0) {

                    }


                    if (sqldb.getAccounts(sql.getNotes().get(no).getmId()).size() > 0 && sqldb.getAccounts(sql.getNotes().get(no).getmId()).get(0).getCategory() != null) {
                        String category = sqldb.getAccounts(sql.getNotes().get(no).getmId()).get(0).getCategory();

//                        if (category.equals("1")) {
//                            mainListItemViewHolder.mTitle.setText("吃飯");
//
//                            Log.d("category", ": " + category);
//                        } else if (category.equals("2")) {
//                            mainListItemViewHolder.mTitle.setText("交通");
//                        }



                    }

                } else {
                    mainListItemViewHolder.mText.setText("收入： 0" + "  支出： 0" + "  小計： 0" );


                }

            } else if (mNoteList.get(no).getmClassification().equals("jot")) {

                mainListItemViewHolder.mTitle.setText("隨手一筆記");
                mainListItemViewHolder.mText.setText(mNoteList.get(no).getmText());
                mainListItemViewHolder.mTypeBackground.setImageResource(R.drawable.background_tag_jot);
                mainListItemViewHolder.mType.setText("隨筆");
                mainListItemViewHolder.mDiaryEmotion.setVisibility(View.INVISIBLE);
                mainListItemViewHolder.mDiaryWeather.setVisibility(View.INVISIBLE);
                mainListItemViewHolder.mTextOne.setVisibility(View.INVISIBLE);
                mainListItemViewHolder.mTextTwo.setVisibility(View.INVISIBLE);
                mainListItemViewHolder.mAccountItemNumber.setVisibility(View.INVISIBLE);
            }


            if (mNoteList.get(no).getmMind() != null) {
                if (mNoteList.get(no).getmMind().equals("1")) {
                    mainListItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.button_emotion);
                } else if (mNoteList.get(no).getmMind().equals("2")) {
                    mainListItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.emotion_2);
                } else if (mNoteList.get(no).getmMind().equals("3")) {
                    mainListItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.emotion_3);
                } else if (mNoteList.get(no).getmMind().equals("4")) {
                    mainListItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.emotion_4);
                } else if (mNoteList.get(no).getmMind().equals("5")) {
                    mainListItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.emotion_5);
                } else if (mNoteList.get(no).getmMind().equals("6")) {
                    mainListItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.emotion_6);
                }
            }

            if (mNoteList.get(no).getmWeather() != null) {
                if (mNoteList.get(no).getmWeather().equals("1")) {
                    mainListItemViewHolder.mDiaryWeather.setImageResource(R.drawable.weather_1);
                } else if (mNoteList.get(no).getmWeather().equals("2")) {
                    mainListItemViewHolder.mDiaryWeather.setImageResource(R.drawable.button_weather);
                } else if (mNoteList.get(no).getmWeather().equals("3")) {
                    mainListItemViewHolder.mDiaryWeather.setImageResource(R.drawable.weather_3);
                } else if (mNoteList.get(no).getmWeather().equals("4")) {
                    mainListItemViewHolder.mDiaryWeather.setImageResource(R.drawable.weather_4);
                } else if (mNoteList.get(no).getmWeather().equals("5")) {
                    mainListItemViewHolder.mDiaryWeather.setImageResource(R.drawable.weather_5);
                } else if (mNoteList.get(no).getmWeather().equals("6")) {
                    mainListItemViewHolder.mDiaryWeather.setImageResource(R.drawable.weather_6);
                }

            }

            if (mNoteList.get(no).getMonth() != null) {
                mainListItemViewHolder.mMonth.setText(mNoteList.get(no).getMonth());
                mainListItemViewHolder.mDay.setText(mNoteList.get(no).getDay());
                mainListItemViewHolder.mTime.setText(mNoteList.get(no).getTime());
                mainListItemViewHolder.mWeek.setText(mNoteList.get(no).getWeek());
            }

            if (mNoteList.get(no).getDayTime() != null) {
                mainListItemViewHolder.mDayTime.setText(mNoteList.get(no).getDayTime());
            }


        if (mNoteList.get(no).getmClassification().equals("diary")) {

            if (mNoteList.get(no).getmTitle() != null && !mNoteList.get(no).equals("")) {
                mainListItemViewHolder.mTitle.setText(mNoteList.get(no).getmTitle());

                mainListItemViewHolder.mTypeBackground.setImageResource(R.drawable.background_tag_diary);
                mainListItemViewHolder.mType.setText("日記");
                mainListItemViewHolder.mTextOne.setVisibility(View.INVISIBLE);
                mainListItemViewHolder.mTextTwo.setVisibility(View.INVISIBLE);
                mainListItemViewHolder.mAccountItemNumber.setVisibility(View.INVISIBLE);
                mainListItemViewHolder.mText.setText(mNoteList.get(no).getmText());
            }




        } else if (mNoteList.get(no).getmClassification().equals("account")) {

            if (mNoteList.get(no).getmTitle() != null && !mNoteList.get(no).getmTitle().equals("")) {
                mainListItemViewHolder.mTitle.setText(mNoteList.get(no).getmTitle());
            } else {
                mainListItemViewHolder.mTitle.setText("今日收支紀錄");
            }

        }


    }


    @Override
    public int getItemCount() {
        if (mNoteList != null) {
            return mNoteList.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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
