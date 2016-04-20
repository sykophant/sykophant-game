package com.starflask.util

import com.badlogic.ashley.core.Component;
import scala.collection.mutable._;

object PublishSubscribeModel {
  
  class Sub[Evt, Pub]() extends Subscriber[Evt, Pub] with Component{
    def notify(pub: Pub, evt: Evt){
  }
}
  
  class Pub[Evt]() extends Publisher[Evt] with Component{}
  
   class PubSub[Evt, Pub2] extends Subscriber[Evt, Pub2] with Publisher[Evt]{
  def notify(pub: Pub2, evt: Evt){
  }
}  
   
   
}