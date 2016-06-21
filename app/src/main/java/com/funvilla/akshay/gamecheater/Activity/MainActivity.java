package com.funvilla.akshay.gamecheater.Activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.funvilla.akshay.gamecheater.Database.DatabaseHelper;
import com.funvilla.akshay.gamecheater.Fragments.DetailFragment;
import com.funvilla.akshay.gamecheater.Adapter.DrawerListAdapter;
import com.funvilla.akshay.gamecheater.Fragments.ListViewFragment;
import com.funvilla.akshay.gamecheater.Fragments.PCFragment;
import com.funvilla.akshay.gamecheater.R;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    Boolean isWifi = false;
    Boolean isMobileData = false;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    ListView v;
    int backPress = 0;
    ArrayList<String> drawerItems = new ArrayList<>();

    public DatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new DatabaseHelper(this);
        loadDrawer();
        LoadListViewFragment();
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_item , menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void loadDrawer() {

        drawer = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawer, null, R.string.drawer_open, R.string.drawer_close);

        drawer.setDrawerListener(toggle);
        v = (ListView) findViewById(R.id.nav_list);
        drawerItems.add("Remove Ads");
        drawerItems.add("PC");
        drawerItems.add("PS2");
        drawerItems.add("PS3");
        drawerItems.add("PS4");
        drawerItems.add("PSP");
        drawerItems.add("VITA");
        drawerItems.add("XBOX 360");
        drawerItems.add("XBOX ONE");
        drawerItems.add("Nintendo Wii U");
        drawerItems.add("Nintendo Wii");
        drawerItems.add("Nintendo 3DS");
        drawerItems.add("Nintendo DS");
        getSupportActionBar().setTitle(" Game Cheater");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawerItems);
        toggle.syncState();
        v.setAdapter(new DrawerListAdapter(this, R.layout.drawerlist_container, drawerItems, v, drawer));
        drawer.openDrawer(Gravity.LEFT);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        toggle.syncState();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            toggle.syncState();
            return true;
        }

        if(item.getItemId() == R.id.rateApp)
        {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(myAppLinkToMarket);
            } catch (ActivityNotFoundException e) {
                coloredToast("unable to find market app");
            }

        }
        return super.onOptionsItemSelected(item);

    }
    public void LoadListViewFragment() {
        PCFragment fragment = new PCFragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide1, R.anim.slide2, R.anim.popenter, R.anim.popexit).replace(R.id.fragment_container, fragment).commit();
    }

    public void LoadDetailFragment(ArrayList<String> cheat, int position) {
        DetailFragment fragment = new DetailFragment();

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("cheat", cheat);
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide1, R.anim.slide2, R.anim.popenter, R.anim.popexit).replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    public void popBackStack() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onBackPressed() {

        Handler handler = new Handler();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            if (backPress == 1) {
                super.onBackPressed();

            } else {
                backPress++;
                //Toast.makeText(this, "Press Back Again To Exit", Toast.LENGTH_SHORT).show();
                coloredToast("Press Back Again To Exit");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        backPress = 0;
                    }
                }, 3000);
            }
        } else {
            popBackStack();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isWifi || isMobileData) {
            LoadListViewFragment();
        }


    }


    @Override
    public void onStop() {
        super.onStop();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences preferences = getSharedPreferences(ListViewFragment.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().commit();
    }
}
