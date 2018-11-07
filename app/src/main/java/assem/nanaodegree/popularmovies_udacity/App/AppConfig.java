package assem.nanaodegree.popularmovies_udacity.App;

public class AppConfig {

    private final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final String API_KEY = "?api_key=0cc111f174b44036a17bca3b027e8832";
    private final String IMG_END_POINT = "http://image.tmdb.org/t/p/w342/";
    private final String RESULTS_ARRAY = "results";
    private final String POPULAR_TYPE = "popular";
    private final String TOP_RATED_TYPE = "top_rated";
    private final String FAV_TYPE = "fav";
    private final String VIDEOS = "/videos";
    private final String REVIEWS = "/reviews";
    private final String YOUTUBE_WATCH_URL = "https://www.youtube.com/watch?v=";
    private final String YOUTUBE_THUMBNAIL_PART_ONE = "https://img.youtube.com/vi/";
    private final String YOUTUBE_THUMBNAIL_PART_TWO = "/sddefault.jpg";


    public String getBASE_URL() {
        return BASE_URL;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public String getIMG_END_POINT() {
        return IMG_END_POINT;
    }

    public String getRESULTS_ARRAY() {
        return RESULTS_ARRAY;
    }

    public String getPOPULAR_TYPE() {
        return POPULAR_TYPE;
    }

    public String getTOP_RATED_TYPE() {
        return TOP_RATED_TYPE;
    }

    public String getFAV_TYPE() {
        return FAV_TYPE;
    }

    public String getVIDEOS() {
        return VIDEOS;
    }

    public String getREVIEWS() {
        return REVIEWS;
    }

    public String getYOUTUBE_WATCH_URL() {
        return YOUTUBE_WATCH_URL;
    }

    public String getYOUTUBE_THUMBNAIL_PART_ONE() {
        return YOUTUBE_THUMBNAIL_PART_ONE;
    }

    public String getYOUTUBE_THUMBNAIL_PART_TWO() {
        return YOUTUBE_THUMBNAIL_PART_TWO;
    }
}
