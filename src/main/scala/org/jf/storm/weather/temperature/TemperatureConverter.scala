package org.jf.storm.weather.temperature


/**
 * Created by Janusz on 2016-03-20.
 */
object TemperatureConverter {


  def celsiusToKelvin(value: Float): Float = {
    value + 273.15f
  }

  def kelvinToCelsius(value: Float): Float = {
    value - 273.15f
  }


}
