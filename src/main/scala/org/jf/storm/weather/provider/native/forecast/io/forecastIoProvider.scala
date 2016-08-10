package org.jf.storm.weather.provider.native.forecast.io

import de.knutwalker.forecastio.ForecastIO
import org.jf.storm.weather.dataobjects.Weather
import org.jf.storm.weather.provider.json.WeatherProvider

/**
  * Created by Janusz on 2016-08-01.
  */
class forecastIoProvider(fio:ForecastIO) extends WeatherProvider{

  /**
    * Finds out weather conditions for given city
    *
    * @param city city you are interested in
    * @return Weather conditions for given city
    */
  override def getWeather(city: String): Weather = {
    ???
  }


}
