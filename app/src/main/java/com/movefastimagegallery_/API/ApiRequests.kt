package com.movefastimagegallery_.API

import android.content.Context

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.movefastimagegallery_.listeners.ResponseListener


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ApiRequests(private val context: Context, private val listener: ResponseListener) {
    /*
     this methode will retun Jason array for   API respose . Api have Response in JSON Arry
     so this method use
     */
    fun getApiRequestMethodarray(pageno: Int, action: String) {

        val apiInterface = ApiClient.apiClient!!.create(ApiInterface::class.java)


        val call = apiInterface.getApiRequestsArray(Constants.UNSPLASH_CLIENT_ID, pageno)
        call.enqueue(object : Callback<JsonArray> {


            override fun onResponse(call: Call<JsonArray>, resp: Response<JsonArray>) {

                /*
                     check respose and Response  body is not null
                     */
                if (resp != null && resp.body() != null) {

                    /*
                   ifResponse or Response body null then set the error
                   set the ListItemClickListener interface Success methode

                    */

                    listener.onSuccess(resp.body()!!.toString(), action)
                } else {
                    /*
                    if Response or Response body null then set the error
                     set the ListItemClickListener interface  error methode
                     */
                    listener.onError(resp.message())
                }

            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                t.printStackTrace()
                /*
                     set the ListItemClickListener interface  error methode
                     */
                listener.onError(t.message ?: "")
            }
        })
    }


    /*

   call releated  images APi

   */
    fun getApiRelatedImage(id: String) {

        val apiInterface = ApiClient.apiClient!!.create(ApiInterface::class.java)

        val call = apiInterface.getPhotosById(id, Constants.UNSPLASH_CLIENT_ID)
        call.enqueue(object : Callback<JsonObject> {


            override fun onResponse(call: Call<JsonObject>, resp: Response<JsonObject>) {

                /*
                     check respose and Response  body is not null
                     */
                if (resp != null && resp.body() != null) {


                    listener.onSuccess(resp.body()!!.toString(), id)
                } else {
                    /*
                    if Response or Response body null then set the error
                     set the ListItemClickListener interface  error methode
                     */
                    listener.onError(resp.message())
                }

            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
                /*
                     set the ListItemClickListener interface  error methode
                     */
                listener.onError(t.message ?: "")
            }
        })
    }


}
