package picodiploma.dicoding.submission5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import picodiploma.dicoding.submission5.R;
import picodiploma.dicoding.submission5.fragment.FavoriteFragment;
import picodiploma.dicoding.submission5.fragment.NowPlayingFragment;
import picodiploma.dicoding.submission5.fragment.SearchFragment;
import picodiploma.dicoding.submission5.fragment.UpComingFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNav;
    private static final String KEY_FRAGMENT = "key_fragment";
    UpComingFragment upComingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null){
            upComingFragment = (UpComingFragment)getSupportFragmentManager().findFragmentByTag(KEY_FRAGMENT);
        }else if(upComingFragment == null){
            loadFragment(new NowPlayingFragment());
        }

        bottomNav = findViewById(R.id.bottomnav_main);
        bottomNav.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_change_language:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
            case R.id.action_reminder_setting:
                Intent reminderIntent = new Intent(this,ReminderSetting.class);
                startActivity(reminderIntent);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_layer, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()){
            case R.id.now_playing:
                fragment = new NowPlayingFragment();
                break;
            case R.id.up_coming:
                fragment = new UpComingFragment();
                break;
            case R.id.favorite:
                fragment = new FavoriteFragment();
                break;
            case R.id.search_menu:
                fragment = new SearchFragment();
                break;
        }
        return loadFragment(fragment);
    }

}