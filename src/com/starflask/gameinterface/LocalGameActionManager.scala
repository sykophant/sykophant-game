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




import com.starflask.world.GameEngine._;
import scala.collection.mutable.IndexedSeq;
 

class LocalGameActionManager extends Entity   with InputActionExecutor {
  
  
  
  //http://infoscience.epfl.ch/record/148043/files/DeprecatingObserversTR2010.pdf
  //need a better sub pub relationship !!
  
  //http://jim-mcbeath.blogspot.com/2009/04/scala-listener-manager.html
  
  
 // var listeners: List[GameActionListenComponent => ()] = Nil

    
  //http://jim-mcbeath.blogspot.com/2009/10/simple-publishsubscribe-example-in.html
  
   val actionPublisher = new GameActionPublisher
   
   var gamedata = new ReactiveGameData //need to keep setting this .... need all 20 though!!
   

	def LocalInputActionManager()
	{
		this.add(new InputActionComponent( this ));
	}
   
   

	override def executeInputAction(inputAction: InputActionType ,pressed: Boolean ) {
		// RUn client side simulation stuff
		//send the action to the server via RemoteClientConnection
		
	  var gameAction = buildGameAction(inputAction, pressed, gamedata) //whatever we come up with 
	  
	  //a function here will combine the inputaction and the world data and then build  a gameaction 
	  
	  //broadcast to all subscribers
	    actionPublisher.publish(
           gameAction
         )
	   
		
	} 
	
	def buildGameAction( inputAction: InputActionType ,pressed: Boolean, gamedata: ReactiveGameData) :CustomGameAction=
	{    
	  
	  
	      var unitId = gamedata.getFocusedUnitId()
	      var unit = gamedata.getUnit(unitId)
	      
	    inputAction match
	    {
       case _ => FireAction(gamedata.networkTick,unitId, gamedata.getPlayerId(), 
	     unit.getPosition(),unit.getFacing() )
	    }
	}
	
	 var reactiveGameData =   ReactiveGameData(0, Map[Int, HardUnit]())
	 
	 def setReactiveGameData(data: ReactiveGameData)
	 {
	     this.reactiveGameData = data 
	 }
	
	


	 
	
	
}