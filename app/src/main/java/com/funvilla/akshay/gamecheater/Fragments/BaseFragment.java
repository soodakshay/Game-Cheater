package com.funvilla.akshay.gamecheater.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.funvilla.akshay.gamecheater.Activity.MainActivity;

/**
 * Created by Akshay on 2/16/2016.
 */
public class BaseFragment extends Fragment {

    MainActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();

    }



}
