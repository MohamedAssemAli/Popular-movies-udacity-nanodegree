package assem.nanaodegree.popularmovies_udacity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import assem.nanaodegree.popularmovies_udacity.Activities.DetailsActivity;
import assem.nanaodegree.popularmovies_udacity.App.AppConfig;
import assem.nanaodegree.popularmovies_udacity.Models.MovieModel;
import assem.nanaodegree.popularmovies_udacity.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesHolder> {

    Context context;
    ArrayList<MovieModel> moviesArrayList;
    AppConfig appConfig;

    public MoviesAdapter(Context context, ArrayList<MovieModel> moviesArrayList) {
        this.context = context;
        this.moviesArrayList = moviesArrayList;
        appConfig = new AppConfig();
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
                .load(appConfig.getIMG_END_POINT() + movieModel.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.movieImg);

        holder.movieImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                String movieObjectAsAString = new Gson().toJson(movieModel);
                intent.putExtra("movieObj", movieObjectAsAString);
                context.startActivity(intent);
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
