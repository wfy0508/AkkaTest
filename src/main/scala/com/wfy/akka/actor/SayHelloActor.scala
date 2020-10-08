package com.wfy.akka.actor

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/*
* @Author wfy
* @Date 2020/10/8 17:37
* com.wfy.akka
*/

class SayHelloActor extends Actor {
  /*
    1. 当我们继承Actor之后，就是一个Actor，核心新方法receive需要重写
    2. receive方法，会被该Actor的MailBox（实现了Runnable方法）调用
    3. MailBox收到消息就会调用receive方法
    4. receive是一个偏函数：type Receive = scala.PartialFunction[scala.Any, scala.Unit]
 */
  override def receive: Receive = {
    case "hello" => println("收到hello， 回应hello too")
    case "ok" => println("收到ok， 回应ok too")
    case "exit" =>
      println("接收到exit指令，退出系统")
      context.stop(self) //停止actorRef
      context.system.terminate() //退出ActorSystem
    case _ => println("匹配不到")
  }

}

object SayHelloActorDemo {
  //1. 先创建一个ActorSystem，专门用于创建Actor
  private val actorFactory = ActorSystem("actorFactory")
  //2. 创将一个Actor的同事，返回Actor的ActorRef
  // Props[SayHelloActor]创建了一sayHelloActor的实例，使用反射机制创建
  // "sayHelloActor"给actor取名
  // sayHelloActorRef: ActorRef就是Props[SayHelloActor]的ActorRef
  private val sayHelloActorRef: ActorRef = actorFactory.actorOf(Props[SayHelloActor], "sayHelloActorRef")

  def main(args: Array[String]): Unit = {
    //1. 创建的sayHelloActor实例被ActorSystem接管
    //2. 在ActorSystem中已经有了sayHelloActor实例
    //3. sayHelloActor实例会关联到自己的sayHelloActorRef
    //4. sayHelloActorRef将消息发给消息分发器（Dispatcher Massage）
    //5. 消息分发器在底层找到sayHelloActor（取决于sayHelloActorRef）的MailBox[sayHelloActor]
    //6. MailBox是Runnable状态，会一直持有sayHelloActor的实例，收到消息后，调用实例的receive方法
    sayHelloActorRef ! "hello" //给sayHelloActor发送消息（邮箱）
    //再发一个ok
    sayHelloActorRef ! "ok"
    //再发一个匹配不到的字符
    sayHelloActorRef ! "ok~"
    //发送和接收的顺序一定是有序的，另外由于MainBox是Runnable，在发送完上述消息后并不会退出（需修改receive方法，添加触发退出的指令）
    sayHelloActorRef ! "exit"
  }
}