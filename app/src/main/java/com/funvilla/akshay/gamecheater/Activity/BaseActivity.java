package com.funvilla.akshay.gamecheater.Activity;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.funvilla.akshay.gamecheater.R;

/**
 * Created by akshaysood on 2/24/16.
 */
public class BaseActivity extends AppCompatActivity {

TextView toastMessageTV;

    public static final String PREFS_NAME = "check_db";
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    public void coloredToast(String Message)
    {
        Toast toast = Toast.makeText(this, Message, Toast.LENGTH_SHORT);
        toast.setText(Message);
        View view = getLayoutInflater().inflate(R.layout.toast_layout,null);
        toastMessageTV = (TextView) view.findViewById(R.id.toastMessageTV);
        toastMessageTV.setText(Message);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0 );
        toast.setView(view);
        toast.show();

    }
}
