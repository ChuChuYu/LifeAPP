package com.example.e3646.lifeblabla.diary;

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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import com.example.e3646.Sqldatabase;
import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.object.Note;

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
    private EditText mDiaryTag;
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
    private ImageView mTagBottom;

    private RecyclerView mTagRecyclerView;
    private DiaryEditAdapter mDiaryEditAdapter;

    private Note mNote;
    private ArrayList<Note> mNoteList;

    private Uri mUri;

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
        mDiaryEditAdapter = new DiaryEditAdapter();
        mTagRecyclerView.setAdapter(mDiaryEditAdapter);

        mDiaryTag = (EditText)view.findViewById(R.id.jot_tag);
        mDiaryTag.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                Log.d("done button", "click 2");
                return false;
            }
        });

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
//            if (mNote.getmPicture() != null || !mNote.getmPicture().equals("")) {
//                mMinusButton.setVisibility(View.VISIBLE);
//                Bitmap bitmap = BitmapFactory.decodeFile(mNote.getmPicture());
//                mPhoto.setImageBitmap(bitmap);
//
//            }
        }

        mCreatedTime = (TextView)view.findViewById(R.id.diary_createdtime);

        if (!isCreating && mNote != null) {
            mDiaryText.setText(mNote.getmText());
            mDiaryTitle.setText(mNote.getmTitle().toString());
            mCreatedTime.setText(mNote.getmCreatedTime());
        } else {

            mCreatedTime.setText(currentTime());
        }

        mCompleteButton = (Button)view.findViewById(R.id.button_complete);
        mCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isCreating) {
                    mPresenter.completeCreating();
                } else {

                    takeDiaryData();


//                    mPresenter.updateDiaryData(mNote.getmId(), mNote);
//                    mPresenter.completeEditDiary();
                    mPresenter.completeEditing(mNote);

                }
            }
        });

        mCancelButton = (Button)view.findViewById(R.id.button_cancel);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isCreating) {
                    mPresenter.cancelEditDiary(null);
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
//
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
        mTagBottom.setVisibility(View.INVISIBLE);

        mTagButton = (ImageButton)view.findViewById(R.id.button_tag);
        mTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTagBottom.setVisibility(View.VISIBLE);
                mTagBottom.startAnimation(animation);

            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 0: //呼叫相簿
//                mMindButton.setVisibility(View.VISIBLE);
                handleImage(data);
                break;

            case 9453:

                if (resultCode == RESULT_OK) {
                    Log.d("take photo", "RESULT_OK");


//                    mImagePath = getRealPathFromURI(data.getData());
                    mPhoto.setVisibility(View.VISIBLE);
                    mPhoto.setImageURI(mUri);
                    Log.d("path", " : " + mImagePath);

//                    Bundle extras = data.getExtras();
//                    Bitmap imageBitmap = (Bitmap) extras.get("data");
//                    mPhoto.setVisibility(View.VISIBLE);
//                    mPhoto.setImageBitmap(imageBitmap);

                } else if (requestCode == RESULT_CANCELED) {
                    Log.d("take photo", "RESULT_CANCELED");

                }

                break;

//                Uri uri = data.getData();
//                Log.d("camera", "on: " + uri);
//                displayPhoto(mImagePath);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    private String currentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

        Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間

        String str = formatter.format(curDate);

        return str;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void takeDiaryData() {

        if (isCreating) {
            mPresenter.setContext(mContext);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat formateForID = new SimpleDateFormat("yyyyMMddHHmmss");
            Date curDate = new Date(System.currentTimeMillis());
            String currentTime = formatter.format(curDate);
            String id = formateForID.format(curDate);

            mNote = new Note();
            mNote.setmId(id);
            if (mDiaryTitle.getText().toString() != null && !mDiaryTitle.getText().toString().equals("")) {
                mNote.setmTitle(mDiaryTitle.getText().toString());
            } else {
                mNote.setmTitle("這是一則日記");
            }
            mNote.setmText(mDiaryText.getText().toString());
            mNote.setmCreatedTime(currentTime);
            mNote.setmUpdatedTime("");
            mNote.setmPlace("市政府");
            mNote.setClassification("diary");

            Log.d("set image path", ": " + mImagePath);
            mNote.setmPicture(mImagePath);

            mNote.setmTag(mDiaryEditAdapter.TagList());

            mNote.setmMind(mMindNum);

            Log.d("weather", "num: "+ mWeatherNum);
            mNote.setmWeather(mWeatherNum);


            mNoteList = new ArrayList<Note>();
            mNoteList.add(mNote);
            mPresenter.saveDiaryData(mNoteList, mNote);
        } else { //isEditing

            mNote.setmTitle(mDiaryTitle.getText().toString());
            mNote.setmText(mDiaryText.getText().toString());
            mNote.setmPicture(mImagePath);
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

        if(num.equals("1")) {
            mMindButton.setImageResource(R.drawable.button_emotion);
        }
        if (num.equals("2")) {
            mMindButton.setImageResource(R.drawable.emotion_2);

        }
        if (num.equals("3")) {
            mMindButton.setImageResource(R.drawable.emotion_3);

        }
        if (num.equals("4")) {
            mMindButton.setImageResource(R.drawable.emotion_4);

        }
        if (num.equals("5")) {
            mMindButton.setImageResource(R.drawable.emotion_5);

        }
        if (num.equals("6")) {
            mMindButton.setImageResource(R.drawable.emotion_6);

        }

    }

    @Override
    public void setWeatherSelect(String num) {

        mWeatherNum = num;

        if (num.equals("1")) {
            mWeatherButton.setImageResource(R.drawable.weather_1);
        } else if (num.equals("2")) {
            mWeatherButton.setImageResource(R.drawable.weather_sun);
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

        Log.d("edit diary in fragment", "note title: " + mNote.getmCreatedTime());
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

        Log.d("permission", "granted" + ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE));

        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.d("permission", "granted");
        }

        String state = Environment.getExternalStorageState();// 獲取記憶體卡可用狀態
        if (state.equals(Environment.MEDIA_MOUNTED)) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri uri;
//            File file = new File(Environment.getExternalStorageDirectory() + "/images/"+System.currentTimeMillis()+".jpg");

            File file = null;
            try {
                file = File.createTempFile(String.valueOf(System.currentTimeMillis()), ".jpg", getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){
                uri = Uri.fromFile(file);
            } else {

                mUri = FileProvider.getUriForFile(mContext, "com.example.e3646.lifeblabla", file);
                Log.d("uri", ": "+ mUri);
                Log.d("file", ": "+ file.getAbsolutePath());

            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
            intent.putExtra("ImageUri", mUri);
            intent.setFlags(FLAG_GRANT_READ_URI_PERMISSION);
            intent.setFlags(FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(intent, 9453);
        } else {

        }
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

        Log.d("imagePath 4 ", ": " + imagePath);

        if (imagePath != null) {

            Log.d("imagePath 5 ", ": " + imagePath);
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
