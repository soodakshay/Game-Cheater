package com.funvilla.akshay.gamecheater.RetrofitFiles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Akshay on 2/16/2016.
 */
public class DataHolder {

    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("cheat")
    @Expose
    String cheat;

    public String getCheat() {
        return cheat;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
