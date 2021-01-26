package com.paulbutcher

import akka.actor.{Actor, ActorRef}

import scala.collection.mutable.HashMap
import scala.collection.mutable.Map

case class Counts(id: Int, counts: Map[String, Int])

class Accumulator(parser: ActorRef) extends Actor {

  var counts = HashMap[String, Int]().withDefaultValue(0)
  var processedIds = Set[Int]()

  def receive = {
    case Counts(id, partialCounts) =>
      if (!processedIds.contains(id)) {
        for ((word, count) <- partialCounts) {
          var newCounts = counts(word) + counts
          counts(word) = newCounts
        }
        processedIds += id
        parser ! Processed(id)
      }
  }
}
