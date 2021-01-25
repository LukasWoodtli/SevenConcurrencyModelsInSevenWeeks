package org.lukaswoodtli

import akka.actor.{ActorSystem, PoisonPill, Props}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class TestingLifecycleActor extends AnyFlatSpec with should.Matchers {
  val system = ActorSystem("Lifecycle")

  val anActor = system.actorOf(Props[LifecycleActor], "an-actor")
  anActor ! CreateChild("a-child")
  anActor ! PoisonPill

  val parent = system.actorOf(Props[LifecycleActor], "parent")
  parent ! CreateChild("a-child")
  val child = system.actorSelection("/user/parent/a-child")

  child ! Divide(1, 0)
}
