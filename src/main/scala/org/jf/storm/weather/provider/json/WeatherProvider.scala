package org.jf.storm.weather.provider.json

import org.jf.storm.weather.dataobjects.Weather

/**
  * Weather condition provider
  * Created by Janusz on 2016-03-20.
  */
trait WeatherProvider extends Serializable {

  /**
    * Finds out weather conditions for given city
    *
    * @param city city you are interested in
    * @return Weather conditions for given city
    */
  def getWeather(city: String): Weather

}
