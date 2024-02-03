package com.example.weatherapp

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("/weather/{city}")
    fun getWeather(@Path("city") city:String): Single<WeatherResponse>
}