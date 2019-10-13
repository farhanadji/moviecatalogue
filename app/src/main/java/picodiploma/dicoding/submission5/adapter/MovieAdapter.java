package picodiploma.dicoding.submission5.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import picodiploma.dicoding.submission5.BuildConfig;
import picodiploma.dicoding.submission5.activity.DetailMovie;
import picodiploma.dicoding.submission5.R;
import picodiploma.dicoding.submission5.detail.MovieItem;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    String IMG_URL = BuildConfig.IMG_URL;
    private List<MovieItem> mov;
    private Context context;

    public MovieAdapter(Context context, List<MovieItem> mov) {
        this.context = context;
        this.mov = mov;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_list, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder movieViewHolder, int i) {
        final int movid = mov.get(i).getMovie_id();
        movieViewHolder.movtitle.setText(mov.get(i).getTitle());
        movieViewHolder.desc.setText(mov.get(i).getOverview());
        Picasso.get().load(IMG_URL+ "w185" + mov.get(i)
                .getPosterPath()).resize(200, 250).into(movieViewHolder.covermovie);

        movieViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moviedetail = new Intent(context, DetailMovie.class);
                moviedetail.putExtra(DetailMovie.movieID, movid);
                context.startActivity(moviedetail);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mov.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView movtitle;
        TextView desc;
        ImageView covermovie;
        CardView cardView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movtitle = itemView.findViewById(R.id.tv_movietitle);
            desc = itemView.findViewById(R.id.tv_moviedesc);
            covermovie = itemView.findViewById(R.id.iv_movieimage);
            cardView = itemView.findViewById(R.id.item_mv_cardview);
        }
    }
}
