package picodiploma.dicoding.submission5.database;


import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static String AUTHORITY = "picodiploma.dicoding.submission5";
    private static final String SCHEME = "content";

    public static String TABLE_FAVORITE = "favorite";

    public static final class FavoriteColumns implements BaseColumns {
        public static String MOVIE_ID = "movie_id";
        public static String TITLE = "title";
        public static String RELEASE_DATE = "release_date";
        public static String OVERVIEW = "overview";
        public static String POSTER_PATH = "poster_path";
        public static String BACKDROP_PATH = "backdrop_path";

    }

    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static double getColumnDouble(Cursor cursor, String columnName){
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }




    }//last
