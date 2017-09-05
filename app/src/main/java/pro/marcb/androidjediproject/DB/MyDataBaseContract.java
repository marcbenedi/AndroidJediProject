package pro.marcb.androidjediproject.DB;

import android.provider.BaseColumns;

public final class MyDataBaseContract {

    // We prevent that someone instantiate this class making the constructor private
    private MyDataBaseContract() {
    }

    //We create as much public static classes as tables we have in our database
    public static class Users implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
    }

    public static class Ranking implements BaseColumns {
        public static final String TABLE_NAME = "ranking";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_NUM_CARDS = "numcards";
        public static final String COLUMN_SCORE = "score";
    }


}
