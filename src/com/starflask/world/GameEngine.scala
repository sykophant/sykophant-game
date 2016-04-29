package com.starflask.world

import com.starflask.util.Vector3f 

 
 import scala.concurrent.Future
 import com.starflask.units.HardUnit
 import scala.collection.mutable.ArrayBuffer
 import com.starflask.util.PositionVector

//This is a thread-safe deterministic engine that manipulates ReactiveGameData in a function way
 
// the ReactiveGameData contains a bunch of Units (entities) which can be updated individually
 
object GameEngine {
  
 /*
  * 
  * Mutable:
			Voxel grids/volumes (my games are generally tile/voxel based)
				My entities (and their animation matrices)    --cant keep copying them because of GC issues
			"Tesselators" wrappers for Float arrays used for temporarily storing generated geometry.
  */
  
 
  case class Player(playerId:Int, n: String)
  {
    var id = playerId;
    var name = n;
  }
 
  
  case class ReactiveGameData(networkTick: Int) //this is the state that gets passed around each frame... 
  {
    
    def this() = this(0 ) 
    
    //this are player and unit lists - they are mutable to be easy on the GC 
    var players =  Map[Int, Player]()
    var units =   Map[Int, HardUnit]()
    
    var localPlayerId = -1
    
    var focusedUnit = -1
    
	      def getFocusedUnitId():Int = focusedUnit
	      
	      def getUnit(unitId:Int):Option[HardUnit] = units.lift(unitId)
	      
	      def getPlayerId():Int = localPlayerId
    
    
  }
  
  class TeamData()
  {
    var name = "New Team"
    var color = "#FF0000"
  }
  
  class BlockType()
  {
    var name = "New Block"
    var spawnable = Map[TeamData, Boolean]()  //can this team spawn here?
    var invisible = false;
    var collidable = true;  //used to build a collision map
    
    
    
  }
  
  case class BlackPrint()//loaded from a file
  {
    
    var teams = Map[Int, TeamData]()
    
    var blockTypes = Map[Int, BlockType]()
    
    var name = "New Blackprint"
    
    var mapHash = "maphash"
    
    
  }
  
  
  
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