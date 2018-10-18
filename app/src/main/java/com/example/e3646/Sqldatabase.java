package com.example.e3646;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.e3646.lifeblabla.object.Account;
import com.example.e3646.lifeblabla.object.Note;

import java.util.ArrayList;
import java.util.Arrays;

public class Sqldatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SQLDB.db";
    public static final int DATABASE_VERSION = 1;

    private static final String NOTE_ID = "NOTE_ID";
    private static final String NOTE_TITLE = "NOTE_TITLE";
    private static final String NOTE_TEXT = "NOTE_TEXT";
    private static final String NOTE_CREATEDTIME = "NOTE_CREATEDTIME";
    private static final String NOTE_UPDATEDTIME = "NOTE_UPDATEDTIME";
    private static final String NOTE_PLACE = "NOTE_PLACE";
    private static final String NOTE_CLASSIFICATION = "NOTE_CLASSIFICATION";

    private static final String NOTE_TAG = "NOTE_TAG";

    private static final String NOTE_PICTURE = "NOTE_PICTURE";
    private static final String NOTE_VIDEO = "NOTE_VIDEO";
    private static final String NOTE_AUDIO = "NOTE_AUDIO";

    private static final String NOTE_MIND = "NOTE_MIND";
    private static final String NOTE_WEATHER = "NOTE_WEATHER";

    private static final String NOTE_ACCOUNT_REVENUE = "NOTE_ACCOUNT_REVENUE";
    private static final String NOTE_ACCOUNT_EXPENSE = "NOTE_ACCOUNT_EXPENSE";
    private static final String NOTE_ACCOUNT_BALANCE = "NOTE_ACCOUNT_BALANCE";

    private static final String ACCOUNT_ID = "ACCOUNT_ID";
    private static final String ACCOUNT_CREATEDTIME = "ACCOUNT_CREATEDTIME";
    private static final String ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
    private static final String ACCOUNT_DESPCIPTION = "ACCOUNT_DESPCIPTION";
    private static final String ACCOUNT_REVENUE = "ACCOUNT_REVENUE";
    private static final String ACCOUNT_EXPENSE = "ACCOUNT_EXPENSE";
    private static final String ACCOUNT_CATEGORY = "ACCCOUNT_CATEGORY";


    private Note mNote;

    public Sqldatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTable =
                "CREATE TABLE " + "NOTE_TABLE"
                        + " ("
                        + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + NOTE_ID + " TEXT, "
                        + NOTE_TITLE + " TEXT, "
                        + NOTE_TEXT + " TEXT, "
                        + NOTE_CREATEDTIME + " TEXT, "
                        + NOTE_UPDATEDTIME + " TEXT, "
                        + NOTE_PLACE + " TEXT, "
                        + NOTE_CLASSIFICATION + " TEXT, "
                        + NOTE_TAG + " TEXT, "
                        + NOTE_PICTURE + " TEXT, "
                        + NOTE_VIDEO + " TEXT, "
                        + NOTE_AUDIO + " TEXT, "
                        + NOTE_ACCOUNT_REVENUE + " TEXT, "
                        + NOTE_ACCOUNT_EXPENSE + " TEXT, "
                        + NOTE_ACCOUNT_BALANCE + " TEXT, "
                        + NOTE_MIND + " TEXT, "
                        + NOTE_WEATHER + " TEXT);";


        String createAccountTable =
                "CREATE TABLE " + "ACCOUNT_TABLE"
                        + " ("
                        + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + NOTE_ID + " TEXT, "
                        + ACCOUNT_ID + " TEXT, "
                        + ACCOUNT_CREATEDTIME + " TEXT, "
                        + ACCOUNT_NUMBER + " TEXT, "
                        + ACCOUNT_DESPCIPTION + " TEXT, "
                        + ACCOUNT_REVENUE + " TEXT, "
                        + ACCOUNT_EXPENSE + " TEXT, "
                        + ACCOUNT_CATEGORY +" TEXT);";

        sqLiteDatabase.execSQL(createAccountTable);
        sqLiteDatabase.execSQL(createTable);

        Log.d("create", "table");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insert(Note note) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_ID, note.getmId());
        contentValues.put(NOTE_TITLE, note.getmTitle());
        contentValues.put(NOTE_TEXT, note.getmText());
        contentValues.put(NOTE_CREATEDTIME, note.getmCreatedTime());
        contentValues.put(NOTE_UPDATEDTIME, note.getmUpdatedTime());
        contentValues.put(NOTE_PLACE, note.getmPlace());
        contentValues.put(NOTE_CLASSIFICATION, note.getmClassification());
        contentValues.put(NOTE_TAG, String.valueOf(note.getmTag()));
        contentValues.put(NOTE_PICTURE, note.getmPicture());
        contentValues.put(NOTE_VIDEO, note.getVideo());
        contentValues.put(NOTE_AUDIO, note.getmAudio());
        contentValues.put(NOTE_MIND, note.getmMind());
        contentValues.put(NOTE_WEATHER, note.getmWeather());

        long row = db.insert("NOTE_TABLE", null, contentValues);
        return row;

    }

    public ArrayList<Note> getNotes() {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query("NOTE_TABLE", new String[] {NOTE_ID, NOTE_TITLE, NOTE_TEXT,
                NOTE_CREATEDTIME, NOTE_UPDATEDTIME, NOTE_PLACE, NOTE_CLASSIFICATION, NOTE_TAG,
                NOTE_PICTURE, NOTE_VIDEO, NOTE_AUDIO, NOTE_MIND, NOTE_WEATHER}, null,
                null, null, null, null);

        ArrayList<Note> noteList = new ArrayList<>();

        if (cursor.getCount() > 0 ) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i ++) {
                Note note = new Note();

                note.setmId(cursor.getString(cursor.getColumnIndex(NOTE_ID)));
                note.setmTitle(cursor.getString(cursor.getColumnIndex(NOTE_TITLE)));
                note.setmText(cursor.getString(cursor.getColumnIndex(NOTE_TEXT)));
                note.setmCreatedTime(cursor.getString(cursor.getColumnIndex(NOTE_CREATEDTIME)));
                note.setmUpdatedTime(cursor.getString(cursor.getColumnIndex(NOTE_UPDATEDTIME)));
                note.setmPlace(cursor.getString(cursor.getColumnIndex(NOTE_PLACE)));
                note.setClassification(cursor.getString(cursor.getColumnIndex(NOTE_CLASSIFICATION)));

                note.setmPicture(cursor.getString(cursor.getColumnIndex(NOTE_PICTURE)));

                if (cursor.getString(cursor.getColumnIndex(NOTE_TAG)) != null) {
                    ArrayList<String> tagList = new ArrayList<String>(Arrays.asList(cursor.getString(cursor.getColumnIndex(NOTE_TAG)).split(",")));
                    ArrayList<String> tagListFinal = new ArrayList<String>();

                    for (int tagI = 0; tagI < tagList.size(); tagI ++) {
                        String tag;

                        if (tagList.get(tagI) != null) {
                            tag = tagList.get(tagI).replaceAll("\\p{Punct}","").toString();
                        tagListFinal.add(tag);
                        }
                    }
                    note.setmTag(tagListFinal);
                }
                note.setmMind(cursor.getString(cursor.getColumnIndex(NOTE_MIND)));
                note.setmWeather(cursor.getString(cursor.getColumnIndex(NOTE_WEATHER)));
                noteList.add(note);
                cursor.moveToNext();
            }
        }
        return noteList;
    }

    public void updateNotes(String id, Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_TITLE, note.getmTitle());
        contentValues.put(NOTE_TEXT, note.getmText());
        contentValues.put(NOTE_CREATEDTIME, note.getmCreatedTime());
        contentValues.put(NOTE_UPDATEDTIME, note.getmUpdatedTime());
        contentValues.put(NOTE_PLACE, note.getmPlace());
        contentValues.put(NOTE_CLASSIFICATION, note.getmClassification());
        contentValues.put(NOTE_TAG, String.valueOf(note.getmTag()));
        contentValues.put(NOTE_PICTURE, note.getmPicture());
        contentValues.put(NOTE_VIDEO, note.getVideo());
        contentValues.put(NOTE_AUDIO, note.getmAudio());
        contentValues.put(NOTE_MIND, note.getmMind());
        contentValues.put(NOTE_WEATHER, note.getmWeather());

        db.update("NOTE_TABLE", contentValues, NOTE_ID + "=\"" + id + "\"", null);
    }

    public void deleteNote(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("NOTE_TABLE", NOTE_ID + "=\"" + id + "\"", null);
    }

    public long insertAccount(Account account) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_ID, account.getId());
        contentValues.put(ACCOUNT_ID, account.getAccountId());
        contentValues.put(ACCOUNT_CREATEDTIME, account.getCreatedTime());
        contentValues.put(ACCOUNT_NUMBER, account.getNumber());
        contentValues.put(ACCOUNT_DESPCIPTION, account.getDescription());
        contentValues.put(ACCOUNT_CATEGORY, account.getCategory());
        contentValues.put(ACCOUNT_REVENUE, account.getRevenue());
        contentValues.put(ACCOUNT_EXPENSE, account.getExpense());

        long row = db.insert("ACCOUNT_TABLE", null, contentValues);

        Log.d("save account", "success");
        return row;
    }

    public ArrayList<Account> getAccounts(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("ACCOUNT_TABLE", new String [] {ACCOUNT_ID, ACCOUNT_CREATEDTIME, ACCOUNT_NUMBER, ACCOUNT_DESPCIPTION, ACCOUNT_CATEGORY, ACCOUNT_REVENUE, ACCOUNT_EXPENSE}, NOTE_ID + "=\"" + id + "\"", null, null, null,null);


        ArrayList<Account> accountList = new ArrayList<Account>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i ++) {
                Account account = new Account();
//                account.setId(cursor.getString(cursor.getColumnIndex(NOTE_ID)));
                account.setAccountId(cursor.getString(cursor.getColumnIndex(ACCOUNT_ID)));
                account.setCreatedTime(cursor.getString(cursor.getColumnIndex(ACCOUNT_CREATEDTIME)));
                account.setNumber(cursor.getString(cursor.getColumnIndex(ACCOUNT_NUMBER)));
                account.setDescription(cursor.getString(cursor.getColumnIndex(ACCOUNT_DESPCIPTION)));
                account.setRevenue(cursor.getString(cursor.getColumnIndex(ACCOUNT_REVENUE)));
                account.setExpense(cursor.getString(cursor.getColumnIndex(ACCOUNT_EXPENSE)));
                account.setCategory(cursor.getString(cursor.getColumnIndex(ACCOUNT_CATEGORY)));

                accountList.add(account);
                cursor.moveToNext();
            }

        }
        return accountList;

    }

    public void updateAccount(String id, String accountId, Account account) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_ID, account.getId());
        contentValues.put(ACCOUNT_ID, account.getAccountId());
        contentValues.put(ACCOUNT_CREATEDTIME, account.getCreatedTime());
        contentValues.put(ACCOUNT_NUMBER, account.getNumber());
        contentValues.put(ACCOUNT_DESPCIPTION, account.getDescription());
        contentValues.put(ACCOUNT_CATEGORY, account.getCategory());
        contentValues.put(ACCOUNT_REVENUE, account.getRevenue());
        contentValues.put(ACCOUNT_EXPENSE, account.getExpense());

        db.update("ACCOUNT_TABLE", contentValues, ACCOUNT_ID + "=\"" + accountId + "\"", null);

    }

    public void deleteAccount(String accountId) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("ACCOUNT_TABLE", ACCOUNT_ID + "=\"" + accountId + "\"", null);
    }

}
