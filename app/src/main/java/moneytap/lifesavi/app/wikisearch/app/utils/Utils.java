package moneytap.lifesavi.app.wikisearch.app.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import moneytap.lifesavi.app.wikisearch.app.WikiSearchApplication;

public class Utils {

    public static boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) WikiSearchApplication.application.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager!=null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        return false;
    }
}
