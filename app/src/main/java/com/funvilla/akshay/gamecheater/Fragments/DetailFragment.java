package com.funvilla.akshay.gamecheater.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.funvilla.akshay.gamecheater.Fragments.BaseFragment;
import com.funvilla.akshay.gamecheater.Fragments.DottedProgress;
import com.funvilla.akshay.gamecheater.R;

import java.util.ArrayList;

/**
 * Created by Akshay on 2/16/2016.
 */
public class DetailFragment extends BaseFragment {
    WebView webView;
    View view;
    ArrayList<String> cheat;
    int position;
    ProgressDialog webLoadProgress;
    Boolean isDataSaving = false;
    Boolean isPageLoaded = false;
    int counter = 0;
    private int timeCounter = 0;
    private ProgressBar dottedPB;
    private TextView dottedTV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        isPageLoaded = false;
        Bundle bundle = getArguments();
        cheat = bundle.getStringArrayList("cheat");
        position = bundle.getInt("position");
        view = inflater.inflate(R.layout.fragment_detail, null);
        init();
        webView.getSettings().setJavaScriptEnabled(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Select Network Mode");
        builder.setMessage("Note: Images will not visible on selecting Data Saving Mode");
        builder.setPositiveButton("Data Saving Mode", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isDataSaving = true;
                webView.getSettings().setLoadsImagesAutomatically(false);
                webView.getSettings().setBlockNetworkImage(true);
                webView.loadUrl(cheat.get(position));
            }
        });
        builder.setNegativeButton("Normal Mode", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isDataSaving = false;
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setBlockNetworkImage(false);
                webView.loadUrl(cheat.get(position));
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (webLoadProgress.isShowing()) {
                    isPageLoaded = true;
                    webView.setVisibility(View.VISIBLE);
                    activity.coloredToast("Please scroll up or down to view the cheat codes");
                    webLoadProgress.dismiss();
                }
            }
        });
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        builder.setCancelable(false);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isPageLoaded) {
                    timeCounter++;
                    if (timeCounter == 20) {
                        timeCounter = 0;
                        activity.coloredToast("Slow Internet Connection.\\" +
                                "Cheat Page isn't Loaded Yet. Please wait");
                        //Toast.makeText(activity, "Slow Internet Connection.\nCheat Page isn't Loaded Yet. Please wait", Toast.LENGTH_LONG).show();
                    }
                } else {
                    handler.removeCallbacks(this);
                    timeCounter = 0;
                }

                handler.postDelayed(this, 1000);
            }
        }, 1000);

        builder.show();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isPageLoaded = true;
    }

    private void init() {
        webView = (WebView) view.findViewById(R.id.webView);
        webView.setVisibility(View.INVISIBLE);
        webLoadProgress = new ProgressDialog(activity);
        webLoadProgress.setTitle("Please Wait..");
        webLoadProgress.setMessage("Web Page Loading .....");
        webLoadProgress.show();
        webLoadProgress.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
               activity.coloredToast("\"Slow Internet Connection.\\nList isn't loaded yet. Please wait\"");
            }
        });
        dottedPB = (ProgressBar) view.findViewById(R.id.dottedPB);
        dottedTV = (TextView) view.findViewById(R.id.dottedTV);
        callHandler();
    }

    private void callHandler() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isPageLoaded) {
                    new DottedProgress(dottedPB, dottedTV, isPageLoaded, counter, activity).showProgress();
                    handler.removeCallbacks(this);

                } else {
                    if (counter > 5)

                    {
                        counter = 0;
                    }
                    counter++;
                    new DottedProgress(dottedPB, dottedTV, isPageLoaded, counter, activity).showProgress();
                }
                handler.postDelayed(this, 800);
            }
        }, 0);
    }


    void getCheatArray(ArrayList<String> cheat) {
        this.cheat = cheat;
    }

}
