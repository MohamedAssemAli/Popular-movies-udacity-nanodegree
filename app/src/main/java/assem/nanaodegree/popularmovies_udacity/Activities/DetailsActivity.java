package assem.nanaodegree.popularmovies_udacity.Activities;

import android.annotation.SuppressLint;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import assem.nanaodegree.popularmovies_udacity.Adapters.ReviewsAdapter;
import assem.nanaodegree.popularmovies_udacity.Adapters.TrailersAdapter;
import assem.nanaodegree.popularmovies_udacity.App.AppConfig;
import assem.nanaodegree.popularmovies_udacity.App.MyApplication;
import assem.nanaodegree.popularmovies_udacity.Models.MovieModel;
import assem.nanaodegree.popularmovies_udacity.Models.ReviewModel;
import assem.nanaodegree.popularmovies_udacity.Models.TrailerModel;
import assem.nanaodegree.popularmovies_udacity.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    // TAG for debugging
    private final String TAG = DetailsActivity.class.getSimpleName();
    // Vars
    private MovieModel movieModel;
    private AppConfig appConfig;
    private ArrayList<TrailerModel> trailersArrayList;
    private ArrayList<ReviewModel> reviewsArrayList;
    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;
    String movieId;
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
    @BindView(R.id.trailers_recycler)
    RecyclerView trailersRecycler;
    @BindView(R.id.reviews_recycler)
    RecyclerView reviewsRecycler;
//    @BindView(R.id.progress_bar)
//    ContentLoadingProgressBar progressBar;
//    @BindView(R.id.progress_layout)
//    RelativeLayout progressLayout;

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
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle(movieModel.getOriginalTitle());
            movieId = String.valueOf(movieModel.getId());
            init();
        }
    }

    private void init() {
//        toggleLayout(false);
        populateUI();
        reviewsArrayList = new ArrayList<>();
        trailersArrayList = new ArrayList<>();
        reviewsAdapter = new ReviewsAdapter(this, reviewsArrayList);
        reviewsRecycler.setAdapter(reviewsAdapter);
        trailersAdapter = new TrailersAdapter(this, trailersArrayList);
        trailersRecycler.setAdapter(trailersAdapter);
        getReviews(movieId);
        getTrailers(movieId);
    }

    @SuppressLint("SetTextI18n")
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

    private void getTrailers(String movieId) {
        String url = appConfig.getBASE_URL() + movieId + appConfig.getVIEDOS() + appConfig.getAPI_KEY();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray(appConfig.getRESULTS_ARRAY());
                            List<TrailerModel> trailer =
                                    new Gson().fromJson(array.toString(), new TypeToken<List<TrailerModel>>() {
                                    }.getType());
                            // adding trailer to list
                            trailersArrayList.clear();
                            trailersArrayList.addAll(trailer);
                            // refreshing recycler view
                            trailersAdapter.notifyDataSetChanged();
//                            toggleLayout(true);
//                            noConnectionLayout.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            Log.d(TAG, "parseMovies called : catch exception :: " + e.toString());
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "parseMovies called : catch onErrorResponse :: " + error.toString());
                Toast.makeText(DetailsActivity.this, "Error : " + error.getMessage(), Toast.LENGTH_LONG).show();
//                noConnectionLayout.setVisibility(View.VISIBLE);
            }
        });
        MyApplication.getInstance(this).addToRequestQueue(request);
    }


    private void getReviews(String movieId) {
        String url = appConfig.getBASE_URL() + movieId + appConfig.getREVIEWS() + appConfig.getAPI_KEY();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray(appConfig.getRESULTS_ARRAY());
                            List<ReviewModel> review =
                                    new Gson().fromJson(array.toString(), new TypeToken<List<ReviewModel>>() {
                                    }.getType());
                            // adding reviews to list
                            reviewsArrayList.clear();
                            reviewsArrayList.addAll(review);
                            // refreshing recycler view
                            reviewsAdapter.notifyDataSetChanged();

//                            toggleLayout(true);
//                            noConnectionLayout.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            Log.d(TAG, "parseMovies called : catch exception :: " + e.toString());
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "parseMovies called : catch onErrorResponse :: " + error.toString());
                Toast.makeText(DetailsActivity.this, "Error : " + error.getMessage(), Toast.LENGTH_LONG).show();
//                noConnectionLayout.setVisibility(View.VISIBLE);
            }
        });
        MyApplication.getInstance(this).addToRequestQueue(request);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

//    private void toggleLayout(boolean flag) {
//        if (flag) {
////            elementaryLevelRecyclerView.setVisibility(View.VISIBLE);
//            progressLayout.setVisibility(View.GONE);
//            progressBar.hide();
//        } else {
//            progressLayout.setVisibility(View.VISIBLE);
//            progressBar.show();
////            elementaryLevelRecyclerView.setVisibility(View.GONE);
//        }
//    }

}
