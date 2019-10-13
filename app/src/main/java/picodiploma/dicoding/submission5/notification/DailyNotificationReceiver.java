package picodiploma.dicoding.submission5.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import java.util.Calendar;

import androidx.core.app.NotificationCompat;
import picodiploma.dicoding.submission5.R;

public class DailyNotificationReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 1001;
    public static String CHANNEL_ID = "channel_01";
    public static CharSequence CHANNEL_NAME = "movie app channel";

    public DailyNotificationReceiver(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String content_title = context.getString(R.string.content_title);
        String content_text = context.getString(R.string.content_text);
        String subtext = context.getString(R.string.subtext);

        showAlarm(context, content_title, content_text, subtext, NOTIFICATION_ID);
    }

    public void showAlarm(Context context, String content_title, String content_text, String subtext, int notifid){
        NotificationManager mNotifManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_movie)
                .setContentTitle(content_title)
                .setContentText(content_text)
                .setSubText(subtext)
                .setAutoCancel(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setShowBadge(true);
            channel.setLightColor(Color.LTGRAY);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            mBuilder.setChannelId(CHANNEL_ID);
            if(mNotifManager != null){
                mNotifManager.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();
        if(mNotifManager!=null){
            mNotifManager.notify(notifid, notification);
        }
    }

    public void setRepeatAlarm(Context context){
        unsetAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 07);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND,0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, getPendingIntent(context));
    }

    public void unsetAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        Intent unsetIntent = new Intent(context, DailyNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,NOTIFICATION_ID,unsetIntent,PendingIntent.FLAG_CANCEL_CURRENT);

        if(alarmManager != null){
            alarmManager.cancel(pendingIntent);
        }
    }

    public static PendingIntent getPendingIntent(Context context){
        Intent pendingIntent = new Intent(context, DailyNotificationReceiver.class);
        return  PendingIntent.getBroadcast(context,NOTIFICATION_ID,pendingIntent,PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
