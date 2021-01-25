package org.lukaswoodtli

import akka.actor.{Actor, ActorRef, PoisonPill, Props, Terminated}

class Master extends Actor {

  val counter: ActorRef = context.actorOf(Props[Counter], "counter")
  val parser: ActorRef = context.actorOf(Props(new Parser(counter)), "parser")

  override def preStart: Unit = {
    context.watch(counter)
    context.watch(parser)
  }

  def receive: Receive = {
    case Terminated(`parser`) => counter ! PoisonPill
    case Terminated(`counter`) => context.system.terminate()
  }

}
