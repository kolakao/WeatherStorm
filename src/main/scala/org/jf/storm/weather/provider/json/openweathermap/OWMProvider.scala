package org.jf.storm.weather.provider.json.openweathermap

import net.liftweb.json.{DefaultFormats, JsonParser}
import org.jf.storm.weather.dataobjects.Weather
import org.jf.storm.weather.provider.json.WeatherProvider
import org.jf.storm.weather.provider.json.openweathermap.owmjson.OWMWeather
import org.jf.storm.weather.network.connection.HttpCallProvider
import org.jf.storm.weather.temperature.TemperatureConverter

/**
  * Weather provider using OpenWeatherMap API as a data source
  * Created by Janusz on 2016-03-20.
  */
class OWMProvider(httpCallProvider: HttpCallProvider) extends WeatherProvider {

  override def getWeather(city: String): Weather = {
    implicit val formats = DefaultFormats
    val url = new java.net.URL(s"http://api.openweathermap.org/data/2.5/weather?q=$city&appid=a423a06f33a8c80f97324e938227d201")
    val content = httpCallProvider.getHttpResponse(url)
    val json = JsonParser.parse(content)
    new Weather(TemperatureConverter.kelvinToCelsius(json.extract[OWMWeather].main.temp))
  }
}
