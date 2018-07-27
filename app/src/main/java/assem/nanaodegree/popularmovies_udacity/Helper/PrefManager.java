package assem.nanaodegree.popularmovies_udacity.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PrefManager {

    // LogCat tag
    private static String TAG = PrefManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "PopularMoviesPref";
    private static final String KEY_PARSE_TYPE = "ParseType";

    public PrefManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setType(String parseType) {
        editor.putString(KEY_PARSE_TYPE, parseType);
        // commit changes
        editor.commit();
        Log.d(TAG, "type is modified!");
    }

    public String getParseType() {
        return pref.getString(KEY_PARSE_TYPE, "popular");
    }

}

