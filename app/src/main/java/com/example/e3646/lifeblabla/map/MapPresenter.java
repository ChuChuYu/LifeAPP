package com.example.e3646.lifeblabla.map;

import com.example.e3646.lifeblabla.main.MainContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class MapPresenter implements MapContract.Presenter {

    private MapContract.View mMapView;

    public MapPresenter(MapContract.View mapView) {

        mMapView = checkNotNull(mapView);
        mMapView.setPresenter(this);

    }

    @Override
    public void start() {

    }
}
