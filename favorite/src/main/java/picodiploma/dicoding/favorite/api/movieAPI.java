package picodiploma.dicoding.favorite.api;

import picodiploma.dicoding.favorite.detail.MovieItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface movieAPI {

    @GET("movie/{movie_id}")
    Call<MovieItem> getDetailMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

}