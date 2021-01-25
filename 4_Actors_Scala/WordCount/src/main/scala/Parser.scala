package org.lukaswoodtli

import akka.actor.{Actor, ActorRef}

case object Processed

class Parser(counter: ActorRef) extends Actor {

  val pages = Pages(10000, "/Users/lukaswoodtli/Development/SevenConcurrencyModelsInSevenWeeks/1_ThreadsAndLocks/src/main/resources/enwiki-latest-pages-articles26.xml")

  override def preStart: Unit = {
    for (page <- pages.take(10))
      counter ! page
  }

  def receive = {
    case Processed if pages.hasNext => counter ! pages.next
    case _ => context.stop(self)
  }

}
