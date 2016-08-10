package org.jf.storm.weather

import org.apache.storm.topology.TopologyBuilder
import org.apache.storm.{Config, LocalCluster}
import org.jf.storm.weather.bolts.AverageTemperatureBolt
import org.jf.storm.weather.provider.json.openweathermap.OWMProvider
import org.jf.storm.weather.provider.json.yahoo.YahooProvider
import org.jf.storm.weather.network.connection.HttpCallProvider
import org.jf.storm.weather.spouts.WeatherSpout

/**
  * Created by Janusz on 2016-03-19.
  */
object Main extends App {

  {
    val cities = List("Prague", "Szczekociny", "Berlin")
    val stormConf = new Config
    val topologyBuilder = new TopologyBuilder
    val httpCallProvider = new HttpCallProvider
    topologyBuilder.setSpout("OWMSpout", new WeatherSpout(new OWMProvider(httpCallProvider), cities))
    topologyBuilder.setSpout("YahooWeatherSpout", new WeatherSpout(new YahooProvider(httpCallProvider), cities))
    topologyBuilder.setBolt("AvgTempBolt", new AverageTemperatureBolt).shuffleGrouping("OWMSpout").shuffleGrouping("YahooWeatherSpout")
    val cluster = new LocalCluster
    cluster.submitTopology("Weather Topology", stormConf, topologyBuilder.createTopology())
  }

}
