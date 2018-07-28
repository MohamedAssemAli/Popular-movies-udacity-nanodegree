package assem.nanaodegree.popularmovies_udacity.Activities;

import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import assem.nanaodegree.popularmovies_udacity.Helper.PrefManager;
import assem.nanaodegree.popularmovies_udacity.Adapters.MoviesAdapter;
import assem.nanaodegree.popularmovies_udacity.App.AppConfig;
import assem.nanaodegree.popularmovies_udacity.App.MyApplication;
import assem.nanaodegree.popularmovies_udacity.Models.MovieModel;
import assem.nanaodegree.popularmovies_udacity.R;
import assem.nanaodegree.popularmovies_udacity.Utils.ConnectivityReceiver;
import assem.nanaodegree.popularmovies_udacity.Utils.ConnectivityReceiver.ConnectivityReceiverListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements ConnectivityReceiverListener {

    // TAG for debugging
    private final String TAG = MainActivity.class.getSimpleName();
    // Vars
    AppConfig appConfig;
    PrefManager prefManager;
    MoviesAdapter moviesAdapter;
    ArrayList<MovieModel> moviesArrayList;
    // ButterKnife
    @BindView(R.id.parent_layout)
    LinearLayout parentLayout;
    @BindView(R.id.no_connection_layout)
    LinearLayout noConnectionLayout;
    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;
    @BindView(R.id.progress_bar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.movies_recycler)
    RecyclerView moviesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        appConfig = new AppConfig();
        prefManager = new PrefManager(this);
        moviesArrayList = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(this, moviesArrayList);
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        moviesRecyclerView.setAdapter(moviesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_popular:
                prefManager.setType(appConfig.getPOPULAR_TYPE());
                parseMovies(prefManager.getParseType());
                return true;
            case R.id.menu_top_rated:
                prefManager.setType(appConfig.getTOP_RATED_TYPE());
                parseMovies(prefManager.getParseType());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void parseMovies(String type) {
        toggleLayout(false);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                appConfig.getBASE_URL() + type + appConfig.getAPI_KEY(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray array = response.getJSONArray(appConfig.getMOVIES_ARRAY());
                            List<MovieModel> movies =
                                    new Gson().fromJson(array.toString(), new TypeToken<List<MovieModel>>() {
                                    }.getType());
                            // adding movies to list
                            moviesArrayList.clear();
                            moviesArrayList.addAll(movies);
                            // refreshing recycler view
                            moviesAdapter.notifyDataSetChanged();

                            toggleLayout(true);
                            noConnectionLayout.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            Log.d(TAG, "parseMovies called : catch exception :: " + e.toString());
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "parseMovies called : catch onErrorResponse :: " + error.toString());
                Toast.makeText(MainActivity.this, "Error : " + error.toString(), Toast.LENGTH_LONG).show();
                noConnectionLayout.setVisibility(View.VISIBLE);
            }
        });
        MyApplication.getInstance(this).addToRequestQueue(request);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver(this);
        registerReceiver(connectivityReceiver, intentFilter);
        /*register connection status listener*/
        MyApplication.getInstance(this).setConnectivityListener(this);
    }

    private void toggleLayout(boolean flag) {

        if (flag) {
            moviesRecyclerView.setVisibility(View.VISIBLE);
            moviesRecyclerView.setAdapter(moviesAdapter);
            progressLayout.setVisibility(View.GONE);
            progressBar.hide();
        } else {
            progressLayout.setVisibility(View.VISIBLE);
            progressBar.show();
            moviesRecyclerView.setVisibility(View.GONE);
        }
    }


    private void isConnected(boolean isConnected) {
        if (isConnected) {
            parseMovies(prefManager.getParseType());
            noConnectionLayout.setVisibility(View.GONE);
        } else {
            noConnectionLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        isConnected(isConnected);
    }
}
