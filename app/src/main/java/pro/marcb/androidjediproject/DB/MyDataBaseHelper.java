package pro.marcb.androidjediproject.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;

import pro.marcb.androidjediproject.SupportClass.MemoryScore;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "MyDataBase.db";
    private static final String TAG = "MyDataBaseHelper";
    private static final String SQL_CREATE_TABLE1 =
            "CREATE TABLE " + MyDataBaseContract.Users.TABLE_NAME + " (" +
                    MyDataBaseContract.Users._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MyDataBaseContract.Users.COLUMN_USERNAME + " TEXT UNIQUE, " +
                    MyDataBaseContract.Users.COLUMN_PASSWORD + " TEXT)";

    private static final String SQL_DELETE_TABLE1 =
            "DROP TABLE IF EXISTS " + MyDataBaseContract.Users.TABLE_NAME;

    private static final String SQL_CREATE_TABLE2 =
            "CREATE TABLE " + MyDataBaseContract.Ranking.TABLE_NAME + " (" +
                    MyDataBaseContract.Ranking._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MyDataBaseContract.Ranking.COLUMN_USERNAME + " TEXT, " +
                    MyDataBaseContract.Ranking.COLUMN_SCORE + " TEXT, " +
                    MyDataBaseContract.Ranking.COLUMN_NUM_CARDS + " TEXT)";

    private static final String SQL_DELETE_TABLE2 =
            "DROP TABLE IF EXISTS " + MyDataBaseContract.Ranking.TABLE_NAME;

    private static MyDataBaseHelper instance;
    private static SQLiteDatabase writable;
    private static SQLiteDatabase readable;

    //With this, all must use getInstance(Context) to use this class
    private MyDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //We will use this method instead the default constructor to get a reference.
    //With this we will use all the time the same reference.
    public static MyDataBaseHelper getInstance(Context c) {
        if (instance == null) {
            instance = new MyDataBaseHelper(c);
            if (writable == null) {
                writable = instance.getWritableDatabase();
                Log.v(TAG, "creating writable");
            }
            if (readable == null) {
                readable = instance.getReadableDatabase();
                Log.v(TAG, "Creating readable");
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //We execute here the SQL sentence to create the DB
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE1);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //This method will be executed when the system detects that DATABASE_VERSION has been upgraded
        sqLiteDatabase.execSQL(SQL_DELETE_TABLE1);
        sqLiteDatabase.execSQL(SQL_DELETE_TABLE2);
        onCreate(sqLiteDatabase);
    }

    public long createUser(String username, String password) {
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Users.COLUMN_USERNAME, username);
        values.put(MyDataBaseContract.Users.COLUMN_PASSWORD, password);
        return writable.insert(MyDataBaseContract.Users.TABLE_NAME, null, values);
    }

    public int changeName(String old, String newone) {
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Users.COLUMN_USERNAME, newone);
        int t = readable.update(MyDataBaseContract.Users.TABLE_NAME,
                values,
                MyDataBaseContract.Users.COLUMN_USERNAME + " LIKE ? ",
                new String[]{old});

        renameUser(old, newone);
        //TODO: Renombrar algu amb un nom que ja existia
        return t;
    }

    public int changePassowrd(String username, String newPassword) {
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Users.COLUMN_PASSWORD, newPassword);
        return readable.update(MyDataBaseContract.Users.TABLE_NAME,
                values,
                MyDataBaseContract.Users.COLUMN_USERNAME + " LIKE ? ",
                new String[]{username});
    }

    public int deleteUser(String username) {
        int t = readable.delete(MyDataBaseContract.Users.TABLE_NAME,
                MyDataBaseContract.Users.COLUMN_USERNAME + " LIKE ? ",
                new String[]{username});
        deleteScores(username);
        return t;
    }

    public String getPassword(String username) {
        Cursor c;
        c = readable.query(MyDataBaseContract.Users.TABLE_NAME,
                new String[]{MyDataBaseContract.Users.COLUMN_PASSWORD},
                MyDataBaseContract.Users.COLUMN_USERNAME + " = ? ",
                new String[]{username},
                null, null, null);

        String returnValue = null;
        if (c.moveToFirst()) {
            do {
                //We go here if the cursor is not empty
                String l = c.getString(c.getColumnIndex(MyDataBaseContract.Users.COLUMN_PASSWORD));
                returnValue = l;
                Log.v(TAG, returnValue);
            } while (c.moveToNext());
        }

        //Always close the cursor after you finished using it
        c.close();

        return returnValue;
    }

    public long insertScore(String username, int score, int numCards) {
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Ranking.COLUMN_USERNAME, username);
        values.put(MyDataBaseContract.Ranking.COLUMN_NUM_CARDS, String.valueOf(numCards));
        values.put(MyDataBaseContract.Ranking.COLUMN_SCORE, String.valueOf(score));
        return writable.insert(MyDataBaseContract.Ranking.TABLE_NAME, null, values);
    }

    public long deleteScores(String username) {
        return readable.delete(MyDataBaseContract.Ranking.TABLE_NAME,
                MyDataBaseContract.Users.COLUMN_USERNAME + " LIKE ? ",
                new String[]{username});
    }

    public int renameUser(String usernameOld, String usernameNew) {
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Ranking.COLUMN_USERNAME, usernameNew);
        return readable.update(MyDataBaseContract.Ranking.TABLE_NAME,
                values,
                MyDataBaseContract.Users.COLUMN_USERNAME + " LIKE ? ",
                new String[]{usernameOld});
    }

    public Collection<MemoryScore> getScores() {
        Cursor c;

        c = readable.query(MyDataBaseContract.Ranking.TABLE_NAME,
                new String[]{MyDataBaseContract.Ranking.COLUMN_USERNAME, MyDataBaseContract.Ranking.COLUMN_NUM_CARDS, MyDataBaseContract.Ranking.COLUMN_SCORE},
                null,
                null,
                null, null, null);

        ArrayList<MemoryScore> memoryScores = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                //We go here if the cursor is not empty
                String username = c.getString(c.getColumnIndex(MyDataBaseContract.Ranking.COLUMN_USERNAME));
                String numCards = c.getString(c.getColumnIndex(MyDataBaseContract.Ranking.COLUMN_NUM_CARDS));
                String score = c.getString(c.getColumnIndex(MyDataBaseContract.Ranking.COLUMN_SCORE));
                memoryScores.add(new MemoryScore(username, numCards, score));
                Log.v(TAG, username + " " + score + " " + numCards);
            } while (c.moveToNext());
        }

        //Always close the cursor after you finished using it
        c.close();

        return memoryScores;
    }

    public void deleteScores() {
        writable.execSQL(SQL_DELETE_TABLE2);
        writable.execSQL(SQL_CREATE_TABLE2);
    }

    @Override
    public synchronized void close() {
        super.close();
        //Always close the SQLiteDatabase
        writable.close();
        readable.close();
        instance = null;
        writable = null;
        readable = null;
        Log.v(TAG, "close()");
    }
}
