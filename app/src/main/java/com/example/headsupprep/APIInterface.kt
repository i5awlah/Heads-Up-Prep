package com.example.headsupprep


import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    @GET("celebrities/")
    fun getCelebrity(): Call<Celebrity>
    @POST("celebrities/")
    fun addCelebrity(@Body celebrity: CelebrityItem): Call<CelebrityItem>
    @PUT("celebrities/{id}")
    fun updateCelebrity(@Path("id") id: Int, @Body celebrity: CelebrityItem): Call<CelebrityItem>
    @DELETE("celebrities/{id}")
    fun deleteCelebrity(@Path("id") id: Int): Call<Void>
    @GET("/celebrities/{id}")
    fun getCelebrity(@Path("id") id: Int): Call<CelebrityItem>
}