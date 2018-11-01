package com.sandy.e3646.lifeblabla.diary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandy.e3646.Sqldatabase;
import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.adapter.TagEditAdapter;
import com.sandy.e3646.lifeblabla.object.Note;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
import static com.google.common.base.Preconditions.checkNotNull;

@SuppressLint("ValidFragment")
public class DiaryEditFragment extends Fragment implements DiaryEditContract.View {

    private DiaryEditContract.Presenter mPresenter;
    private Context mContext;

    private EditText mDiaryTitle;
    private EditText mDiaryText;
    private TextView mCreatedTime;

    private String mMindNum;
    private String mWeatherNum;
    private String mImagePath;

    private Button mCompleteButton;
    private Button mCancelButton;
    private ImageButton mMindButton;
    private ImageButton mWeatherButton;
    private ImageButton mPhotoButton;
    private ImageButton mCameraButton;
    private ImageButton mVideoButton;
    private ImageButton mTagButton;
    private ImageButton mMinusButton;
    private ImageView mPhoto;

    private ConstraintLayout mBottomBar;

    private ImageView mTagBottom;

    private RecyclerView mTagRecyclerView;
    private TagEditAdapter mTagEditAdapter;

    private Note mNote;
    private ArrayList<Note> mNoteList;

    private Uri mUri;

    private ConstraintLayout mConstraintLayout;

    private boolean isCreating;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    public DiaryEditFragment(boolean iscreating, Note note) {

        this.isCreating = iscreating;
        mNote = note;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_diaryedit, container, false);

        mContext = getContext();
        mDiaryTitle = (EditText)view.findViewById(R.id.diary_title10);
        mDiaryText = (EditText)view.findViewById(R.id.jot_text);

