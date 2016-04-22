package com.starflask.gameinterface

 
  
import com.starflask.util._; 

import com.jme3.network.serializing.Serializer;
import com.jme3.network.serializing.Serializable;


//http://jim-mcbeath.blogspot.com/2009/10/simple-publishsubscribe-example-in.html

//For subscribers of things that turn on and off
class GameActionPublisher extends Publisher[GameActionPublisher.CustomGameAction] 

// use "import AbledPublisher._" to pick up these definitions
object GameActionPublisher {

       
    
     sealed class CustomGameAction() {
       def CustomGameAction(){}
     }
     
     
    @Serializable case class NoAction(params: Map[String,Any]) extends CustomGameAction  {  def this() = this(Map()) }
    @Serializable case class MoveAction(params: Map[String,Any]) extends CustomGameAction {  def this() = this(Map()) }
    @Serializable case class FireAction(params: Map[String,Any]) extends CustomGameAction {  def this() = this(Map()) }
     @Serializable  case class JoinServerAction(params: Map[String,Any]) extends CustomGameAction {    def this() = this(Map())  }

       
     //Registration error: no-argument constructor not found on:class com.starflask.gameinterface.GameActionPublisher$JoinServerAct
     
     
     
    object CustomGameAction { 
        
       def apply(b:Boolean) = if (b) MoveAction else FireAction
       
       }
    
    
    
    
    
    
}

 