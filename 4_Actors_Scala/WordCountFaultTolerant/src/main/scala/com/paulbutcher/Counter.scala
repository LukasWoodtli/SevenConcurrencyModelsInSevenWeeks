package com.paulbutcher

import akka.actor.{Actor, ActorRef}

import scala.collection.mutable.HashMap

case class ParserAvailable(parser: ActorRef)
case class Batch(id: Int, pages: Seq[Page], accumulator: ActorRef)

class Counter extends Actor {

  def receive = {
    case ParserAvailable(parser) => parser ! RequestBatch

    case Batch(id, pages, accumulator) =>
      sender ! RequestBatch
      val counts = HashMap[String, Int]().withDefaultValue(0)
      for (page <- pages)
        for (word <- Words(page.text))
          counts(word) += 1
      accumulator ! Counts(id, counts)
  }

}
