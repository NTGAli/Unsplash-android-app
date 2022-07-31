package com.example.pic.network

import com.example.pic.model.*
import com.example.pic.util.Constants.API_KEY
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UnsplashApi {

    @GET("/photos")
    suspend fun getAllImages(
        @Query("page")page: Int,
        @Query("per_page")per_page: Int
    ):Response<List<Feed>>


    @GET("/photos/{id}")
    fun getSpecificImage(
        @Path("id")id: String
    ):Call<ImageDetails>


    @GET("/users/{username}")
    fun getUserByUsername(
        @Path("username")username: String
    ):Call<UnsplashUser>

    @GET("/topics")
    fun getTopics(): Call<List<Topic>>

    @GET("/topics/{id}")
    fun getTopicById(
        @Path("id") topicID: String
    ): Call<Preview>


    @GET("/photos")
    fun getAllImagesByOrder(
        @Query("page")page: Int,
        @Query("per_page")per_page: Int,
        @Query("order_by") order_by : String
    ):Call<List<Feed>>

    @GET("/search/photos")
    suspend fun searchInImages(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<ResultImage>

    @GET("/search/users")
    fun searchInUsers(
        @Query("query") query: String
        ):Call<ResultUser>

}