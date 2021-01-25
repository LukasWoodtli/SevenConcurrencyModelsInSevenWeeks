package org.lukaswoodtli

import akka.actor.{Actor, Props}

case object SayHello
case class SayHelloFrom(path: String)


class TestActor extends Actor {

  def receive: Receive = {
    case CreateChild(name) => context.actorOf(Props[TestActor], name)
    case SayHello => println(s"Hello from $self")
    case SayHelloFrom(path) => context.actorSelection(path) ! SayHello
  }


}
