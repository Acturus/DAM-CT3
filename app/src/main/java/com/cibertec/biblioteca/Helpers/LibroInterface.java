package com.cibertec.biblioteca.Helpers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LibroInterface {
    String JSONURL = "https://artacuri.dev/api/Rest/";

    @GET("consultaLibro")
    Call<String> getLibros(@Query("term") String term);
}