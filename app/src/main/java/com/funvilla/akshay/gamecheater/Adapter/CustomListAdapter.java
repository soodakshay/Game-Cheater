package com.funvilla.akshay.gamecheater.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.funvilla.akshay.gamecheater.Activity.MainActivity;
import com.funvilla.akshay.gamecheater.R;

import java.util.ArrayList;

/**
 * Created by Akshay on 2/16/2016.
 */
public class CustomListAdapter extends ArrayAdapter {
    Context context;
    Button reqBT;
    MainActivity activity;
    int resource;
    LayoutInflater inflater;
    ArrayList<String> gameTitle;
    ListView gameListView;
    ArrayList<String> cheat;
    ArrayList<Integer> positionContainer;
    private int lastPosition;

    public CustomListAdapter(Context context, int resource, ArrayList<String> gameTitle, Button reqBT, ListView gameListView, MainActivity activity, ArrayList<String> cheat, ArrayList<Integer> positionContainer) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.gameTitle = gameTitle;
        this.reqBT = reqBT;
        this.activity = activity;
        this.gameListView = gameListView;
        this.cheat = cheat;
        this.positionContainer = positionContainer;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        if (gameTitle.size() == 0) {
            reqBT.setVisibility(View.VISIBLE);
            return 1;
        } else {
            reqBT.setVisibility(View.INVISIBLE);
        }
        return gameTitle.size();
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(resource, null);
            holder = new ViewHolder();
            holder.indicator = (ImageView) convertView.findViewById(R.id.idicator);
            holder.gameTitleTV = (TextView) convertView.findViewById(R.id.gameTitleTV);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        if (gameTitle.size() == 0) {
            holder.gameTitleTV.setText("Game not found");
            reqBT.setVisibility(View.VISIBLE);
            holder.indicator.setImageResource(R.mipmap.not_found);
            gameListView.setOnItemClickListener(null);
        } else {
            holder.gameTitleTV.setText(gameTitle.get(position));
            holder.indicator.setImageResource(R.mipmap.idicator);
            gameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    View view1 = inflater.inflate(R.layout.activity_main, null);
                    InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(gameListView.getWindowToken(), 0);
                    if (positionContainer.size() == 0) {
                        activity.LoadDetailFragment(cheat, position);
                    } else {
                        activity.LoadDetailFragment(cheat, positionContainer.get(position));
                    }

                }
            });
        }

        lastPosition = -1;
        Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        convertView.startAnimation(animation);
        lastPosition = position;
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
}

class ViewHolder {
    TextView gameTitleTV;
    ImageView indicator;
}
