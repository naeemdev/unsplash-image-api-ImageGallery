package com.movefastimagegallery_.Helper

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast


class Utils(internal var mContext: Context) {


    //check the internet connection  avaiable or not


    fun isWifi(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


    /// show toast
    fun showToast(msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show()
    }


}
