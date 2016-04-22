package com.starflask.gameinterface

import com.starflask.gameinterface.GameActionPublisher.CustomGameAction

class GameActionQueue  {
      
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