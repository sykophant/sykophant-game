package com.starflask.events

 
  
import com.starflask.util._
import com.jme3.network.serializing.Serializable;
import com.starflask.events.GameActionPublisher


//http://jim-mcbeath.blogspot.com/2009/10/simple-publishsubscribe-example-in.html

//For subscribers of things that turn on and off
class CoreEventPublisher extends Publisher[GameActionPublisher.CustomGameAction] 

// use "import AbledPublisher._" to pick up these definitions
object CoreEventPublisher {

       
    
     sealed class CoreEvent() {
       def CoreEvent(){}
     }
     
     
    @Serializable case class NoEvent() extends CoreEvent  {  
      //def this() = this() not needed
      }
   
    @Serializable case class StartServerEvent(  ) extends CoreEvent {  
     // def this() = this( ) 
      
      }
    
    @Serializable case class JoinServerEvent(ipAddress: String  ) extends CoreEvent {  
         def this() = this( "localhost" ) 
      
      }
    
  
    
     
 
     
    object CoreEvent { 
        
      // def apply(b:Boolean) = if (b) MoveAction else FireAction
       
      }
    
    
    
    
    
    
}

 