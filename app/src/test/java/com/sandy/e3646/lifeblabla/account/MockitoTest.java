package com.sandy.e3646.lifeblabla.account;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MockitoTest {

    @Test
    public void getCurrentTime() {

        AccountEditFragment accountEditFragment = new AccountEditFragment(true, null);
        String time = accountEditFragment.getCurrentTime();
        assertEquals("2018/10/29", time);

    }
}
