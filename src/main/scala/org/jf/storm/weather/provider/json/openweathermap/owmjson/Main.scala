package org.jf.storm.weather.provider.json.openweathermap.owmjson

/**
  * Class representing main part of weather in json object from OpenWeatherMap service
  * Created by Janusz on 2016-03-20.
  */
case class Main(temp: Float, pressure: Float, humidity: Float, temp_min: Float, temp_max: Float) {}
