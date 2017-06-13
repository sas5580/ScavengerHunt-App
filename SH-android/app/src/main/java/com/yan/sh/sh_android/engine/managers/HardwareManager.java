package com.yan.sh.sh_android.engine.managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.HttpURLConnection;
import java.net.URL;

import timber.log.Timber;

/**
 * Created by yan on 6/11/17.
 * Tracks connectivity status of the device
 */

public class HardwareManager extends Manager{

    Context mContext;

    public HardwareManager(Context context){
        this.startup();
        mContext = context;

        //TESTING
        if(internetAvailable(mContext)){
            Timber.i("internet available");
            //Needs more testing
            if(hasActiveInternetConnection())
            {
                Timber.i("Can connecto");
            }else{
                Timber.i("No connecto");
            }
        }
    }

    public boolean internetAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null);
    }

    public boolean hasActiveInternetConnection() {
        try{
            HttpURLConnection urlConnection = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
            urlConnection.setRequestProperty("User-Agent", "Test");
            urlConnection.setRequestProperty("Connection", "close");
            urlConnection.setConnectTimeout(2000);
            urlConnection.connect();
            return (urlConnection.getResponseCode() == 200);
        }catch(Exception ex){
        }

        return false;
    }
}
