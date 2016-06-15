package com.example.sadhika.duckduckgo;

import com.example.sadhika.duckduckgo.pojos.DDGResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitDuckDuckGoInterface {
    @GET("/")
    Call<DDGResponse> getResults(@Query("q") String query, @Query("format") String format);
}
