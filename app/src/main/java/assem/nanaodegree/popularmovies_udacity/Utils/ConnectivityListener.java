package assem.nanaodegree.popularmovies_udacity.Utils;

import android.app.Application;

public class ConnectivityListener extends Application {

    private static ConnectivityListener mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized ConnectivityListener getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

}
