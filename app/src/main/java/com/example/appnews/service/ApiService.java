package com.example.appnews.service;

import com.example.appnews.presentation.home.NewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("item/{id}.json?print=pretty")
    Call<NewModel> getDetailNews(
         @Path("id") int id
    );

}


