package pro.marcb.androidjediproject.SupportClass;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import pro.marcb.androidjediproject.R;

public class MiNotificationManager {

    private static SharedPreferences preferences;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private static void sendStateNotification(Context context) {


        /*NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Vamo a ve illo ")
                        .setContentText("Como te lo explico");*/


        Notification notif = new Notification.Builder(context)
                .setContentTitle("Vamo a ve illo ")
                .setContentText("Como te lo explico")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.dividir_0))
                .setStyle(new Notification.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.dividir_0)))
                .build();

        //Instanciamos Notification Manager
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, notif);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.SHARED_PREFERENCES.LAST, "Vamo a ve illo Como te lo explico");
        editor.apply();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void sendNotification(Context c) {

        preferences = c.getSharedPreferences(Constants.SHARED_PREFERENCES.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String notificationtype = preferences.getString(Constants.SHARED_PREFERENCES.NOTIFICATION_TYPE, Constants.SHARED_PREFERENCES.TOAST);

        if (notificationtype.equals(Constants.SHARED_PREFERENCES.STATE)) sendStateNotification(c);
        else sendToast(c);


    }

    private static void sendToast(Context c) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.SHARED_PREFERENCES.LAST, "La notificació d'estat és més divertida");
        editor.apply();
        Toast.makeText(c, "La notificació d'estat és més divertida", Toast.LENGTH_SHORT).show();
    }

}
