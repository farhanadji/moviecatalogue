package picodiploma.dicoding.favorite;

import androidx.appcompat.app.AppCompatActivity;
import picodiploma.dicoding.favorite.api.clientAPI;
import picodiploma.dicoding.favorite.detail.MovieItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailMovie extends AppCompatActivity {
    public static String MOVIE_ID = "movie_id";
    private String IMG_URL = BuildConfig.IMG_URL;
    private final static String API_KEY = BuildConfig.API_KEY;
    private ImageView backdrop;
    private ImageView postermovie;
    private TextView titleMovie;
    private TextView description;
    private TextView ReleaseDate;
    private int mov_id;
    clientAPI APIClient = null;
    private Call<MovieItem> callDetail;
    private MovieItem detailMovie;
    private MovieItem movieItem;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        backdrop = findViewById(R.id.iv_backdrop);
        postermovie = findViewById(R.id.iv_poster);
        titleMovie = findViewById(R.id.tv_titleMovie);
        ReleaseDate = findViewById(R.id.year);
        description = findViewById(R.id.tv_desc);
        progressBar = findViewById(R.id.pb_detail);

        progressBar.setVisibility(View.VISIBLE);

        mov_id = getIntent().getIntExtra(MOVIE_ID, mov_id);
        Log.d("get id : ", " " + mov_id);
        APIClient = clientAPI.getInstance();
        callDetail = APIClient.getApi().getDetailMovie(mov_id, API_KEY);
        callDetail.enqueue(new Callback<MovieItem>() {
            @Override
            public void onResponse(Call<MovieItem> call, Response<MovieItem> response) {
                if (response.isSuccessful()) {
                    detailMovie = response.body();
                    String poster_path = IMG_URL + "w342" + detailMovie.getPosterPath();
                    String backdrop_path = IMG_URL + "original" + detailMovie.getBackdropPath();

                    Log.d("link : ","link: "+ poster_path);
                    Objects.requireNonNull(getSupportActionBar()).setTitle(detailMovie.getTitle());
                    titleMovie.setText(detailMovie.getTitle());
                    ReleaseDate.setText(detailMovie.getRelease_date());
                    description.setText(detailMovie.getOverview());
                    Picasso.get().load(poster_path).into(postermovie);
                    Picasso.get().load(backdrop_path).fit().into(backdrop);
                    progressBar.setVisibility(View.GONE);
                } else {

                }

            }

            @Override
            public void onFailure(Call<MovieItem> call, Throwable t) {

            }
        });
    }
}

