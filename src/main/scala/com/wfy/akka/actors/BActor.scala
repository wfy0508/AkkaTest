package com.wfy.akka.actors

import akka.actor.Actor

/*
* @Author wfy
* @Date 2020/10/8 20:27
* com.wfy.akka.actors
*/

class BActor extends Actor{
  override def receive: Receive = {
    case "我打" =>
      println("BActor(乔峰) 看我降龙十八掌")
      //通过sender()，可以获取到发送方的actorRef
      Thread.sleep(1000)
      sender() ! "我打"
  }
}
