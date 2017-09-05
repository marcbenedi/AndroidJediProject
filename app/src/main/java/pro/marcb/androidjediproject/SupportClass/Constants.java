package pro.marcb.androidjediproject.SupportClass;

public final class Constants {

    private Constants() {
    }

    ;

    public static final class SHARED_PREFERENCES {
        public static final String SHARED_PREFERENCES_NAME = "Sharedpreferences";
        public static final String USER_LOGGED = "Userlogged";
        public static final String NOTIFICATION_TYPE = "Notificafiontype";
        public static final String STATE = "deestado";
        public static final String TOAST = "toast";
        public static final String LAST = "lastnotification";
    }

    public static final class SING_UP_ACTIVITY_RESULT {
        public static final int SignUpCODE = 1234;
        public static final String USERNAME_FIELD = "usernamefield";
        public static final String PASSWORD_FIELD = "passwordfield";
    }

    public static final class RANKING_PAGE_FRAGMENT {
        public static final String NUM_CARDS = "numcards";
    }

    public static final class CALCULATOR_BUNDLE_INSTANCE {
        public static final String EXP = "expression";
        public static final String LAST = "lastpressed";
    }

}
