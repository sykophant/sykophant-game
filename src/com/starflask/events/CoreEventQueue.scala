package com.starflask.events

import com.starflask.events.CoreEventPublisher.CoreEvent

class CoreEventQueue  {
      
    var queuedEvents: List[CoreEvent] = Nil
    
  def addEvent(ev: CoreEvent )
  {
   queuedEvents =  queuedEvents.+:(ev)   //add this item to the end 
    
  }
    
    
     
   def popEvent =  queuedEvents.take(1).last  //first in first out 
   
   //Ugh im jizzing my pants scala is so cool...
   def feedEvents(f:CoreEvent => Unit)
   { 
     while(!queuedEvents.isEmpty )
     {
       f(popEvent)
     }
   }
    
    
}