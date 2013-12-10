package com.example.journeymanager.objects;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetInfo {
    private final Activity mActivity;

    public NetInfo(Activity activity) {
        mActivity = activity;
    }


    public Boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)
                mActivity.getSystemService(mActivity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
