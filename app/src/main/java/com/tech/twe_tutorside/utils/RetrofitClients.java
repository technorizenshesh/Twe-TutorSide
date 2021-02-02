package com.tech.twe_tutorside.utils;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClients {

    private static final String BASE_URL ="https://triumph.cruvz.cloud/webservice/";
    private static RetrofitClients mInstance;
    private Retrofit retrofit;


    private RetrofitClients(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

public static synchronized RetrofitClients getInstance(){

        if (mInstance == null){
       mInstance = new RetrofitClients();
        }
        return mInstance;
    }

  public Api getApi(){

   return retrofit.create(Api.class);

  }

}
