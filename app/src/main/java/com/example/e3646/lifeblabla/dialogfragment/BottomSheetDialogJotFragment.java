package com.example.e3646.lifeblabla.dialogfragment;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.diary.DiaryFragment;
import com.example.e3646.lifeblabla.mainactivity.MainActPresenter;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

@SuppressLint("ValidFragment")
public class BottomSheetDialogJotFragment extends BottomSheetDialogFragment {

    private ImageButton mTextButton;
    private ImageButton mAlbumButton;
    private ImageButton mCameraButton;
    private MainActPresenter mMainActPresenter;

    private Context mContext;
    private String mImagePath;

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

                mMainActPresenter.goJotEdit(null);
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
//                    mPhoto.setVisibility(View.VISIBLE);
//                    mPhoto.setImageURI(uri);
//                    mMinusButton.setVisibility(View.VISIBLE);
//                handleImage(data);

                mMainActPresenter.goJotEdit(mImagePath);
                mMainActPresenter.hideBottomNavigation();
                mMainActPresenter.hideComponent();
                dismiss();


                break;

            case 1:

//                    if (resultCode == RESULT_OK) {
//                        Log.d("take photo", "RESULT_OK");


//                    mImagePath = getRealPathFromURI(data.getData());
//                    Uri uriFor = data.getData();
//                        mPhoto.setVisibility(View.VISIBLE);
//                        mPhoto.setImageURI(mUri);
//
//                        Log.d("path", " : " + mImagePath);

//                    Bundle extras = data.getExtras();
//                    Bitmap imageBitmap = (Bitmap) extras.get("data");
//                    mPhoto.setVisibility(View.VISIBLE);
//                    mPhoto.setImageBitmap(imageBitmap);

//                    } else if (requestCode == RESULT_CANCELED) {
//                        Log.d("take photo", "RESULT_CANCELED");
//
//                    }

                break;

//                Uri uri = data.getData();
//                Log.d("camera", "on: " + uri);
//                displayPhoto(mImagePath);
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
