package picodiploma.dicoding.favorite.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import picodiploma.dicoding.favorite.DetailMovie;
import picodiploma.dicoding.favorite.R;
import picodiploma.dicoding.favorite.detail.MovieItem;

import static picodiploma.dicoding.favorite.database.DatabaseContract.CONTENT_URI;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Cursor favList;
    private Context context;
    private ArrayList<MovieItem> movieItems = new ArrayList<>();

    public FavoriteAdapter(Cursor favList,Context context){
        this.context = context;
        setFavList(favList);
    }
    public void setFavList(Cursor favList){
        this.favList = favList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_list, parent, false);
        return new FavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, final int position) {
        final MovieItem movResult = getPos(position);
        holder.movtitle.setText(movResult.getTitle());
        holder.desc.setText(movResult.getOverview());
        Picasso.get().load("https://image.tmdb.org/t/p/w185" +
                movResult.getPosterPath()).resize(200, 250).into(holder.covermovie);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moviedetail = new Intent(context, DetailMovie.class);

                Uri uri = Uri.parse(CONTENT_URI + "/" + movResult.getMovie_id());
                moviedetail.putExtra(DetailMovie.MOVIE_ID, movResult.getMovie_id());
                moviedetail.setData(uri);
                context.startActivity(moviedetail);

            }
        });
    }

    private MovieItem getPos(int position){
        if(!favList.moveToPosition(position)){
            throw new IllegalStateException("error");
        }
        return new MovieItem(favList);
    }

    @Override
    public int getItemCount() {
        if(favList == null) return 0;
       return favList.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView movtitle;
        TextView desc;
        ImageView covermovie;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movtitle = itemView.findViewById(R.id.tv_movietitlefav);
            desc = itemView.findViewById(R.id.tv_moviedescfav);
            covermovie = itemView.findViewById(R.id.iv_movieimagefav);
            cardView = itemView.findViewById(R.id.item_mv_cardviewfav);

        }
    }
}