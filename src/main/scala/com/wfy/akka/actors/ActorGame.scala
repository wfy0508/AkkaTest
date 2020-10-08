package com.wfy.akka.actors

import akka.actor.{ActorRef, ActorSystem, Props}

/*
* @Author wfy
* @Date 2020/10/8 20:28
* com.wfy.akka.actors
*/

object ActorGame extends App {
  //创建ActorSystem
  val actorFactory = ActorSystem("actorFactory")
  //先创建Actor引用/代理
  val bActorRef: ActorRef = actorFactory.actorOf(Props[BActor], "bActor")
  //创建AActor的引用，并持有BActor的actorRef
  val aActorRef = actorFactory.actorOf(Props(new AActor(bActorRef)), "aActor")

  //由aActor出招
  aActorRef ! "start"

}
