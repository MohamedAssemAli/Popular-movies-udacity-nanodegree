package assem.nanaodegree.popularmovies_udacity.AndroidArchComponents;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import assem.nanaodegree.popularmovies_udacity.Models.MovieModel;

public class MovieRepository {

    private MovieDao mMovieDao;
    private LiveData<List<MovieModel>> mAllMovies;

    MovieRepository(Application application) {
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        mMovieDao = db.movieDao();
        mAllMovies = mMovieDao.getAllMovies();
    }

    public LiveData<List<MovieModel>> getAllMovies() {
        return mAllMovies;
    }

    public void insert(MovieModel movieModel) {
        new insertAsyncTask(mMovieDao).execute(movieModel);
    }

    public void delete(MovieModel movieModel) {
        new deleteAsyncTask(mMovieDao).execute(movieModel);
    }

    public MovieModel selectMovieById(int id) {
        try {
            return new selectAsyncTask(mMovieDao).execute(id).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class insertAsyncTask extends AsyncTask<MovieModel, Void, Void> {

        private MovieDao mAsyncTaskDao;

        insertAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MovieModel... params) {
            mAsyncTaskDao.insertMovie(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<MovieModel, Void, Void> {

        private MovieDao mAsyncTaskDao;

        deleteAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MovieModel... params) {
            mAsyncTaskDao.deleteMovie(params[0]);
            return null;
        }
    }

    private static class selectAsyncTask extends AsyncTask<Integer, Void, MovieModel> {

        private MovieDao mAsyncTaskDao;

        selectAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected MovieModel doInBackground(final Integer... params) {
            return mAsyncTaskDao.selectMovieById(params[0]);
        }
    }
}
