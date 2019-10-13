package picodiploma.dicoding.submission5.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import picodiploma.dicoding.submission5.detail.MovieItem;

import static android.provider.BaseColumns._ID;
import static picodiploma.dicoding.submission5.database.DatabaseContract.FavoriteColumns.BACKDROP_PATH;
import static picodiploma.dicoding.submission5.database.DatabaseContract.FavoriteColumns.MOVIE_ID;
import static picodiploma.dicoding.submission5.database.DatabaseContract.FavoriteColumns.OVERVIEW;
import static picodiploma.dicoding.submission5.database.DatabaseContract.FavoriteColumns.POSTER_PATH;
import static picodiploma.dicoding.submission5.database.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static picodiploma.dicoding.submission5.database.DatabaseContract.FavoriteColumns.TITLE;
import static picodiploma.dicoding.submission5.database.DatabaseContract.TABLE_FAVORITE;

public class FavoriteHelper {
    private static final String DATABASE_TABLE = TABLE_FAVORITE;
    private static DatabaseHelper dataBaseHelper;
    private static FavoriteHelper INSTANCE;
    private static SQLiteDatabase database;

    public FavoriteHelper(Context context){
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context){
        if(INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if(INSTANCE == null){
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }


    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close(){
        dataBaseHelper.close();

        if(database.isOpen()){
            database.close();
        }
    }

    //CRUD
    public ArrayList<MovieItem> getAllMovie(){
        ArrayList<MovieItem> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID+" DESC",
                null);
        cursor.moveToFirst();
        MovieItem movieItem;
        if(cursor.getCount() > 0){
            do{
                movieItem = new MovieItem();
                movieItem.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_PATH)));
                movieItem.setMovie_id(cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID)));
                movieItem.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movieItem.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                movieItem.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movieItem.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));

                arrayList.add(movieItem);
                cursor.moveToNext();

            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovie(MovieItem movieItem){
        ContentValues initialValue = new ContentValues();
        initialValue.put(MOVIE_ID, movieItem.getMovie_id());
        initialValue.put(TITLE,movieItem.getTitle());
        initialValue.put(RELEASE_DATE,movieItem.getRelease_date());
        initialValue.put(OVERVIEW,movieItem.getOverview());
        initialValue.put(POSTER_PATH,movieItem.getPosterPath());
        initialValue.put(BACKDROP_PATH,movieItem.getBackdropPath());

        return database.insert(DATABASE_TABLE,null, initialValue);
    }

    public int updateMovie(MovieItem movieItem){
        ContentValues args = new ContentValues();
        args.put(MOVIE_ID, movieItem.getMovie_id());
        args.put(TITLE,movieItem.getTitle());
        args.put(RELEASE_DATE,movieItem.getRelease_date());
        args.put(OVERVIEW,movieItem.getOverview());
        args.put(POSTER_PATH,movieItem.getPosterPath());
        args.put(BACKDROP_PATH,movieItem.getBackdropPath());

        return database.update(DATABASE_TABLE,args,MOVIE_ID + "= '" + movieItem.getMovie_id() + "'", null);
    }

    public int deleteMovie(int id){
        return database.delete(DATABASE_TABLE,MOVIE_ID + "= '" + id + "'",null);
    }


    public boolean checkId(int mov_id){
        Cursor cursor = null;
        String sql = "SELECT MOVIE_ID FROM "+DATABASE_TABLE+ " WHERE MOVIE_ID="+mov_id;
        cursor = database.rawQuery(sql,null);

        if(cursor.getCount()>0){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,_ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
        ,null
        ,null
        ,null
        ,null
        ,null
        ,_ID + " ASC");
    }

    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }

    public int updateProvider(String id, ContentValues values){
        return database.update(DATABASE_TABLE,values, _ID + " = ?",
                new String[]{id});
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE, _ID + " = ?",
                new String[]{id});
    }

}//last
