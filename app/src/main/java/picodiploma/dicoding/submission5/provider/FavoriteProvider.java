package picodiploma.dicoding.submission5.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import picodiploma.dicoding.submission5.database.DatabaseContract;
import picodiploma.dicoding.submission5.database.FavoriteHelper;

import static picodiploma.dicoding.submission5.database.DatabaseContract.AUTHORITY;
import static picodiploma.dicoding.submission5.database.DatabaseContract.CONTENT_URI;

public class FavoriteProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_FAVORITE,MOVIE);

        sUriMatcher.addURI(AUTHORITY,DatabaseContract.TABLE_FAVORITE+ "/#", MOVIE_ID);
    }

    private FavoriteHelper favHelper;

    @Override
    public boolean onCreate() {
        favHelper = new FavoriteHelper(getContext());
        favHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                cursor = favHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = favHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if(cursor!=null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long add;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                add = favHelper.insertProvider(values);
                break;
            default:
                add=0;
                break;
        }
        if(add>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return Uri.parse(CONTENT_URI + "/" + add);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int del;
        switch (sUriMatcher.match(uri)){
            case MOVIE_ID:
                del = favHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                del=0;
                break;
        }
        if(del>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return del;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int update;
        switch (sUriMatcher.match(uri)){
            case MOVIE_ID:
                update = favHelper.updateProvider(uri.getLastPathSegment(),values);
                break;
            default:
                update=0;
                break;
        }
        if(update > 0 ){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return update;
    }
}
