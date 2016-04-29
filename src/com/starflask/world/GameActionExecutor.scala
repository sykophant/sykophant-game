package com.starflask.world

import com.starflask.events.GameActionPublisher.CustomGameAction
import com.starflask.events.GameActionPublisher.JoinServerAction
import com.starflask.util.Vector3f
import com.starflask.events.GameActionPublisher.SpawnUnitAction
import com.starflask.world.GameEngine.ReactiveGameData
import com.starflask.util.PositionVector
import com.starflask.events.GameActionPublisher.ServerStatusAction
import scala.util.Random
import com.starflask.world.GameEngine.Player

class GameActionExecutor(gd: ReactiveGameData) {
  
  /*
   * This class takes in custom game actions and manipulates the Reactive Game Data
   * 
   * since this is all turn-based from network ticks and since the server and client run this same code, all reactivegamedatas should always be synchronized!
   * 
   * 
   */
  
  var gamedata = gd;
  var randomSeed = new Random; //this should always be the first thing set by the server 
  
   def execute(action: CustomGameAction, tick: Int)  = action match
     {
          case s: ServerStatusAction => establishServerSettings( s.randomSeed )
          case j: JoinServerAction => onJoinServer( tick, j.playerName )  //throw into a queue?
          case _ => "No message"
     }
     
    def getSpawnLocation(): PositionVector = {
      //pick a spawn location using deterministic random seed (spawn locs are a blocktype in the map, defined in the json blackprint)
      new PositionVector()
    }
   
     def establishServerSettings(seed: Random)
     {
       //need to stream in the map and the blackprint (just move whole map and blackprint files)
       randomSeed = seed; 
     }
     
     def onJoinServer(tick:Int, playerName: String )
     {
       println("somebody joined our server " + playerName)
       
       var spawnLoc = getSpawnLocation();
     
        var player = addNewPlayer(tick, playerName)
        spawnUnit( tick, player, spawnLoc.position, spawnLoc.facing  );   //need a fucntion to give us 
       
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
     
     def getDeterministicRandomFloat():Float=
     {
       randomSeed.nextFloat()
     }
     
}