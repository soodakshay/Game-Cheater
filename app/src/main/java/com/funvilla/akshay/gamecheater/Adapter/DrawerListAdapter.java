package com.funvilla.akshay.gamecheater.Adapter;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.funvilla.akshay.gamecheater.Activity.MainActivity;
import com.funvilla.akshay.gamecheater.Fragments.DSFragment;
import com.funvilla.akshay.gamecheater.Fragments.Fragment3DS;
import com.funvilla.akshay.gamecheater.Fragments.PCFragment;
import com.funvilla.akshay.gamecheater.Fragments.PS2Fragment;
import com.funvilla.akshay.gamecheater.Fragments.PS3Fragment;
import com.funvilla.akshay.gamecheater.Fragments.PS4Fragment;
import com.funvilla.akshay.gamecheater.Fragments.PSPFragment;
import com.funvilla.akshay.gamecheater.Fragments.VitaFragment;
import com.funvilla.akshay.gamecheater.Fragments.WiiFragment;
import com.funvilla.akshay.gamecheater.Fragments.WiiUFragment;
import com.funvilla.akshay.gamecheater.Fragments.Xbox360Fragment;
import com.funvilla.akshay.gamecheater.Fragments.XboxOneFragment;
import com.funvilla.akshay.gamecheater.R;

import java.util.ArrayList;

/**
 * Created by Akshay on 2/20/2016.
 */
public class DrawerListAdapter extends ArrayAdapter {

    int resource;
    Context context;
    ListView drawerListView;
    ArrayList<String> platformList;
    LayoutInflater inflator;
    ArrayList<Integer> iconRes = new ArrayList<>();
    MainActivity activity;
DrawerLayout drawerLayout;
    public DrawerListAdapter(Context context, int resource, ArrayList<String> platformList, ListView drawerListView, DrawerLayout drawer) {
        super(context, resource);

        this.context = context;
        this.resource = resource;
        this.drawerLayout = drawer;
        this.platformList = platformList;
        this.drawerListView = drawerListView;
        inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        iconRes.add(R.mipmap.ic_ads);
        iconRes.add(R.mipmap.pc);
        iconRes.add(R.mipmap.ps3);
        iconRes.add(R.mipmap.ps3);
        iconRes.add(R.mipmap.ps3);
        iconRes.add(R.mipmap.psp);
        iconRes.add(R.mipmap.psp);
        iconRes.add(R.mipmap.xbox);
        iconRes.add(R.mipmap.xbox);
        iconRes.add(R.mipmap.nintendo);
        iconRes.add(R.mipmap.nintendo);
        iconRes.add(R.mipmap.nintendo);
        iconRes.add(R.mipmap.nintendo);
        activity = (MainActivity) context;
    }

    @Override
    public int getCount() {
        return platformList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DrawerViewHolder holder;
        if (convertView == null) {
            convertView = inflator.inflate(resource, null);
            holder = new DrawerViewHolder();
            holder.iconIV = (ImageView) convertView.findViewById(R.id.iconIV);
            holder.titleTV = (TextView) convertView.findViewById(R.id.platformTV);
            convertView.setTag(holder);
        } else {
            holder = (DrawerViewHolder) convertView.getTag();
        }
        holder.iconIV.setImageResource(iconRes.get(position));
        holder.titleTV.setText(platformList.get(position));
        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                         final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Upgrade to full version");
                        alert.setMessage("Why upgrade? \n 1. Ad free app. \n 2. 11000 + Games List. \n 3. Daily Cheats Updates.");
                        alert.setPositiveButton("Upgrade", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Uri uri = Uri.parse("market://details?id=com.akshay.gamecheater");
                                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                                try {
                                    context.startActivity(myAppLinkToMarket);
                                } catch (ActivityNotFoundException e) {
                                    activity.coloredToast("unable to find market app");
                                }
                                dialog.dismiss();

                            }
                        });
                        alert.setNegativeButton("No Thanks", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                            }
                        });
                  alert.show();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case 1:
                        activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide1, R.anim.slide2, R.anim.popenter, R.anim.popexit).replace(R.id.fragment_container, new PCFragment()).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case 2:
                        activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide1, R.anim.slide2, R.anim.popenter, R.anim.popexit).replace(R.id.fragment_container, new PS2Fragment()).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;

                    case 3:
                        activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide1, R.anim.slide2, R.anim.popenter, R.anim.popexit).replace(R.id.fragment_container, new PS3Fragment()).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;

                    case 4:
                        activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide1, R.anim.slide2, R.anim.popenter, R.anim.popexit).replace(R.id.fragment_container, new PS4Fragment()).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;

                    case 5:
                        activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide1, R.anim.slide2, R.anim.popenter, R.anim.popexit).replace(R.id.fragment_container, new PSPFragment()).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;

                    case 6:
                        activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide1, R.anim.slide2, R.anim.popenter, R.anim.popexit).replace(R.id.fragment_container, new VitaFragment()).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;

                    case 7:
                        activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide1, R.anim.slide2, R.anim.popenter, R.anim.popexit).replace(R.id.fragment_container, new Xbox360Fragment()).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;

                    case 8:
                        activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide1, R.anim.slide2, R.anim.popenter, R.anim.popexit).replace(R.id.fragment_container, new XboxOneFragment()).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case 9:
                        activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide1, R.anim.slide2, R.anim.popenter, R.anim.popexit).replace(R.id.fragment_container, new WiiUFragment()).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case 10:
                        activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide1, R.anim.slide2, R.anim.popenter, R.anim.popexit).replace(R.id.fragment_container, new WiiFragment()).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case 11:
                        activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide1, R.anim.slide2, R.anim.popenter, R.anim.popexit).replace(R.id.fragment_container, new Fragment3DS()).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case 12:

                        activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide1, R.anim.slide2, R.anim.popenter, R.anim.popexit).replace(R.id.fragment_container, new DSFragment()).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;


                }
            }
        });
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

}

class DrawerViewHolder {
    ImageView iconIV;
    TextView titleTV;
}
