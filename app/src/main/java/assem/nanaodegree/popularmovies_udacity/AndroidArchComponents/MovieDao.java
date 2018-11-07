package assem.nanaodegree.popularmovies_udacity.AndroidArchComponents;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

import assem.nanaodegree.popularmovies_udacity.Models.MovieModel;

@Dao
public interface MovieDao {

    // insert movie
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieModel movieModel);

    // get movie
    @Query("SELECT * FROM movie WHERE id= :id LIMIT 1")
    MovieModel selectMovieById(int id);

    // delete movie
    @Delete
    void deleteMovie(MovieModel movieModel);

    // get all movies
    @Query("SELECT * from movie")
    LiveData<List<MovieModel>> getAllMovies();
}
