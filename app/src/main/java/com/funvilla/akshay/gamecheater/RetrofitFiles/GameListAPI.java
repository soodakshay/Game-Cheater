package com.funvilla.akshay.gamecheater.RetrofitFiles;


import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;


/**
 * Created by Akshay on 2/16/2016.
 */
public interface GameListAPI {
    @GET("gamecheater/{url}")
    Call<GameTitleList> loadData(@Path("url")String url);

}
