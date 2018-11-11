package com.sandy.e3646.lifeblabla.setting;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.sandy.e3646.lifeblabla.NotificationService;
import com.sandy.e3646.lifeblabla.R;
import com.sandy.e3646.lifeblabla.mainactivity.MainActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class SettingFragment extends Fragment implements SettingContract.View {

    private SettingContract.Presenter mPresenter;
    private BottomSheetBehavior mBottomSheetBehavior;
    private Switch mTitleSwitch;
    private Switch mTextSwitch;
    private ImageView mTitleBackground;
    private ImageView mTextBackground;
    private EditText mTitle;
    private EditText mText;

    private ImageButton mDiaryTimeButton;
    private ImageButton mAccountTimeButton;

    private ImageButton mAddButton;
    private boolean isExpanded = false;
    private boolean isCustomTitle;
    private boolean isCustomText;

    private int timesOfBackKey = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        mBottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet_layout));
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mAddButton = (ImageButton)view.findViewById(R.id.button_add);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpanded == false) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    isExpanded = true;
                } else {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    isExpanded = false;
                }

            }
        });

        mTitleBackground = (ImageView)view.findViewById(R.id.background_title);
        mTextBackground = (ImageView)view.findViewById(R.id.background_text);
        mTitle = (EditText)view.findViewById(R.id.sample_title);
        mText = (EditText)view.findViewById(R.id.sample_text);

        mTitleSwitch = (Switch)view.findViewById(R.id.switch_title);
        mTitleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isCustomTitle = b;
                    mTitleBackground.setVisibility(View.GONE);
                    mTitle.setVisibility(View.GONE);

                    if (isCustomTitle == isCustomText) {
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }

                } else {
                    isCustomTitle = b;
                    mTitleBackground.setVisibility(View.VISIBLE);
                    mTitle.setVisibility(View.VISIBLE);


                }
            }
        });

        mTextSwitch = (Switch)view.findViewById(R.id.switch_text);
        mTextSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    isCustomText = b;
                    mTextBackground.setVisibility(View.GONE);
                    mText.setVisibility(View.GONE);

                    if (isCustomTitle == isCustomText) {
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }

                } else {
                    isCustomText = b;
                    mTextBackground.setVisibility(View.VISIBLE);
                    mText.setVisibility(View.VISIBLE);

                }
            }
        });

        mDiaryTimeButton = view.findViewById(R.id.button_diary_time);
        mDiaryTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Log.d("current time", String.valueOf(System.currentTimeMillis()));
//                sendNotification("diaryedit");
                Intent intent = new Intent(getContext(), com.sandy.e3646.lifeblabla.NotificationService.class);
                getContext().startService(intent);

                Intent i = new Intent(getContext(), com.sandy.e3646.lifeblabla.NickyService.class);
                getContext().startService(i);

            }
        });

        mAccountTimeButton = view.findViewById(R.id.button_account_time);
        mAccountTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification("accountedit");
            }
        });

        return view;
    }



    @Override
    public void setPresenter(SettingContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);

    }

    @Override
    public void printSometing() {
        Log.d("service", "is already created.");
    }

    private void sendNotification(String string) {

        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra("fragment", string);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext());
        builder.setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentTitle("Brandy Note")
                .setContentText("快來編寫今天的日記吧！")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent);
        notificationManager.notify(1, builder.build());
    }


}
