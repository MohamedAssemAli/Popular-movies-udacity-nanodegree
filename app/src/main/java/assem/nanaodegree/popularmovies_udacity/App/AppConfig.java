package assem.nanaodegree.popularmovies_udacity.App;

public class AppConfig {

    private final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final String API_KEY = "?api_key=XXXXXXXX";
    private final String IMG_END_POINT = "http://image.tmdb.org/t/p/w342/";
    private final String MOVIES_ARRAY = "results";
    private final String POPULAR_TYPE = "popular";
    private final String TOP_RATED_TYPE = "top_rated";


    public String getBASE_URL() {
        return BASE_URL;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public String getIMG_END_POINT() {
        return IMG_END_POINT;
    }

    public String getMOVIES_ARRAY() {
        return MOVIES_ARRAY;
    }

    public String getPOPULAR_TYPE() {
        return POPULAR_TYPE;
    }

    public String getTOP_RATED_TYPE() {
        return TOP_RATED_TYPE;
    }
}
