package com.starflask.world

import com.starflask.util.Vector3f 

 
 import scala.concurrent.Future

//This is a thread-safe deterministic engine that manipulates ReactiveGameData in a function way
 
// the ReactiveGameData contains a bunch of Units (entities) which can be updated individually
 
object GameEngine {
  
 
  
  class UnitStats(health: Int, powerup: Int)

  class HardUnit(playerId: Int, callback: String, position: Vector3f, stats: UnitStats)
  {
    
	  def onTick() : Unit = {//return a new unit copy 
			
			
			
	  }
    
  }
  
  case class Player(playerId:Int, name: String, callback: String)
 
  
  case class ReactiveGameData(networkTick: Int, units: Map[Int, HardUnit] ) //this is the state that gets passed around each frame... 
  
  
  
  sealed trait GameAction
  
  case class Join(playerId: Int, callback: String) extends GameAction
  case class Move(playerId: Int, position: Vector3f) extends GameAction
  case class ShotFired(playerId: Int, position: Vector3f, facing: Vector3f) extends GameAction //name is the user who placed it
  
  
  /*
object Engine {
 
  def handleAction(action: Action, delay: Int = 4000):     ReactiveGameData   =
    action match {
      case j: Join =>
        val state =
          for {
            board <- AtomicSTRef.get[GameBoard]
            newPlayer = Player(j.name, j.callback, 0, PlayerStats(0, 0, 0)) //in the State Monad, generate a new version of the world with the new player
            _ <- AtomicSTRef.update(board.copy(players = board.players.updated(j.name, newPlayer))) //replace the old version iwth the new version
          } yield ()
         
      case m: Move =>
        val state = positionPlayerLens(m.name) := m.position //:= returns a state which fits in with our 
        
        
  }
  }*/
  
}