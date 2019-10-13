package picodiploma.dicoding.submission5.api;

import picodiploma.dicoding.submission5.adapter.MovieRes;
import picodiploma.dicoding.submission5.detail.MovieItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface movieAPI {
    @GET("movie/popular")
    Call<MovieRes> getPopular(
            @Query("api_key") String API_KEY,
            @Query("language") String LANG,
            @Query("page") int currPage
    );

    @GET("movie/top_rated")
    Call<MovieRes> getTopRated(
        @Query("api_key") String api_key,
        @Query("language") String lang,
        @Query("page") int currPage
    );

    @GET("movie/{movie_id}")
    Call<MovieItem> getDetailMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    @GET("search/movie")
    Call<MovieRes> getSearchMovie(
            @Query("api_key") String api_key,
            @Query("language") String lang,
            @Query("query") String search
    );

    @GET("movie/now_playing")
    Call<MovieRes> getNowPlaying(
            @Query("api_key") String API_KEY,
            @Query("language") String LANG,
            @Query("page") int currPage
    );

    @GET("movie/upcoming")
    Call<MovieRes> getUpComing(
            @Query("api_key") String API_KEY,
            @Query("language") String LANG,
            @Query("page") int currPage
    );

}