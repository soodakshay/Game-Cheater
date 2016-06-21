package com.funvilla.akshay.gamecheater.RetrofitFiles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akshay on 2/16/2016.
 */
public class GameTitleList {
    @SerializedName("data")
    @Expose
    List<DataHolder> holders = new ArrayList<>();

    public List<DataHolder> getHolders() {
        return holders;
    }

}
