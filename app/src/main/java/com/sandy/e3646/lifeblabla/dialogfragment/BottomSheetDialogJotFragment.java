package com.sandy.e3646.lifeblabla.dialogfragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.mainactivity.MainActPresenter;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

@SuppressLint("ValidFragment")
public class BottomSheetDialogJotFragment extends BottomSheetDialogFragment {

    private ImageButton mTextButton;
    private ImageButton mAlbumButton;
    private ImageButton mCameraButton;
    private MainActPresenter mMainActPresenter;

    private Context mContext;
    private String mImagePath;
    private Uri mUri;

    public BottomSheetDialogJotFragment(MainActPresenter mainActPresenter) {
        mMainActPresenter = mainActPresenter;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.bottom_sheet_dialog_jot, container, false);

        mContext = getContext();

        mTextButton = (ImageButton)view.findViewById(R.id.button_form_text);
        mTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMainActPresenter.goJotEdit(null, null);
                mMainActPresenter.hideBottomNavigation();
                mMainActPresenter.hideComponent();
                dismiss();


            }
        });

        mAlbumButton = (ImageButton)view.findViewById(R.id.button_form_album);
        mAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getPhotoFromGallery();


            }
        });

        mCameraButton = (ImageButton)view.findViewById(R.id.button_form_camera);
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getPhotoFromCamera();
            }
        });


        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 0: //呼叫相簿
                Uri uri = data.getData();
                handleImage(data);
                mMainActPresenter.goJotEdit(mImagePath, null);
                mMainActPresenter.hideBottomNavigation();
                mMainActPresenter.hideComponent();
                dismiss();


                break;

            case 1:
                if (resultCode == RESULT_OK) {

                    mMainActPresenter.goJotEdit(null, mUri);
                    mMainActPresenter.hideBottomNavigation();
                    mMainActPresenter.hideComponent();
                    dismiss();

                } else if (requestCode == RESULT_CANCELED) {


                }
                break;

            default:
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((View) getView().getParent()).setBackgroundColor(Color.TRANSPARENT);
    }

    public void getPhotoFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 0);
    }

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


    public void handleImage(Intent data) {
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(mContext, uri)) {
            //如果是document型別的uri，則通過document id處理
            String docId = DocumentsContract.getDocumentId(uri);

            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                Log.d("path", "selection : ");
                String id = docId.split(":")[1];//解析出數字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                mImagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);

            } else if ("com.android,providers.downloads.documents".equals(uri.getAuthority())) {
                Log.d("path", "content uri: ");
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                mImagePath = getImagePath(contentUri, null);

            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            mImagePath = getImagePath(uri, null);

        }

//        displayPhoto(mImagePath);
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
}
