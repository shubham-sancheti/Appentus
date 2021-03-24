package com.shubham_dev.appentus.API

import com.shubham_dev.appentus.Model.ResponseModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("list")
    fun getPhotos(@Query("page") page: Int): Call<List<ResponseModel>>
}