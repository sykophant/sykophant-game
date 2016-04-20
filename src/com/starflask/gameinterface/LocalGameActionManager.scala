package com.starflask.gameinterface

import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.starflask.peripherals.InputActionComponent;
import com.starflask.peripherals.InputActionExecutor;
import com.starflask.peripherals.InputActionType;
import com.starflask.gameinterface._;
import com.starflask.gameinterface.GameActionPublisher._;

import com.starflask.util._;
import com.starflask.util.PublishSubscribeModel._;



import com.starflask.world.GameEngine._;
import scala.collection.mutable.IndexedSeq;
 

class LocalGameActionManager extends Entity   with InputActionExecutor {
  
  
  
  //http://infoscience.epfl.ch/record/148043/files/DeprecatingObserversTR2010.pdf
  //need a better sub pub relationship !!
  
  //http://jim-mcbeath.blogspot.com/2009/04/scala-listener-manager.html
  
  
 // var listeners: List[GameActionListenComponent => ()] = Nil

    
  
  
 private val actionPublisher = new GameActionPublisher
  
  //var listeners =  IndexedSeq[GameActionListenComponent];

	def LocalInputActionManager()
	{
		this.add(new InputActionComponent( this ));
	}

	override def executeInputAction(inputAction: InputActionType ,pressed: Boolean ) {
		// RUn client side simulation stuff
		//send the action to the server via RemoteClientConnection
		
	  var gameAction = 0 //whatever we come up with 
	  
	  
	    actionPublisher.publish(
            MoveAction
          )
	  
  	 // broadcastGameAction(listeners, gameAction);
  	   
		
	} 
	
	 var reactiveGameData =   ReactiveGameData(0, Map[Int, HardUnit]())
	 
	 def setReactiveGameData(data: ReactiveGameData)
	 {
	     this.reactiveGameData = data 
	 }
	
	
	def registerListener(updateFunction: => Unit)
	{
		 
	  
	  //subscribe a function to the publish event (in scala we subscribe the function not the class) 
 	      actionPublisher.subscribe(  updateFunction    ) //this is looking for a function
 	      
 	      
 	      // Unit required: LocalGameActionManager.this.actionPublisher.S (which expands to) com.starflask.gameinterface.GameActionPublisher.CustomGameAction ⇒ Unit
	}
	
	def broadcastGameAction(listeners: IndexedSeq[_],gameAction: GameAction)
	{
	  listeners match {
	    case actionlisteners: IndexedSeq[GameActionListenComponent] => 
	      
	         val it = actionlisteners.iterator
            while( it.hasNext  ) {
              it.next().executeGameAction( gameAction) 
            }
	         
	    case   _ => println("No valid listeners ")
	  }
	  
	  
	 
	}
	
	
	
}