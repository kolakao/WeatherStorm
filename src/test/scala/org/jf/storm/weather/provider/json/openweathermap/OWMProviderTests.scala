package org.jf.storm.weather.provider.json.openweathermap

import java.net.URL

import org.jf.storm.weather.provider.json.WeatherProvider
import org.jf.storm.weather.network.connection.HttpCallProvider
import org.junit.runner.RunWith
import org.mockito.Mockito.when
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

import scala.io.Source


/**
  * Created by Janusz on 2016-03-20.
  */
@RunWith(classOf[JUnitRunner])
class OWMProviderTests extends Specification with Mockito {

  "getWeather" should {
    "return weather for a given city" in {
      val temperatureInPrague = 5f
      val httpCallProviderMock = mock[HttpCallProvider]
      val testOWMWeather = Source.fromURL(getClass.getResource("/OWMTestWeather.json")).getLines()
      when(httpCallProviderMock.getHttpResponse(any[URL])).thenReturn(testOWMWeather.mkString("\n"))
      val weatherProvider: WeatherProvider = new OWMProvider(httpCallProviderMock)
      weatherProvider.getWeather("Prague").temperature must equalTo(temperatureInPrague)
    }
  }
}
