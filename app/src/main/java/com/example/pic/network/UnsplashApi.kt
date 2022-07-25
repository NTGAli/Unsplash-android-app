package com.example.pic.network

import com.example.pic.model.Feed
import com.example.pic.model.ImageDetails
import com.example.pic.model.UnsplashUser
import com.example.pic.util.Constants.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {

    @Headers("Authorization: Client-ID $API_KEY")
    @GET("/photos")
    fun getAllImages(
        @Query("page")page: Int,
        @Query("per_page")per_page: Int
    ):Call<List<Feed>>


    @Headers("Authorization: Client-ID $API_KEY")
    @GET("/photos/{id}")
    fun getSpecificImage(
        @Path("id")id: String
    ):Call<ImageDetails>


    @Headers("Authorization: Client-ID $API_KEY")
    @GET("/users/{username}")
    fun getUserByUsername(
        @Path("username")username: String
    ):Call<UnsplashUser>
}