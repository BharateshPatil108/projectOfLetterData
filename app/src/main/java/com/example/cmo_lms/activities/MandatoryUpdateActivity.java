package com.example.cmo_lms.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmo_lms.R;
import com.example.cmo_lms.Utils.InternetUtil;

public class MandatoryUpdateActivity extends AppCompatActivity {
    private final String packageName = "com.example.cmo_lms";
    TextView txt_install_version, min_version;
    Button update_app;
    private String latestVersion;
    private int installedVersionCode;
    private int minRequiredVersionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mandatory_update);

        update_app = findViewById(R.id.update_btn);
        txt_install_version = findViewById(R.id.install_version);
        min_version = findViewById(R.id.min_version);

        installedVersionCode = getInstalledVersionCode();

        minRequiredVersionCode = getMinRequiredVersionCode();

        new FetchLatestVersionTask().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!InternetUtil.isNetworkAvailable(this)) {
            Intent dIntent = new Intent(this, NoInternetActivity.class);
            dIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dIntent);
            finish();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setupUI() {
        installedVersionCode = getInstalledVersionCode();
        minRequiredVersionCode = getMinRequiredVersionCode();

        txt_install_version.setText(getString(R.string.installed_version) + " " + installedVersionCode + " | " + getString(R.string.latest_version) + " " + latestVersion);
        min_version.setText(getString(R.string.min_version) + " " + minRequiredVersionCode);

        update_app.setOnClickListener(v -> openAppInPlayStore(packageName));

        compareVersions();
    }

    private void openAppInPlayStore(String packName) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packName));

            startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packName));

            startActivity(intent);
        }
    }

    private int getInstalledVersionCode() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(packageName, 0);
            return pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private int getMinRequiredVersionCode() {

        return 1; // Replace with the actual minimum version code
    }

    private void compareVersions() {
        if (installedVersionCode < minRequiredVersionCode) {
            Toast.makeText(this, "Installed App is old please update", Toast.LENGTH_SHORT).show();
        } else if (installedVersionCode < convertVersionStringToCode(latestVersion)) {
            Toast.makeText(this, "please update the app", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "App is up to date", Toast.LENGTH_SHORT).show();
        }
    }

    private int convertVersionStringToCode(String versionName) {
        String[] parts = versionName.split("\\.");
        int versionCode = 0;
        for (String part : parts) {
            versionCode = versionCode * 100 + Integer.parseInt(part);
        }
        return versionCode;
    }

    public class FetchLatestVersionTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... voids) {
            return "1"; // Replace with the actual latest version
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            latestVersion = result;

            setupUI();
        }
    }
}