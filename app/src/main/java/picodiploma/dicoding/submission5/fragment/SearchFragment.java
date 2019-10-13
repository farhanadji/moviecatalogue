package picodiploma.dicoding.submission5.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import picodiploma.dicoding.submission5.BuildConfig;
import picodiploma.dicoding.submission5.R;
import picodiploma.dicoding.submission5.adapter.MovieAdapter;
import picodiploma.dicoding.submission5.adapter.MovieRes;
import picodiploma.dicoding.submission5.api.ClientAPI;
import picodiploma.dicoding.submission5.detail.MovieItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    EditText edtSearch;
    Button btnSearch;
    ProgressBar progressBar;
    private int currPage = 1;
    private String LANG = "en-US";
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private ClientAPI apiClient = null;
    private ArrayList<MovieItem> moviesItem = new ArrayList<>();
    private String API_KEY = BuildConfig.API_KEY;
    private static final String KEY_MOVIES = "key_movies";

    private Call<MovieRes> call;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        Objects.requireNonNull((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.search_fragment);
        edtSearch = view.findViewById(R.id.edt_search);
        btnSearch = view.findViewById(R.id.btn_search);
        progressBar = view.findViewById(R.id.pb_search);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = view.findViewById(R.id.list_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(savedInstanceState!=null){
            moviesItem = savedInstanceState.getParcelableArrayList(KEY_MOVIES);
            adapter = new MovieAdapter(getContext(),moviesItem);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        }else{
            getSearch();
        }

        return view;
    }

        public void getSearch(){
            apiClient = ClientAPI.getInstance();
            call = apiClient.getApi().getTopRated(API_KEY,LANG,currPage);
            call.enqueue(new Callback<MovieRes>() {
                @Override
                public void onResponse(Call<MovieRes> call, Response<MovieRes> response) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    if(response.isSuccessful()) {
                        moviesItem = response.body().getResult();
                        if (moviesItem != null) {
                            adapter = new MovieAdapter(getContext(), moviesItem);
                            recyclerView.setAdapter(adapter);
                            progressBar.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
                @Override
                public void onFailure(Call<MovieRes> call, Throwable t) {

                }
            });

            btnSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    call = apiClient.getApi().getSearchMovie(API_KEY,LANG,edtSearch.getText().toString());
                    call.enqueue(new Callback<MovieRes>() {
                        @Override
                        public void onResponse(Call<MovieRes> call, Response<MovieRes> response) {
                            if(response.isSuccessful()) {
                                moviesItem = response.body().getResult();
                                if (moviesItem != null) {
                                    adapter = new MovieAdapter(getContext(), moviesItem);
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MovieRes> call, Throwable t) {

                        }
                    });
                }
            });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        outState.putParcelableArrayList(KEY_MOVIES,moviesItem);
        super.onSaveInstanceState(outState);
    }

}
