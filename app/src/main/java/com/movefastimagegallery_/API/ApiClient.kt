package com.movefastimagegallery_.API

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object ApiClient {
    private val TAG = ApiClient::class.java.simpleName
    private var retrofitApi: Retrofit? = null


    val apiClient: Retrofit?
        get() {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val orignalRequest = chain.request()

                    val newRequest = orignalRequest.newBuilder()
                        .header("Content-Type", "application/json")
                        .build()
                    return chain.proceed(newRequest)

                }
            })

            val logging = HttpLoggingInterceptor()
            logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }

            httpClient.addInterceptor(logging)
            httpClient.readTimeout(30, TimeUnit.SECONDS)

            if (retrofitApi == null) {
                retrofitApi = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
            }
            return retrofitApi
        }


}
