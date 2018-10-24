package com.example.e3646.lifeblabla.Search;

import android.content.Context;
import android.util.Log;

import com.example.e3646.Sqldatabase;
import com.example.e3646.lifeblabla.object.Note;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View mSearchView;

    public SearchPresenter(SearchContract.View searchView) {
        mSearchView = checkNotNull(searchView);
        mSearchView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public ArrayList<Note> getSearchList(Context context, String tag) {

        ArrayList<Note> searchList = new ArrayList<Note>();

        Sqldatabase sql = new Sqldatabase(context);
        ArrayList<Note> noteList = sql.getNotes();

        for (int i = 0; i < noteList.size(); i ++) {
            if ( noteList.get(i).getmTag().contains(tag)) {
                searchList.add(noteList.get(i));
            }
        }
        Log.d("search list", "size 1 : " + searchList.size());

        return searchList;
    }
}
