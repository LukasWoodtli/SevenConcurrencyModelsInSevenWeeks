package org.lukaswoodtli

import akka.actor.Actor

import scala.collection.mutable

class Counter extends Actor {

  val counts = mutable.HashMap[String, Int]().withDefaultValue(0)

  def receive = {
    case Page(title, text) =>
      for (word <- Words(text))
        counts(word) += 1

      sender ! Processed
  }
}
