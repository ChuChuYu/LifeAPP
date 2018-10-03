package com.example.e3646.lifeblabla.diary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.object.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

public class DiaryEditFragment extends Fragment implements DiaryEditContract.View {

    private DiaryEditContract.Presenter mPresenter;

    private ImageButton mMindOneButton;
    private ImageButton mMindTwoButton;
    private ImageButton mMindThreeButton;
    private ImageButton mMindFourButton;
    private ImageButton mMindFiveButton;
    private ImageButton mMindSixButton;

    private ImageView mMindOneSelect;
    private ImageView mMindTwoSelect;
    private ImageView mMindThreeSelect;
    private ImageView mMindFourSelect;
    private ImageView mMindFiveSelect;
    private ImageView mMindSixSelect;

    private EditText mDiaryTitle;
    private EditText mDiaryText;

    private String mDiaryMind;

    private Button mCompleteButton;
    private Button mCancelButton;

    private Note mNote;
    private ArrayList<Note> mNoteList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_diaryedit, container, false);

//        mMindOneButton = (ImageButton)view.findViewById(R.id.mind_button_1);
//        mMindTwoButton = (ImageButton)view.findViewById(R.id.mind_button_2);
//        mMindThreeButton = (ImageButton)view.findViewById(R.id.mind_button_3);
//        mMindFourButton = (ImageButton)view.findViewById(R.id.mind_button_4);
//        mMindFiveButton = (ImageButton)view.findViewById(R.id.mind_button_5);
//        mMindSixButton = (ImageButton)view.findViewById(R.id.mind_button_6);
//
//        mMindOneSelect = (ImageView)view.findViewById(R.id.mind_selected_1);
//        mMindTwoSelect = (ImageView)view.findViewById(R.id.mind_selected_2);
//        mMindThreeSelect = (ImageView)view.findViewById(R.id.mind_selected_3);
//        mMindFourSelect = (ImageView)view.findViewById(R.id.mind_selected_4);
//        mMindFiveSelect = (ImageView)view.findViewById(R.id.mind_selected_5);
//        mMindSixSelect = (ImageView)view.findViewById(R.id.mind_selected_6);

        mDiaryTitle = (EditText)view.findViewById(R.id.diary_title10);
        mDiaryText = (EditText)view.findViewById(R.id.diary_text) ;

        mCompleteButton = (Button)view.findViewById(R.id.button_complete);
        mCancelButton = (Button)view.findViewById(R.id.button_cancel);

//        mMindOneButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showMindSelection(mMindOneSelect);
//                mDiaryMind = "mind1";
//            }
//        });
//
//        mMindTwoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showMindSelection(mMindTwoSelect);
//                mDiaryMind = "mind2";
//            }
//        });
//
//        mMindThreeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showMindSelection(mMindThreeSelect);
//                mDiaryMind = "mind3";
//            }
//        });
//
//        mMindFourButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showMindSelection(mMindFourSelect);
//                mDiaryMind = "mind4";
//            }
//        });
//
//        mMindFiveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showMindSelection(mMindFiveSelect);
//                mDiaryMind = "mind5";
//            }
//        });
//
//        mMindSixButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showMindSelection(mMindSixSelect);
//                mDiaryMind = "mind6";
//            }
//        });
//
//        mCompleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPresenter.completeEditDiary();
//            }
//        });
//
//        mCancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPresenter.cancelEditDiary();
//            }
//        });


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showMindSelection(ImageView imageView) {

//        mMindOneSelect.setVisibility(View.INVISIBLE);
//        mMindTwoSelect.setVisibility(View.INVISIBLE);
//        mMindThreeSelect.setVisibility(View.INVISIBLE);
//        mMindFourSelect.setVisibility(View.INVISIBLE);
//        mMindFiveSelect.setVisibility(View.INVISIBLE);
//        mMindSixSelect.setVisibility(View.INVISIBLE);
//        imageView.setVisibility(View.VISIBLE);

    }

    @Override
    public void takeDiaryData() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis()) ;
        String currentTime = formatter.format(curDate);

        mNote = new Note();
        mNote.setmTitle(mDiaryTitle.getText().toString());
        mNote.setmText(mDiaryText.getText().toString());
        mNote.setmCreatedTime(currentTime);
        mNote.setClassification("diary");
        mNote.setmPlace("市政府");
        mNote.setmMind(mDiaryMind);
        mNote.setmWeather("sun");

        mNoteList = new ArrayList<Note>();
        mNoteList.add(mNote);
        mPresenter.saveDiaryData(mNoteList);

    }

    @Override
    public void setPresenter(DiaryEditContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);

    }

}
