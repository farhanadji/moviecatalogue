package picodiploma.dicoding.submission5.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import picodiploma.dicoding.submission5.LoadMoviesCallback;
import picodiploma.dicoding.submission5.R;
import picodiploma.dicoding.submission5.adapter.FavoriteAdapter;
import picodiploma.dicoding.submission5.database.FavoriteHelper;
import picodiploma.dicoding.submission5.detail.MovieItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    private FavoriteAdapter adapter;
    private RecyclerView rvFavorite;
    FavoriteHelper favHelper;
    TextView empty;
    private ArrayList<MovieItem> moviesItem = new ArrayList<>();
    private static final String KEY_MOVIES = "key_movies";

    public FavoriteFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        Objects.requireNonNull((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.favorite);
        empty = view.findViewById(R.id.empty_movie);

        rvFavorite = view.findViewById(R.id.rv_favorite);
        rvFavorite.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavorite.setHasFixedSize(true);

        favHelper = FavoriteHelper.getInstance(getActivity());
        favHelper.open();
        if(savedInstanceState != null){
            moviesItem = savedInstanceState.getParcelableArrayList(KEY_MOVIES);
            if(!moviesItem.isEmpty()) {
                adapter = new FavoriteAdapter(getContext(), moviesItem);
                adapter.notifyDataSetChanged();
                empty.setVisibility(View.INVISIBLE);
                rvFavorite.setAdapter(adapter);
            }else{
                empty.setVisibility(View.VISIBLE);
            }
        }else{
            if(!favHelper.getAllMovie().isEmpty()) {
                empty.setVisibility(View.INVISIBLE);
                adapter = new FavoriteAdapter(getContext(), favHelper.getAllMovie());
                rvFavorite.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }else{
                empty.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        favHelper = FavoriteHelper.getInstance(getActivity());
        favHelper.open();
        adapter = new FavoriteAdapter(getContext(), favHelper.getAllMovie());
        rvFavorite.setAdapter(adapter);

        if(favHelper.getAllMovie().isEmpty()) {
            empty.setVisibility(View.VISIBLE);
        }else{
            empty.setVisibility(View.INVISIBLE);
        }
    }

    public static class LoadMoviesAsync extends AsyncTask<Void, Void, ArrayList<MovieItem>> {
        private final WeakReference<FavoriteHelper> weakFavHelper;
        private final WeakReference<LoadMoviesCallback> weakCallback;

        private LoadMoviesAsync(FavoriteHelper favHelper, LoadMoviesCallback callback){
            weakFavHelper = new WeakReference<>(favHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<MovieItem> doInBackground(Void... voids) {
            return weakFavHelper.get().getAllMovie();
        }

        @Override
        protected void onPostExecute(ArrayList<MovieItem> movies){
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        outState.putParcelableArrayList(KEY_MOVIES,moviesItem);
        super.onSaveInstanceState(outState);
    }

}