package com.paulbutcher

import akka.actor.{Actor, Props, RootActorPath}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent.{CurrentClusterState, MemberUp}
import akka.cluster.MemberStatus.Up

class Counters(count: Int) extends Actor {

  val counters = context.actorOf(Props[Counter].
    withRouter(new BroadcastRouter(count)), "counter")

  override def preStart = {
    Cluster(context.system).subscribe(self, classOf[MemberUp])
  } 

  def receive = {
    case state: CurrentClusterState =>
      for (member <- state.members if (member.status == Up))
        counters ! ParserAvailable(findParser(member))

    case MemberUp(member) => counters ! ParserAvailable(findParser(member))
  }

  def findParser(member: Member) =
    context.actorSelection(RootActorPath(member.address) / "user" / "parser")

}
