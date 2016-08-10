package org.jf.storm.weather.provider.json.yahoo.yahooJson

/**
  * Container object for Yahoo weather condition JSON mapping
  * Created by Janusz on 2016-04-17.
  */
case class Condition(code: String, date: String, temp: String, text: String) {}
