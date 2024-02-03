package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val requestButton: Button = findViewById(R.id.requestBtn)
        val searchCityInput: TextInputLayout = findViewById(R.id.city)
        requestButton.setOnClickListener {
            val userInput = searchCityInput.editText?.text?.toString()?.trim()
            if (!userInput.isNullOrBlank()) {
                val apiClient = ApiClient.client.create(ApiInterface::class.java)
                apiClient.getWeather(userInput)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        handleResponse(it)
                    },{
                        println("THIS")
                        Toast.makeText(this, "Error ${it.message}", Toast.LENGTH_LONG).show()
                    })
            } else {
                Toast.makeText(this, "Please enter city", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun handleResponse(weatherResponse: WeatherResponse) {
        findViewById<TextView>(R.id.temperatureTextView).text = "Now: ${weatherResponse.temperature}"
        findViewById<TextView>(R.id.windTextView).text = "Wind: ${weatherResponse.wind}"
        findViewById<TextView>(R.id.descriptionTextView).text = "${weatherResponse.description}"

        val forecastTextView = findViewById<TextView>(R.id.forecastTextView)
        val forecastStringBuilder = StringBuilder()
        for (forecastItem in weatherResponse.forecast) {
            forecastStringBuilder.append("Day: ${forecastItem.day}, Temperature: ${forecastItem.temperature}, Wind: ${forecastItem.wind}\n")
        }
        forecastTextView.text = forecastStringBuilder.toString()
    }
}