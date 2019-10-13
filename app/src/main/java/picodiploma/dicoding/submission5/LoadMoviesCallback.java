package picodiploma.dicoding.submission5;

import java.util.ArrayList;

import picodiploma.dicoding.submission5.detail.MovieItem;

public interface LoadMoviesCallback {
    void preExecute();
    void postExecute(ArrayList<MovieItem> movies);
}
