package picodiploma.dicoding.submission5.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import androidx.core.app.NotificationCompat;
import picodiploma.dicoding.submission5.BuildConfig;
import picodiploma.dicoding.submission5.R;
import picodiploma.dicoding.submission5.adapter.MovieRes;
import picodiploma.dicoding.submission5.api.ClientAPI;
import picodiploma.dicoding.submission5.detail.MovieItem;
import picodiploma.dicoding.submission5.detail.MovieResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReleaseTodayReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 101;
    private static int notificationId;
    public static String CHANNEL_ID = "channel_02";
    public static CharSequence CHANNEL_NAME = "movie app channel";
    String title;
    String contenttext_today;
    String contenttext_release;
    public String TITLE_MOV;

    List<MovieItem> nowMovie = new ArrayList<>();
    List<MovieItem> todayMovie = new ArrayList<>();
    Call<MovieRes> call;
    private ClientAPI clientAPI = null;

    private static final String API_KEY = BuildConfig.API_KEY;
    private static final String LANG = "en-us";
    private int currPage = 1;

    int count =0;

    public ReleaseTodayReceiver(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        title = intent.getStringExtra("movieTitle");
        contenttext_today = context.getString(R.string.contenttext_today);
        contenttext_release = context.getString(R.string.contenttext_release);
        notificationId = intent.getIntExtra("id",0);

        getToday(context);
    }

//    public void todayMovie(List<MovieItem> todayMov){
//        this.todayMovie = todayMov;
//    }


    public void showAlarm(Context context, String title, int notificationId){
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_movie)
                .setContentTitle(title)
                .setContentText(contenttext_today + " " + title + " " + contenttext_release)
                .setAutoCancel(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);
            if(notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }
        Notification notification = mBuilder.build();
        if(notificationManager!=null){
            notificationManager.notify(notificationId, notification);
        }
    }

    public void setAlarm(Context context, String title, int notificationId){
        unsetAlarm(context);
        int delay = 0;

            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(context, ReleaseTodayReceiver.class);
            intent.putExtra("movieTitle",title);
            intent.putExtra("id",notificationId);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 00);
            calendar.set(Calendar.SECOND,0);

            if(alarmManager!=null){
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + delay, AlarmManager.INTERVAL_DAY, pendingIntent);
            }

//            notificationId++;
//            delay+=1000;
    }

    public void unsetAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if(alarmManager!=null){
            alarmManager.cancel(getPendingIntent(context));
        }
    }

    public static PendingIntent getPendingIntent(Context context){
        Intent pendingIntent = new Intent(context, DailyNotificationReceiver.class);
        return  PendingIntent.getBroadcast(context,NOTIFICATION_ID,pendingIntent,PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public void getToday(final Context context){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String currDate = simpleDateFormat.format(date);
        clientAPI = ClientAPI.getInstance();
        call = clientAPI.getApi().getNowPlaying(API_KEY,LANG,currPage);
        call.enqueue(new Callback<MovieRes>() {
            @Override
            public void onResponse(Call<MovieRes> call, Response<MovieRes> response) {
                if(response.isSuccessful()){
//                    List<MovieItem> todayMov = response.body().getResult();
                    nowMovie = response.body().getResult();
                    for(int i=0; i<nowMovie.size(); i++){
                        MovieItem mov = nowMovie.get(i);
                        Date movDate = date(mov.getRelease_date());

                        if(movDate.equals(date(currDate))){
                            TITLE_MOV = mov.getTitle();
                            showAlarm(context, TITLE_MOV,NOTIFICATION_ID);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieRes> call, Throwable t) {

            }
        });
    }

    public Date date(String movDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = null;
        try {
            date = simpleDateFormat.parse(movDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }
}
