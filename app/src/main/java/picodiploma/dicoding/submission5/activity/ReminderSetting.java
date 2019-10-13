package picodiploma.dicoding.submission5.activity;

import androidx.appcompat.app.AppCompatActivity;
import picodiploma.dicoding.submission5.R;
import picodiploma.dicoding.submission5.SwitchPreferences;

import android.os.Bundle;
import android.preference.SwitchPreference;

public class ReminderSetting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_setting);

        getFragmentManager().beginTransaction().replace(R.id.fragment_layout, new SwitchPreferences()).commit();
    }
}
