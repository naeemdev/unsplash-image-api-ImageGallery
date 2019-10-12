package com.movefastimagegallery_.API


import com.google.gson.JsonArray

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {


    @GET("photos")
    fun getApiRequestsArray(
        @Query("client_id") client_id: String,
        @Query("page") page: Int
    ): Call<JsonArray>


    @GET("photos")
    fun getPhotosById(
        @Query("id") id: String,
        @Query("client_id") client_id: String,
        @Query("page") page: Int
    ): Call<JsonArray>


}
