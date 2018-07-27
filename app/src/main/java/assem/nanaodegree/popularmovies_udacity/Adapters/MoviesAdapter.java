package assem.nanaodegree.popularmovies_udacity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import assem.nanaodegree.popularmovies_udacity.Models.MovieModel;
import assem.nanaodegree.popularmovies_udacity.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesHolder> {

    Context context;
    ArrayList<MovieModel> moviesArrayList;

    public MoviesAdapter(Context context, ArrayList<MovieModel> moviesArrayList) {
        this.context = context;
        this.moviesArrayList = moviesArrayList;
    }

    @NonNull
    @Override
    public MoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        MoviesHolder holder = new MoviesHolder(item);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesHolder holder, int position) {

        final MovieModel movieModel = moviesArrayList.get(position);
        Picasso.get()
                .load("http://image.tmdb.org/t/p/w342/" + movieModel.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.movieImg);

        holder.movieImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, movieModel.getTitle(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }

    public class MoviesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_movie_img)
        ImageView movieImg;

        public MoviesHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
