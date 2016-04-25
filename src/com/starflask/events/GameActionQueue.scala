package com.starflask.events

import com.starflask.events.GameActionPublisher.CustomGameAction
import com.starflask.events.GenericEventQueue

class GameActionQueue extends GenericEventQueue {
      
    var queuedEvents: List[CustomGameAction] = Nil
    
  def addEvent(ev: CustomGameAction )
  {
   queuedEvents =  queuedEvents.+:(ev)   //add this item to the end
    
    
  }
    
    
     
   def popEvent =  queuedEvents.take(1).last  //first in first out 
   
   //Ugh im jizzing my pants scala is so cool...
   def feedEvents(f:CustomGameAction => Unit)
   { 
     while(!queuedEvents.isEmpty )
     {
       f(popEvent)
     }
   }
    
    
}