package picodiploma.dicoding.favorite;


import android.database.Cursor;

import java.util.ArrayList;

import picodiploma.dicoding.favorite.detail.MovieItem;

public interface LoadMoviesCallback {
    void preExecute();
    void postExecute(Cursor movies);
}
