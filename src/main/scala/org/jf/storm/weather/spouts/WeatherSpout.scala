package org.jf.storm.weather.spouts

import java.util

import org.apache.storm.spout.SpoutOutputCollector
import org.apache.storm.task.TopologyContext
import org.apache.storm.topology.OutputFieldsDeclarer
import org.apache.storm.topology.base.BaseRichSpout
import org.apache.storm.tuple.{Fields, Values}
import org.jf.storm.weather.provider.json.WeatherProvider

/**
  * Spout generating weather conditions for all the cities from the given list
  * Created by Janusz on 2016-03-20.
  */
class WeatherSpout(weatherProvider: WeatherProvider, citiesList: List[String]) extends BaseRichSpout {
  var outputCollector: SpoutOutputCollector = null
  var cities = citiesList

  override def open(map: util.Map[_, _], topologyContext: TopologyContext, spoutOutputCollector: SpoutOutputCollector): Unit = {
    outputCollector = spoutOutputCollector
  }

  override def nextTuple(): Unit = {
    if (cities.isEmpty) {
      return
    }
    val weather = weatherProvider.getWeather(cities.head)
    println("EMITING:" + cities.head + ":" + weather.temperature)
    outputCollector.emit(new Values(cities.head, weather))
    cities = cities.tail
  }

  override def declareOutputFields(outputFieldsDeclarer: OutputFieldsDeclarer): Unit = {
    outputFieldsDeclarer.declare(new Fields("city", "temperature"))
  }

  override def ack(msgId: AnyRef): Unit = {

  }
}
