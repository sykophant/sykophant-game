package com.starflask.units

import com.starflask.util.Vector3f
import com.badlogic.ashley.core.Entity


 
case class UnitStats(health: Int, powerup: Int)
	
	
	class HardUnit(playerId: Int, callback: String, p: Vector3f, f:Vector3f, stats: UnitStats) extends Entity 
	{
  
  
  var position = new Vector3f(0,0,0)  //put these in a component ?
  var facing = new Vector3f(0,0,0) 
  
  

	  def onTick() : Unit = {//return a new unit copy 
			
			
			
	  }
	  
	  
	  def getPosition(): Vector3f = position
  
	   def getFacing(): Vector3f = facing
	   
}
	 

	