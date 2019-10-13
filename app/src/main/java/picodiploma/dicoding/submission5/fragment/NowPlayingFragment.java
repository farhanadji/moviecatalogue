package picodiploma.dicoding.submission5.fragment;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import picodiploma.dicoding.submission5.BuildConfig;
import picodiploma.dicoding.submission5.R;
import picodiploma.dicoding.submission5.adapter.MovieAdapter;
import picodiploma.dicoding.submission5.adapter.MovieRes;
import picodiploma.dicoding.submission5.api.ClientAPI;
import picodiploma.dicoding.submission5.database.FavoriteHelper;
import picodiploma.dicoding.submission5.detail.MovieItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends androidx.fragment.app.Fragment {
    private int currPage = 1;
    private String LANG = "en-US";
    private MovieAdapter adapter;
    private RecyclerView rvNowPlaying;
    private ClientAPI clientApi = null;
    private ArrayList<MovieItem> moviesItem = new ArrayList<>();
    private Call<MovieRes> call;
    private final static String API_KEY = BuildConfig.API_KEY;
    private static final String KEY_MOVIES = "key_movies";
    ProgressBar progressBar;
    FavoriteHelper favHelper;

    public NowPlayingFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favHelper = FavoriteHelper.getInstance(getActivity());
        favHelper.open();
        if(savedInstanceState!=null){
            moviesItem = savedInstanceState.getParcelableArrayList(KEY_MOVIES);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        progressBar = view.findViewById(R.id.pb_nowplaying);
        progressBar.setVisibility(View.VISIBLE);
        Objects.requireNonNull((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.nowplaying);
        rvNowPlaying = view.findViewById(R.id.now_playing);
        rvNowPlaying.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(savedInstanceState != null){
            progressBar.setVisibility(View.GONE);
            moviesItem = savedInstanceState.getParcelableArrayList(KEY_MOVIES);
            adapter = new MovieAdapter(getContext(),moviesItem);
            rvNowPlaying.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            getNowPlayingMovie();
        }
        return view;
    }

    public void getNowPlayingMovie(){
        clientApi = ClientAPI.getInstance();
        call = clientApi.getApi().getNowPlaying(API_KEY,LANG,currPage);
        call.enqueue(new Callback<MovieRes>() {
            @Override
            public void onResponse(Call<MovieRes> call, Response<MovieRes> response) {
                if(response.isSuccessful()){
                    moviesItem = response.body().getResult();
                    if(moviesItem != null){
                        progressBar.setVisibility(View.GONE);
                        adapter = new MovieAdapter(getActivity(), moviesItem);
                        rvNowPlaying.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(getActivity(),"Movie not found!", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieRes> call, Throwable t) {

            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        outState.putParcelableArrayList(KEY_MOVIES,moviesItem);
        super.onSaveInstanceState(outState);
    }




}