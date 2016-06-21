package com.funvilla.akshay.gamecheater.Fragments;

import android.os.Bundle;

import com.funvilla.akshay.gamecheater.Database.DBValues;
import com.funvilla.akshay.gamecheater.RetrofitFiles.EndPoints;
import com.funvilla.akshay.gamecheater.R;

/**
 * Created by Akshay on 2/22/2016.
 */
public class WiiUFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = new Bundle();
        bundle.putString("listboolkey" , "isWIIU");
        bundle.putString("endpoints" , EndPoints.WIIU);
        bundle.putInt("layoutres", R.layout.fragment_listview);
        bundle.putString("tablename", DBValues.WIIU_TABLE);
        bundle.putString("actionbartitle" , "Nintendo Wii U Cheater");

        loadFragment(bundle);
    }

    private void loadFragment(Bundle bundle) {
        MainFragment fragment = new MainFragment();
        fragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , fragment).setCustomAnimations(R.anim.slide1 , R.anim.slide2 , R.anim.popenter , R.anim.popexit).commit();
    }

}