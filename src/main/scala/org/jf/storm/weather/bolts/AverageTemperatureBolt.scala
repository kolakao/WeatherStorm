package org.jf.storm.weather.bolts

import java.util

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.OutputFieldsDeclarer
import org.apache.storm.topology.base.BaseRichBolt
import org.apache.storm.tuple.{Fields, Tuple, Values}
import org.jf.storm.weather.dataobjects.Weather

import scala.collection.mutable

/**
  * Created by Janusz on 2016-04-23.
  */
class AverageTemperatureBolt extends BaseRichBolt {

  var outputCollector: OutputCollector = null
  val temperatures = new mutable.HashMap[String, Float]

  override def execute(input: Tuple): Unit = {
    val city = input.getString(0)
    val temperature = input.getValue(1).asInstanceOf[Weather].temperature

    temperatures get city match {
      case None => temperatures += (city -> temperature)
      case Some(_) => temperatures += (city -> (temperature + temperatures(city)) / 2)
    }

    println("Emiting avg temperature: " + city + ": " + temperatures(city))
    outputCollector.emit(new Values(city, temperatures get city))
    outputCollector.ack(input)
  }

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector): Unit = {
    outputCollector = collector
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = {
    declarer.declare(new Fields("city", "averageTemperature"))
  }
}
