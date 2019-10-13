package picodiploma.dicoding.submission5;

import android.graphics.Movie;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.util.Log;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import butterknife.BindString;
import butterknife.ButterKnife;
import picodiploma.dicoding.submission5.api.ClientAPI;
import picodiploma.dicoding.submission5.adapter.MovieRes;
import picodiploma.dicoding.submission5.detail.MovieItem;
import picodiploma.dicoding.submission5.detail.MovieResult;
import picodiploma.dicoding.submission5.notification.DailyNotificationReceiver;
import picodiploma.dicoding.submission5.notification.ReleaseTodayReceiver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwitchPreferences extends PreferenceFragment implements Preference.OnPreferenceChangeListener{
    @BindString(R.string.daily_reminder)
    String keyDaily;

    @BindString(R.string.release_reminder)
    String keyRelease;
    List<MovieItem> nowMovie = new ArrayList<>();
//    Call<MovieRes> call;
//    private ClientAPI clientAPI = null;
//
//    private static final String API_KEY = BuildConfig.API_KEY;
//    private static final String LANG = "en-us";
//    private int currPage = 1;

    private DailyNotificationReceiver dailyNotificationReceiver = new DailyNotificationReceiver();
    private ReleaseTodayReceiver releaseTodayReceiver = new ReleaseTodayReceiver();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        ButterKnife.bind(this, getActivity());

        SwitchPreference switchDaily = (SwitchPreference)findPreference(keyDaily);
        switchDaily.setOnPreferenceChangeListener(this);

        SwitchPreference switchRelease = (SwitchPreference)findPreference(keyRelease);
        switchRelease.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        boolean cek = (boolean) newValue;
        if (key.equals(keyDaily)) {
            if (cek) {
                dailyNotificationReceiver.setRepeatAlarm(getActivity());
                Log.d("test","berhasil");
            } else {
                dailyNotificationReceiver.unsetAlarm(getActivity());
            }
        }else{
            if(cek){
                releaseTodayReceiver.setAlarm(getActivity(), releaseTodayReceiver.TITLE_MOV, 101);
            }else{
                releaseTodayReceiver.unsetAlarm(getActivity());
            }
        }
        return true;
      }

//    public void getToday(){
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        Date date = new Date();
//        String currDate = simpleDateFormat.format(date);
//
//        clientAPI = ClientAPI.getInstance();
//        call = clientAPI.getApi().getNowPlaying(API_KEY,LANG,currPage);
//        call.enqueue(new Callback<MovieRes>() {
//            @Override
//            public void onResponse(Call<MovieRes> call, Response<MovieRes> response) {
//                if(response.isSuccessful()){
//                    List<MovieItem> todayMov = response.body().getResult();
//                    nowMovie = response.body().getResult();
//                    for(int i=0; i<nowMovie.size(); i++){
//                        MovieItem mov = nowMovie.get(i);
//                        Date movDate = date(mov.getRelease_date());
//
//                        if(movDate.equals(date(currDate))){
//                            todayMov.add(mov);
//                        }
//                    }
//                    releaseTodayReceiver.setAlarm(getActivity(),todayMov);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieRes> call, Throwable t) {
//
//            }
//        });
//
//    }
//
//    public Date date(String movDate){
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        Date date = null;
//        try {
//            date = simpleDateFormat.parse(movDate);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return date;
//    }
}
