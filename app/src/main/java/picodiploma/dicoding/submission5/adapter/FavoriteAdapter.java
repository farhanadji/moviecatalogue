package picodiploma.dicoding.submission5.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import androidx.annotation.NonNull;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import picodiploma.dicoding.submission5.BuildConfig;
import picodiploma.dicoding.submission5.activity.DetailMovie;
import picodiploma.dicoding.submission5.R;
import picodiploma.dicoding.submission5.detail.MovieItem;

import static picodiploma.dicoding.submission5.database.DatabaseContract.CONTENT_URI;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    String IMG_URL = BuildConfig.IMG_URL;
    private Cursor favoriteList;
    private Context context;

    private ArrayList<MovieItem> listMovie = new ArrayList<>();
    private Activity activity;

    public FavoriteAdapter(Context context, ArrayList<MovieItem> mov) {
        this.context = context;
        this.listMovie = mov;
    }

    public void setListFavorite(ArrayList<MovieItem> listMovie){
        if(listMovie.size() > 0){
            this.listMovie.addAll(listMovie);
        }
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
        final int movid = listMovie.get(position).getMovie_id();
        holder.movtitle.setText(listMovie.get(position).getTitle());
        holder.desc.setText(listMovie.get(position).getOverview());
        Picasso.get().load(IMG_URL+ "w185" +
                listMovie.get(position).getPosterPath()).resize(200, 250).into(holder.covermovie);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moviedetail = new Intent(context, DetailMovie.class);
                Uri uri = Uri.parse(CONTENT_URI + "/" + movid);
                moviedetail.putExtra(DetailMovie.movieID, movid);
                moviedetail.setData(uri);
                context.startActivity(moviedetail);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
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
