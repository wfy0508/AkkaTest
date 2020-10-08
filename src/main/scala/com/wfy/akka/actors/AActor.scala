package com.wfy.akka.actors

import akka.actor.{Actor, ActorRef}

/*
* @Author wfy
* @Date 2020/10/8 20:27
* com.wfy.akka.actors
*/

class AActor(actorRef: ActorRef) extends Actor {

  val bActorRef: ActorRef = actorRef

  override def receive: Receive = {
    case "start" =>
      println("AActor, start ok！")
      Thread.sleep(1000)
      self ! "我打" //发给自己
    case "我打" =>
      //给BActor发出消息，这里需要持有BActor的引用（BActorRef）
      println("AActor(黄飞鸿) 看我佛山无影脚")
      Thread.sleep(1000)
      bActorRef ! "我打"
  }
}
