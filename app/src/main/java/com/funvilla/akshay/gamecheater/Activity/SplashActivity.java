package com.funvilla.akshay.gamecheater.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.funvilla.akshay.gamecheater.R;

import java.lang.reflect.Method;

/**
 * Created by Akshay on 2/9/2016.
 */
public class SplashActivity extends AppCompatActivity {
    final int WIFI_REQUEST_CODE = 0;
    final int MOBILE_REQUEST_CODE = 1;
    ImageView logoIV;
    TextView labelTV, nameTV, versionTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(" Game Cheater");
        if (!wifiCheck() && !networkCheck()) {
            showAlert("Please Enable : ", "Warning!!!!", Settings.ACTION_WIFI_SETTINGS, Settings.ACTION_DATA_ROAMING_SETTINGS, "Wifi", "Mobile Data");
        }
        initUI();

    }

    private void initUI() {

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();
        logoIV = (ImageView) findViewById(R.id.logoIV);
        labelTV = (TextView) findViewById(R.id.labelTV);
        versionTV = (TextView) findViewById(R.id.versionTV);
        nameTV = (TextView) findViewById(R.id.nameTV);
        logoIV.setImageResource(R.mipmap.splash);
        labelTV.setText("**Game Cheater**");
        nameTV.setText("By Akshay");
        versionTV.setText("v1.5");
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logoIV.setAnimation(fadeIn);
        labelTV.setAnimation(fadeIn);
        nameTV.setAnimation(fadeIn);
        versionTV.setAnimation(fadeIn);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (networkCheck() || wifiCheck()) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, 3000);
        }
    }

    public Boolean networkCheck() {
        boolean mobileDataEnabled = false; // Assume disabled
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Class cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true);
            mobileDataEnabled = (Boolean) method.invoke(cm);
        } catch (Exception e) {
        }
        return mobileDataEnabled;
    }

    public boolean wifiCheck() {
       ConnectivityManager wifiConnectivity = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = wifiConnectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(info.isConnected())
        {
            return true;
        }else
        {
            return false;
        }
    }

    private void showAlert(String message, String title, final String WifiIntent, final String MobileIntent, String wifiButton, final String MobileButton) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setNeutralButton(wifiButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivityForResult(new Intent(WifiIntent), WIFI_REQUEST_CODE);//For Wifi
            }
        });
        builder.setCancelable(false);
        builder.setPositiveButton(MobileButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivityForResult(new Intent(MobileIntent), MOBILE_REQUEST_CODE);
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((!wifiCheck() && requestCode == WIFI_REQUEST_CODE && resultCode == RESULT_CANCELED) && (!networkCheck() && requestCode == MOBILE_REQUEST_CODE && resultCode == RESULT_CANCELED)) {
            showAlert("Wifi OR Mobile Data must be enabled", "Warning!!!!", Settings.ACTION_WIFI_SETTINGS, Settings.ACTION_DATA_ROAMING_SETTINGS, "Wifi", "Mobile Data");
        }

    }
}
