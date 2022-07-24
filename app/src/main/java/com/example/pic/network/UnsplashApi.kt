package com.example.pic.network

import com.example.pic.model.Feed
import com.example.pic.util.Constants.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    @Headers("Authorization: Client-ID $API_KEY")
    @GET("/photos")
    fun getAllImages(
        @Query("page")page: Int,
        @Query("per_page")per_page: Int
    ):Call<List<Feed>>

//    @GET("api/users?")
//    fun getUserData(@Query("page") numberPage: String): Call<UserRes>

}