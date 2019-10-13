package picodiploma.dicoding.favorite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import picodiploma.dicoding.favorite.adapter.FavoriteAdapter;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import static picodiploma.dicoding.favorite.database.DatabaseContract.CONTENT_URI;


public class MainActivity extends AppCompatActivity{

    FavoriteAdapter favoriteAdapter;
    RecyclerView rvFavorite;
    Cursor list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvFavorite = findViewById(R.id.rv_favorite);

        favoriteAdapter = new FavoriteAdapter(list,this);
        rvFavorite.setLayoutManager(new LinearLayoutManager(this));
        rvFavorite.setAdapter(favoriteAdapter);

        new getFav().execute();
    }

    @Override
    protected void onResume(){
        super.onResume();
        new getFav().execute();
    }

    private class getFav extends AsyncTask<Void, Void, Cursor>{
        @Override
        protected void onPreExecute(){
            Log.d("test","jalan");
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor item){
            super.onPostExecute(item);

            list = item;
            favoriteAdapter.setFavList(list);
        }
    }

}
