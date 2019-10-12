package com.movefastimagegallery_.listeners


/*
Muhammad Naeem
 api Respose ResponseListener set in APi reftofit  response

 */
interface ResponseListener {
    fun onSuccess(`object`: String, action: String)
    fun onError(message: String)
}
