package com.sandy.e3646.lifeblabla.jot;

import com.sandy.e3646.lifeblabla.object.Account;
import com.sandy.e3646.lifeblabla.object.Note;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class JotFragmentTest {

    @Test
    public void testCounting() {


        Account account = Mockito.mock(Account.class);





    }

    @Test
    public void setNoteData() {


        Note note = Mockito.mock(Note.class);
//        JotFragment jotFragment = new JotFragment(note);
        note.getmCreatedTime();
        Mockito.verify(note).getmCreatedTime();

    }
}