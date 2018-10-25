package com.example.e3646.lifeblabla.main;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
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
import com.example.e3646.lifeblabla.object.Account;
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

    public MainAdapterGrid (Context context, ArrayList<Note> noteList) {
        this.mContext = context;

        if (noteList != null) {
            mNoteList = noteList;
        } else {
            Sqldatabase sql = new Sqldatabase(mContext);
            mNoteList = sql.getNotes();
        }
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


            if (mNoteList.get(no).getmClassification().equals("diary")) {
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

            if (mNoteList.get(no).getmClassification().equals("jot")) {
                mainGridItemViewHolder.mTypeBackground.setImageResource(R.drawable.background_tag_jot);
                mainGridItemViewHolder.mType.setText("隨筆");
                mainGridItemViewHolder.mDiaryWeather.setVisibility(View.INVISIBLE);
                mainGridItemViewHolder.mDiaryEmotion.setVisibility(View.INVISIBLE);

                if (mNoteList.get(no).getmTitle() != null && !mNoteList.get(no).getmTitle().equals("")) {
                    mainGridItemViewHolder.mTitle.setText(mNoteList.get(no).getmTitle());
                } else {
                    mainGridItemViewHolder.mTitle.setText("隨手記一筆");
                }

                if (mNoteList.get(no).getPhotoFromCamera() == null || mNoteList.get(no).getPhotoFromCamera().equals("")) {
                    if (mNoteList.get(no).getmPicture() == null || mNoteList.get(no).getmPicture().equals("")) {

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

        mainGridItemViewHolder.mDate.setText(mNoteList.get(no).getmCreatedTime());
        mainGridItemViewHolder.mTime.setText(mNoteList.get(no).getTime());

            if (mNoteList.get(no).getmClassification().equals("account")) {
                mainGridItemViewHolder.mTime.setText(mNoteList.get(no).getTime());
                Sqldatabase sql = new Sqldatabase(mContext);
                ArrayList<Account> accountList = sql.getAccounts(mNoteList.get(mNoteList.size()-i-1).getmId());
                mainGridItemViewHolder.mAccountNumber.setText(String.valueOf(accountList.size()));

                if (mNoteList.get(no).getmTitle() != null && !mNoteList.get(no).getmTitle().equals("")) {
                    mainGridItemViewHolder.mTitle.setText(mNoteList.get(no).getmTitle());
                } else {
                    mainGridItemViewHolder.mTitle.setText("今日收支紀錄");
                }
            }


            if (mNoteList.get(no).getAccountRevenue() != null) {
                mainGridItemViewHolder.mAccountRevenue.setText(mNoteList.get(no).getAccountRevenue());
                mainGridItemViewHolder.mAccountExpense.setText(mNoteList.get(no).getAccountExpense());
                mainGridItemViewHolder.mAccountBalance.setText(mNoteList.get(no).getAccountBalance());
            }

        if (mNoteList.get(no).getmTag() != null && !mNoteList.get(mNoteList.size() - i - 1).getmTag().get(0).equals("") && !mNoteList.get(mNoteList.size() - i - 1).getmTag().get(0).equals("null")) {
            mainGridItemViewHolder.mTag.setText(mNoteList.get(mNoteList.size() - i - 1).getmTag().get(0));
            ViewGroup.LayoutParams backgorundParams = mainGridItemViewHolder.mTagBackground.getLayoutParams();
            backgorundParams.width = mNoteList.get(mNoteList.size() - i - 1).getmTag().get(0).length() * 30 + 20;
            mainGridItemViewHolder.mTagBackground.setLayoutParams(backgorundParams);
        } else {
            mainGridItemViewHolder.mTag.setVisibility(View.INVISIBLE);
            mainGridItemViewHolder.mTagBackground.setVisibility(View.INVISIBLE);
        }


        mainGridItemViewHolder.itemView.setTag(no);

        mainGridItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view);
                Log.d("gird item", "click");
            }
        });

//

//        if (mNoteList != null && mNoteList.get(mNoteList.size()-i-1) != null) {
//            if (mNoteList.get(mNoteList.size()-i-1).getmClassification().equals("diary")) {
//
//
//                if (mNoteList.get(mNoteList.size() - i - 1).getmMind() != null) {
//                    if (mNoteList.get(mNoteList.size() - i - 1).getmMind().equals("1")) {
//                        mainGridItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.button_emotion);
//                    } else if (mNoteList.get(mNoteList.size() - i - 1).getmMind().equals("2")) {
//                        mainGridItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.emotion_2);
//                    } else if (mNoteList.get(mNoteList.size() - i - 1).getmMind().equals("3")) {
//                        mainGridItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.emotion_3);
//                    } else if (mNoteList.get(mNoteList.size() - i - 1).getmMind().equals("4")) {
//                        mainGridItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.emotion_4);
//                    } else if (mNoteList.get(mNoteList.size() - i - 1).getmMind().equals("5")) {
//                        mainGridItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.emotion_5);
//                    } else if (mNoteList.get(mNoteList.size() - i - 1).getmMind().equals("6")) {
//                        mainGridItemViewHolder.mDiaryEmotion.setImageResource(R.drawable.emotion_6);
//                    }
//                }
//
//                if (mNoteList.get(mNoteList.size()-i-1).getmWeather() != null) {
//                    if (mNoteList.get(mNoteList.size()-i-1).getmWeather().equals("1")) {
//                        mainGridItemViewHolder.mDiaryWeather.setImageResource(R.drawable.weather_1);
//                    } else if (mNoteList.get(mNoteList.size()-i-1).getmWeather().equals("2")) {
//                        mainGridItemViewHolder.mDiaryWeather.setImageResource(R.drawable.button_weather);
//                    } else if (mNoteList.get(mNoteList.size()-i-1).getmWeather().equals("3")) {
//                        mainGridItemViewHolder.mDiaryWeather.setImageResource(R.drawable.weather_3);
//                    } else if (mNoteList.get(mNoteList.size()-i-1).getmWeather().equals("4")) {
//                        mainGridItemViewHolder.mDiaryWeather.setImageResource(R.drawable.weather_4);
//                    } else if (mNoteList.get(mNoteList.size()-i-1).getmWeather().equals("5")) {
//                        mainGridItemViewHolder.mDiaryWeather.setImageResource(R.drawable.weather_5);
//                    } else if (mNoteList.get(mNoteList.size()-i-1).getmWeather().equals("6")) {
//                        mainGridItemViewHolder.mDiaryWeather.setImageResource(R.drawable.weather_6);
//                    }
//
//                }
//
//            } else if (mNoteList.get(mNoteList.size()-i-1).getmClassification().equals("jot")) {
//
//                mainGridItemViewHolder.mDiaryEmotion.setVisibility(View.INVISIBLE);
//                mainGridItemViewHolder.mDiaryWeather.setVisibility(View.INVISIBLE);
//                mainGridItemViewHolder.mTypeBackground.setImageResource(R.drawable.background_tag_jot);
//                mainGridItemViewHolder.mType.setText("隨筆");
//
//            } else if (mNoteList.get(mNoteList.size()-i-1).getmClassification().equals("account")) {
//
//
//            }
//
//
//            if (!mNoteList.get(mNoteList.size()-i-1).getmText().equals("")) {
//                mainGridItemViewHolder.mText.setText(mNoteList.get(mNoteList.size() - i - 1).getmText());
//            } else {
//                mainGridItemViewHolder.mText.setText("還沒想到要寫些什麼...");
//            }
//            if (mNoteList.get(mNoteList.size()-i-1).getmPicture() != null && !mNoteList.get(mNoteList.size()-i-1).getmPicture().equals("")) {
//                Bitmap bitmap = BitmapFactory.decodeFile(mNoteList.get(mNoteList.size()-i-1).getmPicture());
//                mainGridItemViewHolder.mImage.setImageBitmap(bitmap);
//
//                Log.d("bitmap", "path: " + mNoteList.get(mNoteList.size()-i-1).getmPicture());
//
//            } else {
//
//                mainGridItemViewHolder.mImage.setVisibility(View.GONE);
//                mainGridItemViewHolder.mImageBack.setVisibility(View.GONE);
//                mainGridItemViewHolder.mCardView.setVisibility(View.GONE);
//            }
//
//        }



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
