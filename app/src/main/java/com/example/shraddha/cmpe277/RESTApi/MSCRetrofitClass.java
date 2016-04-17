package com.example.shraddha.cmpe277.RESTApi;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class MSCRetrofitClass {

    private static String ERDDAP_BASE_URL = "http://erddap.cencoos.org/erddap/";
    private static String MSC_SERVER_BASE_URL = "http://erddap.cencoos.org/erddap/";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static ErddapService getErrdapClient() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ERDDAP_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ErddapService.class);
    }

    public static MSCService getMSCClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(MSC_SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(MSCService.class);
    }
}
