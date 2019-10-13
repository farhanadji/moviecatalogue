package picodiploma.dicoding.submission5.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import picodiploma.dicoding.submission5.R;
import picodiploma.dicoding.submission5.detail.MovieItem;

import static picodiploma.dicoding.submission5.database.DatabaseContract.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final Context mContext;
    private Cursor cursor;
    private int appWidgetId;

    StackRemoteViewsFactory(Context context, Intent intent){
        mContext = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        if(cursor != null){
            cursor.close();
        }

        final long identifyToken = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(CONTENT_URI,null,null,null,null);
        Binder.restoreCallingIdentity(identifyToken);

    }

    @Override
    public void onDestroy() {
        if(cursor!=null) cursor.close();
    }

    @Override
    public int getCount() {
        if(cursor == null) return 0;
        else return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION || cursor == null || !cursor.moveToPosition(position)) {
            return null;
        }

        MovieItem movieItem = getItem(position);
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap bitmap = null;
        String poster_url = "https://image.tmdb.org/t/p/" + "w342" + movieItem.getPosterPath();
        String movTitle = movieItem.getTitle();

        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(poster_url)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

        } catch (InterruptedException | ExecutionException e) {
            Log.d("error", "error bitmap");
        }

        remoteViews.setImageViewBitmap(R.id.imageView,bitmap);

        Bundle extras = new Bundle();
        extras.putString(FavoriteImageWidget.EXTRA_ITEM, movTitle);

        Intent intent = new Intent();
        intent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.imageView, intent);

        return remoteViews;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        if(cursor.moveToPosition(position)){
            return cursor.getLong(0);
        }else{
            return position;
        }
    }

    public MovieItem getItem(int pos){
        if(!cursor.moveToPosition(pos)){
            throw new IllegalStateException("error");
        }
        return new MovieItem(cursor);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
