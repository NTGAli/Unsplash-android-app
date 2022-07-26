package com.example.pic.network

import com.example.pic.model.*
import com.example.pic.util.Constants.API_KEY
import retrofit2.Call
import retrofit2.http.*

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

    @Headers("Authorization: Client-ID $API_KEY")
    @GET("/topics")
    fun getTopics(): Call<List<Topic>>

    @Headers("Authorization: Client-ID $API_KEY")
    @GET("/topics/{id}")
    fun getTopicById(
        @Path("id") topicID: String
    ): Call<Preview>


    @Headers("Authorization: Client-ID $API_KEY")
    @GET("/photos")
    fun getAllImagesByOrder(
        @Query("page")page: Int,
        @Query("per_page")per_page: Int,
        @Query("order_by") order_by : String
    ):Call<List<Feed>>


}