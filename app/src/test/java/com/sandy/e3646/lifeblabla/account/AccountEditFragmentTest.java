package com.sandy.e3646.lifeblabla.account;

import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.sandy.e3646.lifeblabla.object.Account;
import com.sandy.e3646.lifeblabla.object.Note;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class AccountEditFragmentTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSetData() {

//        Account account = Mockito.mock(Account.class);
        Note note = Mockito.mock(Note.class);
//        Note note = new Note();

        AccountEditFragment accountEditFragment = Mockito.mock(AccountEditFragment.class);
//        AccountEditFragment accountEditFragment = new AccountEditFragment(false, note);

        AccountEditPresenter accountEditPresenter = Mockito.mock(AccountEditPresenter.class);
//        AccountEditPresenter accountEditPresenter = new AccountEditPresenter(accountEditFragment, null, null, true);
        accountEditFragment.setPresenter(accountEditPresenter);


        accountEditFragment.takeNoteData();

        Mockito.verify(accountEditPresenter).saveNoteData(null, note);

//        accountEditFragment.setAccountDatainDialog(account);
//        Mockito.verify(accountEditFragment).setAccountDatainDialog(account);

    }

    @Test
    public void getCurrentTime() {

        AccountEditFragment accountEditFragment = new AccountEditFragment(true, null);
        String time = accountEditFragment.getCurrentTime();
        assertEquals("2018/10/29", time);

    }
}