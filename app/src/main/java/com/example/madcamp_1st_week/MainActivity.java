package com.example.madcamp_1st_week;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    //
//    private TabLayout tabLayout;
//    private AppBarLayout appBarLayout;
//    private ViewPager viewPager;
    ProgressBar progressBar;
    TextView textView;

    private static String[] permissionCheck = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.SYSTEM_ALERT_WINDOW
    };

    private static final int MY_PERMISSION_STORAGE = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressBar = findViewById(R.id.progressbar_id);
        textView = findViewById(R.id.progressbartext_id);

        progressBar.setMax(100);
        progressBar.setScaleY(3f);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        } else {
            progressAnimation();
        }

//        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
//        appBarLayout = (AppBarLayout) findViewById(R.id.appbar_id);
//        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//
//        // Adding Fragments
//        adapter.AddFragment(new FragmentContacts(), "Contacts");
//        adapter.AddFragment(new FragmentGallery(), "Gallery");
//        adapter.AddFragment(new FragmentAlpha(), "Alpha");
//
//        // adapter Setup
//        viewPager.setAdapter(adapter);
//        tabLayout.setupWithViewPager(viewPager);

    }

    public void progressAnimation() {
        ProgressbarAnimation anim = new ProgressbarAnimation(this, progressBar, textView, 0f, 100f);
        anim.setDuration(2500);
        progressBar.setAnimation(anim);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionCheck[0]) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permissionCheck[1]) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permissionCheck[2]) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permissionCheck[3]) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permissionCheck[4]) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permissionCheck[5]) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permissionCheck[6])
            ) {
                Dialog();
            } else {
                ActivityCompat.requestPermissions(this, permissionCheck, MY_PERMISSION_STORAGE);
                finish();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_STORAGE:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] < 0) {
                        Toast.makeText(MainActivity.this, "Need to Allow the Permission", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                }
                break;
        }
    }

    public void Dialog() {
        new AlertDialog.Builder(this)
                .setTitle("Notification")
                .setMessage("Storage Permission Denied. Allow the Permission on the Settings.")
                .setNeutralButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivity(intent);
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }
}
