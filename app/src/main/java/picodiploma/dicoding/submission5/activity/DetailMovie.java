package picodiploma.dicoding.submission5.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import androidx.core.content.ContextCompat;
import picodiploma.dicoding.submission5.BuildConfig;
import picodiploma.dicoding.submission5.R;
import picodiploma.dicoding.submission5.api.ClientAPI;
import picodiploma.dicoding.submission5.database.FavoriteHelper;
import picodiploma.dicoding.submission5.detail.MovieItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMovie extends AppCompatActivity {
    public static String movieID;
    public String TAG = "detail movie";
    ClientAPI APIClient = null;
    private int mov_id;
    private Call<MovieItem> callDetail;
    private String IMG_URL = BuildConfig.IMG_URL;
    private MovieItem detailMovie;
    private FavoriteHelper favoriteHelper;
    private MovieItem favorite;
    private boolean setFavorite = false;
    private boolean setFavorite2 = false;
    private final AppCompatActivity activity = DetailMovie.this;
    private final static String API_KEY = BuildConfig.API_KEY;
    String titleToast = "title";
    ImageView backdrop;
    ImageView postermovie;
    TextView titleMovie;
    TextView description;
    TextView ReleaseDate;
    Menu menu;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        backdrop = findViewById(R.id.iv_backdrop);
        postermovie = findViewById(R.id.iv_poster);
        titleMovie = findViewById(R.id.tv_titleMovie);
        ReleaseDate = findViewById(R.id.year);
        description = findViewById(R.id.tv_desc);
        progressBar = findViewById(R.id.pb_nowplaying);
        progressBar.setVisibility(View.VISIBLE);

        mov_id = getIntent().getIntExtra(movieID, mov_id);
        favoriteHelper = new FavoriteHelper(activity);
        favoriteHelper.open();
        if(favoriteHelper.checkId(mov_id)){
            setFavorite = true;
        }else{
            setFavorite = false;
        }


        Log.d(TAG, "Received Id :" + mov_id);
        APIClient = ClientAPI.getInstance();
        callDetail = APIClient.getApi().getDetailMovie(mov_id, API_KEY);
        callDetail.enqueue(new Callback<MovieItem>() {
            @Override
            public void onResponse(Call<MovieItem> call, Response<MovieItem> response) {
                if (response.isSuccessful()) {
                    detailMovie = response.body();
                    String poster_path = IMG_URL + "w342" + detailMovie.getPosterPath();
                    String backdrop_path = IMG_URL + "original" + detailMovie.getBackdropPath();

                    titleToast = detailMovie.getTitle();
                    Objects.requireNonNull(getSupportActionBar()).setTitle(detailMovie.getTitle());
                    titleMovie.setText(detailMovie.getTitle());
                    ReleaseDate.setText(detailMovie.getRelease_date());
                    description.setText(detailMovie.getOverview());
                    Picasso.get().load(poster_path).into(postermovie);
                    Picasso.get().load(backdrop_path).fit().into(backdrop);

                } else {
                    Log.d(TAG, "fail");
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieItem> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        this.menu = menu;
        getMenuInflater().inflate(R.menu.favorite,menu);
        invalidateOptionsMenu();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem fav = menu.findItem(R.id.fav_button_clicked);
        MenuItem unfav = menu.findItem(R.id.fav_button);

        fav.setVisible(setFavorite);
        unfav.setVisible(!setFavorite);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.fav_button){
            setFavorite = true;
            saveFavorite();
        }else if(item.getItemId() == R.id.fav_button_clicked){
            setFavorite2 = true;
            saveFavorite();
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveFavorite(){
        favoriteHelper = new FavoriteHelper(activity);
        favorite = new MovieItem();
        if(favoriteHelper.checkId(mov_id)){
            favoriteHelper.open();
            favoriteHelper.deleteMovie(mov_id);
            Toast.makeText(activity, titleToast+ " " + getString(R.string.toast_fav_remove), Toast.LENGTH_LONG).show();
            setFavoriteIconState2();
        }else {
            favorite.setMovie_id(mov_id);
            favorite.setRelease_date(detailMovie.getRelease_date());
            favorite.setOverview(detailMovie.getOverview());
            favorite.setPosterPath(detailMovie.getPosterPath());
            favorite.setBackdropPath(detailMovie.getBackdropPath());
            favorite.setTitle(detailMovie.getTitle());

            favoriteHelper.open();
            favoriteHelper.insertMovie(favorite);
            Toast.makeText(activity, titleToast+ " " + getString(R.string.toast_fav_add), Toast.LENGTH_SHORT).show();
            setFavoriteIconState();
        }
    }


    public void checkFavorite(){

    }

    private void setFavoriteIconState() {
        if(setFavorite) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.fav_button_clicked));
        } else {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.fav_button));
        }
    }

    private void setFavoriteIconState2() {
        if(setFavorite2) {
            menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.fav_button));
        } else {
            menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.fav_button_clicked));
        }
    }
}
