package com.movefastimagegallery_.API


import com.google.gson.JsonArray
import com.google.gson.JsonObject

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {


    @GET("photos")
    fun getApiRequestsArray(
        @Query("client_id") client_id: String,
        @Query("page") page: Int
    ): Call<JsonArray>


    @GET("photos/{id}")
    fun getPhotosById(
        @Path("id") id: String,
        @Query("client_id") client_id: String
    ): Call<JsonObject>

}
