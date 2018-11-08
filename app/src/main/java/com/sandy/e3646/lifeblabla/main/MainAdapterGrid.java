package com.sandy.e3646.lifeblabla.main;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandy.e3646.Sqldatabase;
import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.object.Account;
import com.sandy.e3646.lifeblabla.object.Note;

import java.util.ArrayList;

public class MainAdapterGrid extends RecyclerView.Adapter {

    private Context mContext;
    private int TYPE_DIARY = 1;
    private int TYPE_ACCOUNT = 2;

    private View.OnClickListener mListener;
    private ArrayList<Note> mNoteList;

    public MainAdapterGrid (Context context, ArrayList<Note> noteList) {
        this.mContext = context;

        if (noteList != null) {
            mNoteList = noteList;
        } else {
            Sqldatabase sql = new Sqldatabase(mContext);
            mNoteList = sql.getNotes();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (i == TYPE_DIARY) {
            return new MainAdapterGrid.MainGridItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_grid_diary, null));

        } else if (i == TYPE_ACCOUNT) {
            return new MainAdapterGrid.MainGridItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_grid_account, null));
        } else {
            return null;
        }
    }

    public class MainGridItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImage;
        private ImageView mImageBack;

        private ImageView mDiaryEmotion;
        private ImageView mDiaryWeather;
        private ImageView mTagBackground;
        private ImageView mTypeBackground;

        private TextView mTag;
        private TextView mType;
        private TextView mTitle;
        private TextView mDate;
        private TextView mDayTime;
        private TextView mTime;

        private CardView mCardView;

        private ImageView mCard;

        /////

        private TextView mAccountRevenue;
        private TextView mAccountExpense;
        private TextView mAccountBalance;
        private TextView mAccountNumber;



        public MainGridItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mImage = itemView.findViewById(R.id.note_image);
            mImageBack = itemView.findViewById(R.id.note_image_back);
            mDiaryEmotion = itemView.findViewById(R.id.note_diary_emotion);
            mDiaryWeather = itemView.findViewById(R.id.note_diary_weather);
            mTagBackground = itemView.findViewById(R.id.note_tag_background);
            mTypeBackground = itemView.findViewById(R.id.note_type_background);
            mTag = itemView.findViewById(R.id.note_tag);
            mType = itemView.findViewById(R.id.note_type);
            mTitle = itemView.findViewById(R.id.note_title);
            mDate = itemView.findViewById(R.id.note_date);
            mDayTime = itemView.findViewById(R.id.note_daytime);
            mTime = itemView.findViewById(R.id.note_time);

            mCardView = itemView.findViewById(R.id.cardView);
            mCard = itemView.findViewById(R.id.card);

            /////

            mAccountRevenue = itemView.findViewById(R.id.account_revenue);
            mAccountExpense = itemView.findViewById(R.id.account_expense);
            mAccountBalance = itemView.findViewById(R.id.account_balance);
            mAccountNumber = itemView.findViewById(R.id.account_number);

        }

        @Override
        public void onClick(View view) {

        }
    }

    public class MainAccountItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mAccountNumber;
        private TextView mAccountRevenue;
        private TextView mAccountExpense;
        private TextView mAccountBalance;

        private TextView mTitle;
        private TextView mDate;
        private TextView mTime;


        public MainAccountItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mAccountNumber = itemView.findViewById(R.id.account_number);
            mAccountRevenue = itemView.findViewById(R.id.account_revenue);
            mAccountExpense = itemView.findViewById(R.id.account_expense);
            mAccountBalance = itemView.findViewById(R.id.account_balance);
            mTitle = itemView.findViewById(R.id.note_title);
            mDate = itemView.findViewById(R.id.note_date);
            mTime = itemView.findViewById(R.id.note_time);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (viewHolder instanceof MainGridItemViewHolder) {
            initLayoutDiary((MainGridItemViewHolder) viewHolder, i);
        } else if (viewHolder instanceof MainAccountItemViewHolder) {
            initLayoutAccount((MainAccountItemViewHolder)viewHolder, i);
        }

    }

    private void initLayoutDiary(MainGridItemViewHolder viewHolder, int i) {

        final MainGridItemViewHolder mainGridItemViewHolder = (MainGridItemViewHolder)viewHolder;

        int no = mNoteList.size()-i-1;

        mainGridItemViewHolder.itemView.setTag(no);
        mainGridItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view);
            }
        });

        //////// 共同 //////

        mainGridItemViewHolder.mDate.setText(mNoteList.get(no).getmCreatedTime());
        mainGridItemViewHolder.mDayTime.setText(mNoteList.get(no).getDayTime());
        mainGridItemViewHolder.mTime.setText(mNoteList.get(no).getTime());

        if (mNoteList.get(no).getmTag() != null && !mNoteList.get(no).getmTag().get(0).equals("")
                && !mNoteList.get(no).getmTag().get(0).equals("null")) {
            mainGridItemViewHolder.mTag.setText(mNoteList.get(no).getmTag().get(0));
            ViewGroup.LayoutParams backgorundParams = mainGridItemViewHolder.mTagBackground.getLayoutParams();
            backgorundParams.width = mNoteList.get(no).getmTag().get(0).length() * 30 + 20;
            mainGridItemViewHolder.mTagBackground.setLayoutParams(backgorundParams);
        } else {
            mainGridItemViewHolder.mTag.setVisibility(View.INVISIBLE);
            mainGridItemViewHolder.mTagBackground.setVisibility(View.INVISIBLE);
        }

        ////////////////////

        if (mNoteList.get(no).getmClassification().equals("diary")) {

            mainGridItemViewHolder.mTypeBackground.setImageResource(R.drawable.background_tag_diary);
            mainGridItemViewHolder.mType.setText("日記");

            mainGridItemViewHolder.mDiaryEmotion.setVisibility(View.VISIBLE);
            mainGridItemViewHolder.mDiaryWeather.setVisibility(View.VISIBLE);
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

            if (mNoteList.get(no).getmTitle() != null) {
                mainGridItemViewHolder.mTitle.setText(mNoteList.get(no).getmTitle());
            } else {
                mainGridItemViewHolder.mTitle.setText("一則日記");
            }

            if (mNoteList.get(no).getPhotoFromCamera() == null || mNoteList.get(no).getPhotoFromCamera().equals("")) {
                if (mNoteList.get(no).getmPicture() == null || mNoteList.get(no).getmPicture().equals("")) {
                    mainGridItemViewHolder.mImage.setVisibility(View.GONE);
                    mainGridItemViewHolder.mImageBack.setVisibility(View.GONE);
                    mainGridItemViewHolder.mCardView.setVisibility(View.GONE);
                } else {

                    Bitmap bitmap = BitmapFactory.decodeFile(mNoteList.get(no).getmPicture());
                    Bitmap resized = Bitmap.createScaledBitmap(bitmap, 190, 180, true);

                    mainGridItemViewHolder.mImage.setImageBitmap(resized);
                    mainGridItemViewHolder.mImageBack.setVisibility(View.VISIBLE);
                    mainGridItemViewHolder.mCardView.setVisibility(View.VISIBLE);
                    mainGridItemViewHolder.mImage.setVisibility(View.VISIBLE);
                }
            } else {
                mainGridItemViewHolder.mImage.setImageURI(Uri.parse(mNoteList.get(no).getPhotoFromCamera()));
                mainGridItemViewHolder.mImageBack.setVisibility(View.VISIBLE);
                mainGridItemViewHolder.mCardView.setVisibility(View.VISIBLE);
                mainGridItemViewHolder.mImage.setVisibility(View.VISIBLE);
            }

        } else if (mNoteList.get(no).getmClassification().equals("account")) {

            Sqldatabase sql = new Sqldatabase(mContext);
            ArrayList<Account> accountList = sql.getAccounts(mNoteList.get(mNoteList.size()-i-1).getmId());
            mainGridItemViewHolder.mAccountNumber.setText(String.valueOf(accountList.size()));

            if (mNoteList.get(no).getmTitle() != null && !mNoteList.get(no).getmTitle().equals("")) {
                mainGridItemViewHolder.mTitle.setText(mNoteList.get(no).getmTitle());
            } else {
                mainGridItemViewHolder.mTitle.setText("今日收支紀錄");
            }

        } else if (mNoteList.get(no).getmClassification().equals("jot")) {

            mainGridItemViewHolder.mDiaryEmotion.setVisibility(View.INVISIBLE);
            mainGridItemViewHolder.mDiaryWeather.setVisibility(View.INVISIBLE);

            mainGridItemViewHolder.mTypeBackground.setImageResource(R.drawable.background_tag_jot);
            mainGridItemViewHolder.mType.setText("隨筆");

            if (mNoteList.get(no).getmTitle() != null && !mNoteList.get(no).getmTitle().equals("")) {
                mainGridItemViewHolder.mTitle.setText(mNoteList.get(no).getmTitle());
            } else {
                mainGridItemViewHolder.mTitle.setText("隨手記一筆");
            }

            if (mNoteList.get(no).getPhotoFromCamera() == null || mNoteList.get(no).getPhotoFromCamera().equals("")) {
                if (mNoteList.get(no).getmPicture() == null || mNoteList.get(no).getmPicture().equals("")) {
                    mainGridItemViewHolder.mImage.setVisibility(View.GONE);
                    mainGridItemViewHolder.mImageBack.setVisibility(View.GONE);
                    mainGridItemViewHolder.mCardView.setVisibility(View.GONE);
                } else {
                    Bitmap bitmap = BitmapFactory.decodeFile(mNoteList.get(no).getmPicture());
                    mainGridItemViewHolder.mImage.setImageBitmap(bitmap);
                    mainGridItemViewHolder.mImageBack.setVisibility(View.VISIBLE);
                    mainGridItemViewHolder.mCardView.setVisibility(View.VISIBLE);
                    mainGridItemViewHolder.mImage.setVisibility(View.VISIBLE);
                }
            } else {
                mainGridItemViewHolder.mImage.setImageURI(Uri.parse(mNoteList.get(no).getPhotoFromCamera()));
                mainGridItemViewHolder.mImageBack.setVisibility(View.VISIBLE);
                mainGridItemViewHolder.mCardView.setVisibility(View.VISIBLE);
                mainGridItemViewHolder.mImage.setVisibility(View.VISIBLE);
            }

        }

        if (mNoteList.get(no).getAccountRevenue() != null) {
            mainGridItemViewHolder.mAccountRevenue.setText(mNoteList.get(no).getAccountRevenue());
            mainGridItemViewHolder.mAccountExpense.setText(mNoteList.get(no).getAccountExpense());
            mainGridItemViewHolder.mAccountBalance.setText(mNoteList.get(no).getAccountBalance());
        }


    }

    private void initLayoutAccount(MainAccountItemViewHolder viewHolder, int i) {
        MainAccountItemViewHolder mainAccountItemViewHolder = (MainAccountItemViewHolder)viewHolder;
        int no = mNoteList.size()-i-1;

        mainAccountItemViewHolder.mAccountRevenue.setText(mNoteList.get(no).getAccountRevenue());
        mainAccountItemViewHolder.mAccountExpense.setText(mNoteList.get(no).getAccountExpense());
        mainAccountItemViewHolder.mAccountBalance.setText(mNoteList.get(no).getAccountBalance());

        mainAccountItemViewHolder.itemView.setTag(no);
        mainAccountItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
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
