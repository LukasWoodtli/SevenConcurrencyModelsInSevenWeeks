package com.paulbutcher

import javax.xml.stream.XMLInputFactory
import java.io.FileInputStream

case class Page(title: String, text: String)

class Pages(maxPages: Int, fileName: String) extends Iterator[Page] {
  var remainingPages = maxPages
  val reader = XMLInputFactory.newInstance.createXMLEventReader(new FileInputStream(fileName))
  
  def hasNext = remainingPages > 0
  
  def next(): Page = {
    while (true) {
      val event = reader.nextEvent
      if (event.isStartElement &&
          event.asStartElement.getName.getLocalPart == "page") {
        var title: String = null
        var text: String = null
        while (true) {
          val event = reader.nextEvent
          if (event.isStartElement) {
            event.asStartElement.getName.getLocalPart match {
              case "title" => title = reader.getElementText
              case "text" => text = reader.getElementText
              case _ =>
            }
          } else if (event.isEndElement &&
              event.asEndElement.getName.getLocalPart == "page") {
            remainingPages -= 1
            return Page(title, text)
          }
        }
      }
    }
    throw new NoSuchElementException
  }
}

object Pages {
  def apply(maxPages: Int, fileName: String) = new Pages(maxPages, fileName)
}
