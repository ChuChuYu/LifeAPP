package com.example.e3646.lifeblabla.setting;

import static com.google.common.base.Preconditions.checkNotNull;

public class SettingPresenter implements SettingContract.Presenter {

    private SettingContract.View mSettingView;

    public SettingPresenter(SettingContract.View settingView) {

        mSettingView = checkNotNull(settingView);
        mSettingView.setPresenter(this);

    }

    @Override
    public void start() {

    }
}
