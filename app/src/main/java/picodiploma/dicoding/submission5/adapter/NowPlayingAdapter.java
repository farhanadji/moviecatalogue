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

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import picodiploma.dicoding.submission5.BuildConfig;
import picodiploma.dicoding.submission5.activity.DetailMovie;
import picodiploma.dicoding.submission5.R;
import picodiploma.dicoding.submission5.detail.MovieItem;

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.ViewHolder> {
    String IMG_URL = BuildConfig.IMG_URL;
    private List<MovieItem> nowPlayingMov;
    private Context context;
    private MovieAdapter adapter;

    public NowPlayingAdapter(Context context , List<MovieItem> nowPlayingMov){
        this.context = context;
        this.nowPlayingMov = nowPlayingMov;
    }

    public void refill(ArrayList<MovieItem> nowPlayingMov){
        this.nowPlayingMov = new ArrayList<>();
        this.nowPlayingMov.addAll(nowPlayingMov);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView movtitle;
        TextView desc;
        ImageView covermovie;
        CardView cardView;
        public ViewHolder(View view) {
            super(view);
            movtitle = itemView.findViewById(R.id.tv_movietitle);
            desc = itemView.findViewById(R.id.tv_moviedesc);
            covermovie = itemView.findViewById(R.id.iv_movieimage);
            cardView = itemView.findViewById(R.id.item_mv_cardview);
        }
    }


    @NonNull
    @Override
    public NowPlayingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final int movid = nowPlayingMov.get(i).getMovie_id();
        viewHolder.movtitle.setText(nowPlayingMov.get(i).getTitle());
        viewHolder.desc.setText(nowPlayingMov.get(i).getOverview());
        Picasso.get().load(IMG_URL + "w185"+nowPlayingMov.get(i)
                .getPosterPath()).resize(200,250).into(viewHolder.covermovie);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moviedetail = new Intent(context, DetailMovie.class);
                moviedetail.putExtra(DetailMovie.movieID,movid);
                context.startActivity(moviedetail);

            }
        });
    }

    @Override
    public int getItemCount() {
        return nowPlayingMov.size();
    }

}
