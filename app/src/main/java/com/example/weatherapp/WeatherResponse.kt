package com.example.weatherapp

data class WeatherResponse(
    var temperature: String,
    var wind: String,
    var description: String,
    var forecast: ArrayList<Forecast>
)

data class Forecast (
    var day: String,
    var temperature: String,
    var wind: String?
)
