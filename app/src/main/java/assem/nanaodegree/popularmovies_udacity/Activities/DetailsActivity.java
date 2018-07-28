package assem.nanaodegree.popularmovies_udacity.Activities;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import assem.nanaodegree.popularmovies_udacity.App.AppConfig;
import assem.nanaodegree.popularmovies_udacity.Models.MovieModel;
import assem.nanaodegree.popularmovies_udacity.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    // Vars
    MovieModel movieModel;
    AppConfig appConfig;

    // ButterKnife
    @BindView(R.id.movie_cover)
    ImageView movieCover;
    @BindView(R.id.movie_poster)
    ImageView moviePoster;
    @BindView(R.id.movie_title)
    TextView movieTitle;
    @BindView(R.id.movie_release_date)
    TextView movieReleaseDate;
    @BindView(R.id.movie_average_rating)
    TextView movieRating;
    @BindView(R.id.movie_overview)
    TextView movieOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        appConfig = new AppConfig();
        String movieObjectAsAString = getIntent().getStringExtra("movieObj");
        movieModel = new Gson().fromJson(movieObjectAsAString, MovieModel.class);
        if (movieModel == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        populateUI();
        getSupportActionBar().setTitle(movieModel.getOriginalTitle());
    }


    private void populateUI() {
        Picasso.get()
                .load(appConfig.getIMG_END_POINT() + movieModel.getBackdropPath())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(movieCover);

        Picasso.get()
                .load(appConfig.getIMG_END_POINT() + movieModel.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(moviePoster);

        movieTitle.setText(movieModel.getOriginalTitle());
        movieOverview.setText(movieModel.getOverview());
        movieRating.setText("Rating : " + String.valueOf(movieModel.getVoteAverage()));
        movieReleaseDate.setText(movieModel.getReleaseDate());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

}
