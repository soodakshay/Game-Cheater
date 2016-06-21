package com.funvilla.akshay.gamecheater.Fragments;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.funvilla.akshay.gamecheater.Activity.MainActivity;

/**
 * Created by Akshay on 2/23/2016.
 */
public class DottedProgress {

    TextView dottedProgress;
    ProgressBar progressBar;
    Boolean isListLoaded;
    int count;
    MainActivity activity;

    public DottedProgress(ProgressBar progressBar, TextView dottedProgress, Boolean isListLoaded , int count , MainActivity activity) {
        this.progressBar = progressBar;
        this.dottedProgress = dottedProgress;
        this.isListLoaded = isListLoaded;
        this.count = count;
        this.activity = activity;
    }

    public void showProgress() {


        if (isListLoaded) {
            dottedProgress.setText("");
            progressBar.setVisibility(View.INVISIBLE);
            dottedProgress.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            dottedProgress.setVisibility(View.VISIBLE);

            switch (count) {
                case 1:
                    dottedProgress.setText(".");
                    break;

                case 2:
                    dottedProgress.setText("..");
                    break;

                case 3:
                    dottedProgress.setText("...");
                    break;
                case 4:
                    dottedProgress.setText("....");
                    break;

                case 5:
                    dottedProgress.setText(".....");
                    break;
                case 6:
                    dottedProgress.setText("");
                    break;
            }

            if (count > 5)

            {
                count = 0;
            }
            count++;


        }

    }

}
