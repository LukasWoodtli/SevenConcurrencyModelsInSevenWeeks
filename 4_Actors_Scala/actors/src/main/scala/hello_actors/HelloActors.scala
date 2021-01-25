package org.woodtlilukas
package hello_actors

import akka.actor.{Actor, ActorRef, ActorSystem, PoisonPill, Props, Terminated}

case class Greet(name: String)
case class Praise(name: String)
case class Celebrate(name: String, age: Int)


class Talker extends Actor {

  def receive: Receive = {
    case Greet(name) => println(s"Hello $name")
    case Praise(name) => println(s"$name, you're amazing")
    case Celebrate(name, age) => println(s"Here's to another $age, $name")
  }
}


class Master extends Actor {

  val talker: ActorRef = context.actorOf(Props[Talker], "talker")

  override def preStart: Unit = {
    // establishing a death watch
    context.watch(talker)

    talker ! Greet("Huey")
    talker ! Praise("Dewey")
    talker ! Celebrate("Louie", 16)

    talker ! PoisonPill
  }

  def receive: Receive = {
    // `talker`: backticks to match value of existing talker variable
    case Terminated(`talker`) => context.system.terminate()
  }
}

object HelloActors extends App {

  val system = ActorSystem("HelloActors")

  // `Props` acts as a factory
  system.actorOf(Props[Master], "master")
}
