package picodiploma.dicoding.favorite.detail;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import picodiploma.dicoding.favorite.database.DatabaseContract;

import static picodiploma.dicoding.favorite.database.DatabaseContract.getColumnInt;
import static picodiploma.dicoding.favorite.database.DatabaseContract.getColumnString;


public class MovieItem {

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("id")
    @Expose
    private int movie_id;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("release_date")
    @Expose
    private String release_date;

    @SerializedName("title")
    @Expose
    private String title;

    public MovieItem(){

    }

    public MovieItem(String backdropPath, int movie_id, String overview, String release_date, String title, String posterPath) {
        this.backdropPath = backdropPath;
        this.movie_id = movie_id;
        this.overview = overview;
        this.release_date = release_date;
        this.title = title;
        this.posterPath = posterPath;
    }

    public MovieItem(Cursor cursor){
        this.movie_id = getColumnInt(cursor, DatabaseContract.FavoriteColumns.MOVIE_ID);
        this.overview = getColumnString(cursor, DatabaseContract.FavoriteColumns.OVERVIEW);
        this.release_date = getColumnString(cursor, DatabaseContract.FavoriteColumns.RELEASE_DATE);
        this.title = getColumnString(cursor, DatabaseContract.FavoriteColumns.TITLE);
        this.posterPath = getColumnString(cursor, DatabaseContract.FavoriteColumns.POSTER_PATH);
        this.backdropPath = getColumnString(cursor, DatabaseContract.FavoriteColumns.BACKDROP_PATH);
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}