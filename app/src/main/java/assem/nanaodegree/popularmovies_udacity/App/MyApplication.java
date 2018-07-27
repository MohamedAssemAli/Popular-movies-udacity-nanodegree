package assem.nanaodegree.popularmovies_udacity.App;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import assem.nanaodegree.popularmovies_udacity.Utils.ConnectivityReceiver;

public class MyApplication extends Application {

    // This class will be executed on app launch. Initiate all the volley core objects.
    // Singleton class
    private static MyApplication mInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private MyApplication(Context context) {
        // Specify the application context
        mContext = context;
        // Get the request queue
        mRequestQueue = getRequestQueue();
    }

    public static synchronized MyApplication getInstance(Context context) {
        // If Instance is null then initialize new Instance
        if (mInstance == null) {
            mInstance = new MyApplication(context);
        }
        // Return MySingleton new Instance
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
//
//    public static synchronized MyApplication getInstance() {
//        return mInstance;
//    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    public RequestQueue getRequestQueue() {
        // If RequestQueue is null the initialize new RequestQueue
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }

        // Return RequestQueue
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        // Add the specified request to the request queue
        getRequestQueue().add(request);
    }

}