        mTagRecyclerView = (RecyclerView)view.findViewById(R.id.diary_tag_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mTagRecyclerView.setLayoutManager(linearLayoutManager);
        mTagEditAdapter = new TagEditAdapter(this);
        mTagRecyclerView.setAdapter(mTagEditAdapter);

        mBottomBar = view.findViewById(R.id.bottom_bar);

        mConstraintLayout = view.findViewById(R.id.constraintlayout);

        mPhoto = (ImageView)view.findViewById(R.id.Jot_photo);
        mMinusButton = (ImageButton)view.findViewById(R.id.button_minus);
        mMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deletePhoto();

            }
        });

        if  (isCreating) {
            mPhoto.setVisibility(View.INVISIBLE);
            mMinusButton.setVisibility(View.INVISIBLE);
        } else if (!isCreating) {
        }

        mCreatedTime = (TextView)view.findViewById(R.id.diary_createdtime);

        if (!isCreating && mNote != null) {
            mDiaryText.setText(mNote.getmText());
            mDiaryTitle.setText(mNote.getmTitle().toString());
            mCreatedTime.setText(mNote.getmCreatedTime());
        } else {

            mCreatedTime.setText(getCurrentTime());
        }

        mCompleteButton = (Button)view.findViewById(R.id.button_complete);
        mCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isCreating) {
                    mPresenter.completeCreating();
                } else {

                    takeDiaryData();
                    mPresenter.completeEditing(mNote);

                }
            }
        });

        mCancelButton = (Button)view.findViewById(R.id.button_cancel);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isCreating) {
                    mPresenter.cancelEditing(null);
                } else {
                    hideUI();
                }
            }
        });

        mMindButton = (ImageButton)view.findViewById(R.id.button_mind);
        mMindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPresenter.selectMind();
            }
        });

        mWeatherButton = (ImageButton)view.findViewById(R.id.button_weather);
        mWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPresenter.selectWeather();
            }
        });


        mPhotoButton = (ImageButton)view.findViewById(R.id.button_photo);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPhotoFromGallery();
            }
        });

        mCameraButton = (ImageButton)view.findViewById(R.id.button_camera);
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getPhotoFromCamera();
            }
        });

        mVideoButton = (ImageButton)view.findViewById(R.id.button_vedio);
        mTagBottom = (ImageView)view.findViewById(R.id.tag_bottom);
        final Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.tag_animation);
        mTagRecyclerView.setVisibility(View.INVISIBLE);
        mTagRecyclerView.setFitsSystemWindows(true);

        mTagButton = (ImageButton)view.findViewById(R.id.button_tag);
        mTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mTagRecyclerView.setVisibility(View.VISIBLE);
                mTagRecyclerView.startAnimation(animation);

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (keyEvent.getAction() == keyEvent.ACTION_UP && i == keyEvent.KEYCODE_BACK) {

                    if (isCreating) {
                        mPresenter.cancelEditing(null);
                    } else {
                        hideUI();
                    }
                    return false;
                }
                return false;
            }


        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return;
            }
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 0: //呼叫相簿
                Uri uri = data.getData();
                handleImage(data);
                mPhoto.setVisibility(View.VISIBLE);
                mPhoto.setImageURI(uri);
                mMinusButton.setVisibility(View.VISIBLE);

                break;

            case 1: //呼叫相機

                if (resultCode == RESULT_OK) {

                    //用getdata的方式沒有東西
                    mConstraintLayout.setVisibility(View.VISIBLE);
                    mPhoto.setVisibility(View.VISIBLE);
                    mPhoto.setImageURI(mUri);

                } else if (requestCode == RESULT_CANCELED) {

                }
                break;

             default:
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    @Override
    public void takeDiaryData() {

        if (isCreating) {
            mPresenter.setContext(mContext);
            mNote = new Note();
            if (mDiaryTitle.getText().toString() != null && !mDiaryTitle.getText().toString().equals("")) {
                mNote.setmTitle(mDiaryTitle.getText().toString());
            } else {
                mNote.setmTitle("這是一則日記");
            }
            mNote.setmText(mDiaryText.getText().toString());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat formatterForMonth = new SimpleDateFormat("MM");
            SimpleDateFormat formatterForDay = new SimpleDateFormat("dd");
            SimpleDateFormat formatterForTime = new SimpleDateFormat("HH:mm");
            SimpleDateFormat formatterForWeek = new SimpleDateFormat("EEEE");
            SimpleDateFormat formateForID = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat formateForDaytime = new SimpleDateFormat("HH");
            Date curDate = new Date(System.currentTimeMillis());
            String currentTime = formatter.format(curDate);
            String month = formatterForMonth.format(curDate);
            String day = formatterForDay.format(curDate);
            String time = formatterForTime.format(curDate);
            String week = formatterForWeek.format(curDate);
            String id = formateForID.format(curDate);
            String daytime = formateForDaytime.format(curDate);

            if (Integer.parseInt(daytime) > 12) {
                mNote.setDayTime("下午");
            } else {
                mNote.setDayTime("上午");
            }

            mNote.setmCreatedTime(currentTime);
            mNote.setMonth(month);
            mNote.setDay(day);
            mNote.setTime(time);
            if (week.equals("Monday")) {
                mNote.setWeek("MON");
            } else if (week.equals("Tuesday")) {
                mNote.setWeek("TUE");
            } else if (week.equals("Wednesday")) {
                mNote.setWeek("WED");
            } else if (week.equals("Thursday")) {
                mNote.setWeek("THUR");
            } else if (week.equals("Friday")) {
                mNote.setWeek("FRI");
            } else if (week.equals("Saturday")) {
                mNote.setWeek("SAT");
            } else if (week.equals("Sunday")) {
                mNote.setWeek("SUN");
            }

            mNote.setmId(id);
            mNote.setmUpdatedTime("");
            mNote.setmPlace("市政府");
            mNote.setClassification("diary");

            if (mUri != null) {
                mNote.setPhotoFromCamera(mUri.toString());
            } else if (mImagePath != null) {
                mNote.setmPicture(mImagePath);
            }

            mNote.setmPicture(mImagePath);
            mNote.setmTag(mTagEditAdapter.TagList());
            mNote.setmMind(mMindNum);
            mNote.setmWeather(mWeatherNum);

            mNoteList = new ArrayList<Note>();
            mNoteList.add(mNote);
            mPresenter.saveDiaryData(mNoteList, mNote);
        } else { //isEditing

            mNote.setmTitle(mDiaryTitle.getText().toString());
            mNote.setmText(mDiaryText.getText().toString());
            if (mUri != null) {
                mNote.setPhotoFromCamera(mUri.toString());
            } else if (mImagePath != null) {
                mNote.setmPicture(mImagePath);
            }
            mNote.setmMind(mMindNum);

            Sqldatabase sql = new Sqldatabase(mContext);
            sql.updateNotes(mNote.getmId(), mNote);

        }
    }

    @Override
    public void hideUI() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(DiaryEditFragment.this)
                .commit();
    }

    @Override
    public void setMindSelection(String num) {
        mMindNum = num;
        if (num.equals("1")) {
            mMindButton.setImageResource(R.drawable.button_emotion);
        } else if (num.equals("2")) {
            mMindButton.setImageResource(R.drawable.emotion_2);
        } else if (num.equals("3")) {
            mMindButton.setImageResource(R.drawable.emotion_3);
        } else if (num.equals("4")) {
            mMindButton.setImageResource(R.drawable.emotion_4);
        } else if (num.equals("5")) {
            mMindButton.setImageResource(R.drawable.emotion_5);
        } else if (num.equals("6")) {
            mMindButton.setImageResource(R.drawable.emotion_6);
        }
    }

    @Override
    public void setWeatherSelect(String num) {
        mWeatherNum = num;
        if (num.equals("1")) {
            mWeatherButton.setImageResource(R.drawable.weather_1);
        } else if (num.equals("2")) {
            mWeatherButton.setImageResource(R.drawable.button_weather);
        } else if (num.equals("3")) {
            mWeatherButton.setImageResource(R.drawable.weather_3);
        } else if (num.equals("4")) {
            mWeatherButton.setImageResource(R.drawable.weather_4);
        } else if (num.equals("5")) {
            mWeatherButton.setImageResource(R.drawable.weather_5);
        } else if (num.equals("6")) {
            mWeatherButton.setImageResource(R.drawable.weather_6);
        }
    }

    @Override
    public void setNote(Note note) {
        this.mNote = note;
        mDiaryText.setText(mNote.getmText());
        mDiaryTitle.setText(mNote.getmTitle().toString());
        mCreatedTime.setText(mNote.getmCreatedTime());
    }

    @Override
    public void getPhotoFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 0);
    }

    @Override
    public void getPhotoFromCamera() {

        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        }
        String state = Environment.getExternalStorageState();// 獲取記憶體卡可用狀態
        if (state.equals(Environment.MEDIA_MOUNTED)) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = new File(Environment.getExternalStorageDirectory() + "/images/" + System.currentTimeMillis() + ".jpg");

            try {
                file = File.createTempFile(String.valueOf(System.currentTimeMillis()), ".jpg", getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                mUri = Uri.fromFile(file);
            } else {
                mUri = FileProvider.getUriForFile(mContext, "com.example.e3646.lifeblabla", file);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
            intent.setFlags(FLAG_GRANT_READ_URI_PERMISSION);
            intent.setFlags(FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(intent, 1);
        } else {

        }
    }

    @Override
    public void getTagEditFocus() {

        mBottomBar.setFitsSystemWindows(true);
    }

    @Override
    public void getTagEditUnFocus() {

        mBottomBar.setFitsSystemWindows(false);

    }


    @Override
    public void setPresenter(DiaryEditContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    public void handleImage(Intent data) {
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(mContext, uri)) {
            //如果是document型別的uri，則通過document id處理
            String docId = DocumentsContract.getDocumentId(uri);

            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出數字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                mImagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);

            } else if ("com.android,providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                mImagePath = getImagePath(contentUri, null);

            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            mImagePath = getImagePath(uri, null);

        }

        displayPhoto(mImagePath);
    }

    public String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null); //內容提供器
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)); //獲取路徑
            }
        }
        cursor.close();
        return path;
    }

    public void displayPhoto(String imagePath) {

        mConstraintLayout.setVisibility(View.VISIBLE);

        if (imagePath != null) {

            mPhoto.setVisibility(View.VISIBLE);
            Bitmap bitImage = BitmapFactory.decodeFile(imagePath);//格式化圖片
            mPhoto.setImageBitmap(bitImage);//為imageView設定圖片

        }

        mMinusButton.setVisibility(View.VISIBLE);
    }

    public void deletePhoto() {
        mPhoto.setVisibility(View.INVISIBLE);
        mMinusButton.setVisibility(View.INVISIBLE);
        this.mImagePath = "";
    }

}
