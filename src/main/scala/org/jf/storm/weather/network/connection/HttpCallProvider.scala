package org.jf.storm.weather.network.connection

import java.net.URL
import scala.io.Source

/**
  * Created by Janusz on 2016-04-17.
  */
class HttpCallProvider extends Serializable{

  def getHttpResponse(url: URL): String = {
    Source.fromInputStream(url.openStream()).getLines.mkString("\n")
  }


}
