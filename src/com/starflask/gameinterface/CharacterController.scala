package com.starflask.gameinterface

import com.starflask.world.GameEngine._ 

class CharacterController {
  
  var gameActionQueue = new GameActionQueue();
  
  var focusedUnitId = 0
  
  //this is all predictive simulation stuff. May be desynced from server so need to correct
  def update(gamedata: ReactiveGameData) 
  {
    //map this for just my main 'focused' character 
    val myChar = gamedata.units.get( focusedUnitId )
    
    //do things to me
  }
   
  
}