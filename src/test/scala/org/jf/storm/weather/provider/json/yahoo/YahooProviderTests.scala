package org.jf.storm.weather.provider.json.yahoo

import java.net.URL

import org.jf.storm.weather.provider.json.WeatherProvider
import org.jf.storm.weather.network.connection.HttpCallProvider
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

import scala.io.Source

/**
  * Created by Janusz on 2016-04-17.
  */
@RunWith(classOf[JUnitRunner])
class YahooProviderTests extends Specification with Mockito {

  "getWeather" should {
    "return weather for a given city" in {
      val temperatureInPrague = 18.0f
      val httpCallProviderMock = mock[HttpCallProvider]
      val testYahooWeather = Source.fromURL(getClass.getResource("/YahooTestWeather.json")).getLines()
      when(httpCallProviderMock.getHttpResponse(any[URL])).thenReturn(testYahooWeather.mkString("\n"))
      val weatherProvider: WeatherProvider = new YahooProvider(httpCallProviderMock)
      weatherProvider.getWeather("Prague").temperature must equalTo(temperatureInPrague)
    }

    "Throw an exception when the returned json is unparseable" in {
      val httpCallProviderMock = mock[HttpCallProvider]
      when(httpCallProviderMock.getHttpResponse(any[URL])).thenReturn("Unparseable response")
      val weatherProvider: WeatherProvider = new YahooProvider(httpCallProviderMock)
      weatherProvider.getWeather("Prague") must throwA[Exception]
    }
  }

}
