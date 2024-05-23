package com.ip.imagecachingapp.Retrofit

import com.ip.imagecachingapp.Models.ImageResponesItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/photos/?client_id=9DXn7HTepDN51_1vKrCiISASqFQZBDyOuB9PiUg-W2U")
    suspend fun getData(
        @Query("page") page : Int
    ) : ArrayList<ImageResponesItem>
}