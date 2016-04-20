package com.starflask.gameinterface

 
  
import com.starflask.util._;
import com.starflask.util.PublishSubscribeModel._;


//http://jim-mcbeath.blogspot.com/2009/10/simple-publishsubscribe-example-in.html

//For subscribers of things that turn on and off
class GameActionPublisher extends Publisher[GameActionPublisher.CustomGameAction]

// use "import AbledPublisher._" to pick up these definitions
object GameActionPublisher {

    sealed abstract class CustomGameAction { val state:Boolean }
    case object MoveAction extends CustomGameAction { override val state = true }
    case object FireAction extends CustomGameAction { override val state = false }

    object CustomGameAction { def apply(b:Boolean) = if (b) MoveAction else FireAction }
}

 