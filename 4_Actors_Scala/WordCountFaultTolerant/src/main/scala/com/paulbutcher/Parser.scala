package com.paulbutcher

import akka.actor.{Actor, Props}

import scala.collection.mutable


case object RequestBatch
case class Processed(id: Int)

class Parser(filename: String,
             batchSize: Int,
             limit: Int) extends Actor {

  val pages = Pages(limit, filename)
  var nextId = 1
  val pending = mutable.LinkedHashMap[Int, Batch]()

  val accumulator = context.actorOf(Props(new Accumulator(self)))

  def receive = {
    case RequestBatch =>
      if (pages.hasNext) {
        val batch = Batch(nextId, pages.take(batchSize).toVector, accumulator)
        pending(nextId) = batch
        sender ! batch
        nextId += 1
      } else {
        val (id, batch) = pending.head
        pending -= id
        pending(id) = batch
        sender ! batch
      }

    case Processed(id) =>
      pending.remove(id)
      if (!pages.hasNext && pending.isEmpty)
        context.system.terminate()
  }
}
