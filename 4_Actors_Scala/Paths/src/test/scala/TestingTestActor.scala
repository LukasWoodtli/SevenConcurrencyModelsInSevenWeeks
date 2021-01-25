package org.lukaswoodtli

import akka.actor.{ActorSystem, Props}
import org.scalatest._
import flatspec._
import matchers._

class TestingTestActor extends AnyFlatSpec with should.Matchers {
  val system = ActorSystem("Paths")

  val anActor = system.actorOf(Props[TestActor], "an-actor")
  anActor ! SayHello

  anActor ! CreateChild("a-child")

  val aChild = system.actorSelection("/user/an-actor/a-child")
  aChild ! SayHello

  anActor ! CreateChild("another-child")
  aChild ! SayHelloFrom("../another-child")

  val children = system.actorSelection("/user/an-actor/*")
  children ! SayHello

}