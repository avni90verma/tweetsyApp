package com.example.tweetsyapp.api

import com.example.tweetsyapp.models.TweetListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface TwestyApi {


    @GET("/v3/b/6766fb39acd3cb34a8bd6319?meta=false")
    suspend fun getTweets(@Header("X-JSON-Path") category:String) :Response<List<TweetListItem>>

}