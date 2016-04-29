package com.starflask.networking

 import java.lang.Runnable;

import com.jme3.network._;
import com.starflask.networking._;
import com.jme3.network.service.serializer.ServerSerializerRegistrationsService;
import com.starflask.events.GameActionQueue
import com.starflask.events.GameActionPublisher.CustomGameAction
import com.starflask.events.GameActionPublisher.JoinServerAction
import com.starflask.networking.NetworkUtils.NetworkMessage 
import com.starflask.events.GameActionPublisher.SpawnUnitAction
import com.starflask.util.Vector3f
import com.starflask.world.World
import com.starflask.world.GameEngine.ReactiveGameData 
import com.starflask.world.GameActionExecutor
import com.starflask.events.GameActionPublisher.NetworkTickAction
import com.starflask.events.GameActionPublisher.ServerStatusAction
import scala.util.Random

/*
 * Just like sands..
 * Our server constantly sends all unit position+facing data to all clients
 * The client constantly sends its owned unit position+facing data to the server (ignores the value the server gives it for its own unit)
 * 
 * Everything else besides position is handled in a turn-based fashion...  
 *  -The clients literally send their KEY AND MOUSE input commands to the server ! (there is a command for Shoot etc...) 
 *  
 *  
 *  
 *
 */
object TestServer {
  def main(args: Array[String]): Unit = {
   var world = new World();
    var testserver = new GameServerProcess(world.gameActionExecutor, Math.random());
    var thread = new Thread(testserver);
    thread.start( );
  }
}


class GameServerProcess(gameActionExecutor: GameActionExecutor, seed: Double ) extends Runnable{
  
  var randomSeed = new Random((seed*100000).toLong);
  var actionExecutor = gameActionExecutor;  //used to push discrete game actions to the World
  var gamedata = actionExecutor.gamedata;  //used to push unit pos and stat update to the clients
  
  var lastTickTime = System.currentTimeMillis
  final val NETWORK_TICK_RATE = 66  //66 millis
  
   var networkTick = 0
   
    var gameActionQueue = new GameActionQueue();
  
  override def run()
  {
    
     
    build(); 
    
     
    
    while(true)
    {
      loop( update(networkTick) , NETWORK_TICK_RATE );
    }
  }
  
  
  case class ServerData(networkTick:Int)
  
  /*
   * This ensures that we are only running the given function every so often - not as often as physically possible
   * 
   */
  def loop(updateFunction: => Unit , tick_rate: Int)
  {
    var delta  = System.currentTimeMillis - lastTickTime  
      
    if( delta > tick_rate ) 
    {
     updateFunction 
     
     networkTick += 1
      
    lastTickTime = System.currentTimeMillis
     
      
    } 
    
  }
  
  private var myServer = Network.createServer(6143);
  
  def build()
  {
    
   
    
    myServer.getServices().removeService(myServer.getServices().getService( classOf[ServerSerializerRegistrationsService] ));
    
    myServer.start();
    
    var listener = new ServerListener( gameActionQueue  );
    
    NetworkUtils.registerMessageTypes(myServer, listener);
    
     println("server is listening for packets ")
  }
  
  /*
   * we send out players health, status, and positions to the clients
   * 
   */
  
  def update(networkTick: Int )
  {
    
    //pop events off the stack and process them (only process them in the right network tick?)
    
    gameActionQueue.feedEvents { ev => processAction(ev,networkTick);broadcastAction(ev) }
    
    //should rebroadcast these out!
    
    broadcastAction(new NetworkTickAction( networkTick ));  //all tcp so all messages will go in order... locksteppy..
    
  }
    
    def broadcastAction(action: CustomGameAction)  
     {
          
           myServer.broadcast(new NetworkMessage(action));
           
     }
    
    def processAction(action: CustomGameAction, tick: Int)
    {
      
      action match 
      { //when a new player joins, get that player the deterministic random seed
        case j : JoinServerAction => broadcastAction(new ServerStatusAction(randomSeed));
      }
      
      actionExecutor.execute(action,tick);
    }
    
   
     def getCurrentNetworkTick() = networkTick
     
     
  
  
}