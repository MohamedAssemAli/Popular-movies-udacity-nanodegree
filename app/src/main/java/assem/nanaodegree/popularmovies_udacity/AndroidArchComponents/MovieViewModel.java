package assem.nanaodegree.popularmovies_udacity.AndroidArchComponents;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import assem.nanaodegree.popularmovies_udacity.Models.MovieModel;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository mRepository;
    private LiveData<List<MovieModel>> mAllMovies;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MovieRepository(application);
        mAllMovies = mRepository.getAllMovies();
    }

    public LiveData<List<MovieModel>> getAllMovies() {
        return mAllMovies;
    }

    public void insert(MovieModel movieModel) {
        mRepository.insert(movieModel);
    }

    public void delete(MovieModel movieModel) {
        mRepository.delete(movieModel);
    }
}
