package org.jf.storm.weather.provider.json.yahoo

import net.liftweb.json.JsonAST.{JNothing, JValue}
import net.liftweb.json.{DefaultFormats, JsonParser}
import org.jf.storm.weather.dataobjects.Weather
import org.jf.storm.weather.provider.json.WeatherProvider
import org.jf.storm.weather.provider.json.yahoo.yahooJson.Item
import org.jf.storm.weather.network.connection.HttpCallProvider

/**
  * Weather provider using Yahoo services as a data source
  * Created by Janusz on 2016-03-20.
  */
class YahooProvider(httpCallProvider: HttpCallProvider) extends WeatherProvider {

  // Set default formats for json serializer
  private implicit val formats = DefaultFormats

  val yahooAppId = "dj0yJmk9ZXRNcUtRSzdncm1TJmQ9WVdrOVUwbDRkRU0xTkc4bWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD1lYQ--"
  val yahooAppSecret = "01b5c78122dc2871b5c213e7fbc5921093035095"

  override def getWeather(city: String): Weather = {
    val resultsJson = parseJson(city)
    val items = resultsJson.children.filter(jVal => valid(jVal)).map(toItem)
    new Weather(items.head.condition.temp.toFloat)
  }


  private def parseJson(city: String) = {
    val url = new java.net.URL(s"https://query.yahooapis.com/v1/public/yql?q=%0Aselect%20item.title%2Citem.condition%20from%20weather.forecast%20where%20woeid%20in%20(%0Aselect%20woeid%20from%20geo.places%20where%20text%3D%22$city%22%0A)%20and%20u%3D%22c%22&format=json&diagnostics=true&callback=")
    val content = httpCallProvider.getHttpResponse(url)
    val json = JsonParser.parse(content)
    json \ "query" \ "results" \ "channel"
  }

  /**
    * We are checking the depth of the json because Yahoo API is totally inconsistent thus sometimes
    * as root object we get outer object and sometimes we get Item
    *
    * @param jVal JSON object
    * @return true if the object is valid and false otherwise
    */
  private def valid(jVal: JValue) = {
    val title = jVal \ "item" match {
      case JNothing => jVal \ "title"
      case anythingElse => (jVal \ "item") \ "title"
    }
    !title.extract[String].contains("Error")
  }

  private def toItem(jVal: JValue): (Item) = {
    jVal \ "item" match {
      case JNothing => jVal
      case anythingElse => jVal \ "item"
    }
  }.extract[Item]


}