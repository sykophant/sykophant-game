package com.starflask.networking

import com.jme3.network._;
import com.starflask.networking.NetworkUtils;
import com.starflask.networking.ClientListener;
import com.starflask.gameinterface._;

 
import com.starflask.events.GameActionPublisher._;

import com.jme3.network.service.serializer.ClientSerializerRegistrationsService;
 import java.lang.Runnable; 
 import com.starflask.networking.NetworkUtils.NetworkMessage
 import com.starflask.events.GameActionPublisher._ 

//This is opposite of 'GameServerProcess.scala'
 import com.starflask.events.GameActionQueue
 import com.starflask.world.World
 import com.starflask.world.GameActionExecutor
  


object TestClient {
  
  def main(args: Array[String]): Unit = {
    var world = new World();
    var testclient = new RemoteClientConnection( world.gameActionExecutor  );
    var thread = new Thread(testclient);
    thread.start( );
  }
}

/*
 * 
 * IF the server is a listen server, one of these will be run by the 'host' as well..
 * 
 */
class RemoteClientConnection( executor: GameActionExecutor ) extends Runnable{
  
    var lastSampleTime = System.currentTimeMillis
  final val ACTION_SAMPLING_RATE = 33   //ms... this is about 30 fps
  
    var gameActionQueue = new GameActionQueue();
  
  def build(playerName:String)  //bundle up playername?
  {
      var myClient = Network.connectToServer("localhost", 6143);
       myClient.getServices().removeService(myClient.getServices().getService( classOf[ClientSerializerRegistrationsService] ));
     
       myClient.start(); //connect to the server 
       
       
       var myListener = new ClientListener(gameActionQueue);
   
     NetworkUtils.registerMessageTypes(myClient, myListener );
     
       myClient.send(new NetworkMessage( new JoinServerAction(  playerName   )  ) )
     
        println("sent join server packet ")
    
  }
  
   
  
  
  override def run()
  {
    build( "PlayerOne"  )   //somehow get this from game settings
    while(true)
    {
      loop( update() , ACTION_SAMPLING_RATE );
    }
  }
  
  
  
   def loop(updateFunction: => Unit , tick_rate: Int)
  {
    var delta  = System.currentTimeMillis - lastSampleTime  
      
    if( delta > tick_rate ) 
    {
     updateFunction 
     
     
    lastSampleTime = System.currentTimeMillis
     
      
    } 
    
  }
  
  
  /*
   * This executes every 55 ms.  At that point in time, we send our units position and rotation to the server.
   * We also send a list of queued actions such as 'shoot' and 'crouch' to the server 
   */
    def update(  )
  {
     //sample actions and then send them out over to the server..server will change some gamestate values based on this 
     
    
    gameActionQueue.feedEvents { ev => processAction(ev);  }
  }
    
    
    def processAction(action: CustomGameAction)
    {
          println("server sent us a gameaction " + action.toString());
    }
    
  
  
}