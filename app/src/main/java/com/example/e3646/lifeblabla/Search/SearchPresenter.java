package com.example.e3646.lifeblabla.Search;

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
}
