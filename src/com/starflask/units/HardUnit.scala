package com.starflask.units

import com.starflask.util.Vector3f
import com.badlogic.ashley.core.Entity


 
case class UnitStats(ownerId:Int, health: Int, powerup: Int)
	
	
	class HardUnit(   p: Vector3f, f:Vector3f, s: UnitStats) extends Entity 
	{
  
  
  var position = p  //put these in a component ?
  var facing = f 
  var stats = s
  

	  def onTick( /* gamedata   */  ) : HardUnit = {//return a new unit copy 
			  
    
    //update w changes from gamedata   - this morphs the unit based on gameactions and returns the changed copy
			new HardUnit( position,facing, stats )
			
	  }
	  
	  
	  def getPosition(): Vector3f = position
  
	   def getFacing(): Vector3f = facing
	   
}
	 

	