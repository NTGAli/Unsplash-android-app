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
    suspend fun getSpecificImage(
        @Path("id")id: String
    ):ImageDetails


    @GET("/users/{username}")
    suspend fun getUserByUsername(
        @Path("username")username: String
    ):UnsplashUser

    @GET("/users/{username}/photos")
    suspend fun getUserPhotosByUsername(
        @Path("username")username: String,
        @Query("page") page: Int,
        @Query("per_page")per_page: Int
    ):Response<List<Feed>>

    @GET("/topics")
    suspend fun getTopics(): List<Topic>

    @GET("/topics/{id}")
    suspend fun getTopicById(
        @Path("id") topicID: String
    ): Preview




    @GET("/photos")
    suspend fun getAllImagesByOrder(
        @Query("page")page: Int,
        @Query("per_page")per_page: Int,
        @Query("order_by") order_by : String
    ):List<Feed>

    @GET("/search/photos")
    suspend fun searchInImages(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<ResultImage>

    @GET("/search/users")
    suspend fun searchInUsers(
        @Query("query") query: String
        ):ResultUser

}