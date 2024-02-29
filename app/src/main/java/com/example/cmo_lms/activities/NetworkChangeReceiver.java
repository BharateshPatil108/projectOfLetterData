package com.example.cmo_lms.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.example.cmo_lms.Utils.InternetUtil;


public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                if (!InternetUtil.isNetworkAvailable(context)) {
                    Intent noInternetIntent = new Intent(context, NoInternetActivity.class);
                    noInternetIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(noInternetIntent);
                }
            }
        }
    }
}