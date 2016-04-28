package com.starflask.world

import com.starflask.events.GameActionPublisher.CustomGameAction
import com.starflask.events.GameActionPublisher.JoinServerAction
import com.starflask.util.Vector3f
import com.starflask.networking.Player
import com.starflask.events.GameActionPublisher.SpawnUnitAction
import com.starflask.world.GameEngine.ReactiveGameData

class GameActionExecutor(gd: ReactiveGameData) {
  
  /*
   * This class takes in custom game actions and manipulates the Reactive Game Data
   * 
   * since this is all turn-based from network ticks and since the server and client run this same code, all reactivegamedatas should always be synchronized!
   * 
   * 
   */
  
  var gamedata = gd;
  
  
   def execute(action: CustomGameAction, tick: Int):ReactiveGameData = action match
     {
          case j: JoinServerAction => onJoinServer( tick, j.playerName )  //throw into a queue?
          case _ => "No message"
     }
     
     
     def onJoinServer(tick:Int, playerName: String )
     {
       println("somebody joined our server " + playerName)
       
     
        var player = addNewPlayer(tick, playerName)
        spawnUnit( tick, player, new Vector3f(0,0,0), new Vector3f(0,0,0)  );   //need a fucntion to give us 
       
     }
     
     def addNewPlayer(): Player
     {
          var newPlayer = new Player();
          
     }
     
     
     def spawnUnit(tick:Int, owner: Player, pos: Vector3f, fac: Vector3f)
     {
       var unitId = 1 ;//fix me 
       var spawnAction = new SpawnUnitAction(tick,unitId,owner.id,pos,fac);
       
        spawnUnit(  ); 
      
       
     }
     
     
     
}