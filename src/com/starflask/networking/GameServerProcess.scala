package com.starflask.networking

 import java.lang.Runnable;

import com.jme3.network._;
import com.starflask.networking._;
import com.jme3.network.service.serializer.ServerSerializerRegistrationsService;

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
    var testserver = new GameServerProcess();
    var thread = new Thread(testserver);
    thread.start( );
  }
}


class GameServerProcess extends Runnable{
  
  var lastTickTime = System.currentTimeMillis
  final var NETWORK_TICK_RATE = 66  //15 millis
  
   var networkTick = 0
  
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
  
  
  def build()
  {
    
    var myServer = Network.createServer(6143);
    
    myServer.getServices().removeService(myServer.getServices().getService( classOf[ServerSerializerRegistrationsService] ));
    
    myServer.start();
    
    var listener = new ServerListener();
    
    NetworkUtils.registerMessageTypes(myServer, listener);
    
  }
  
  def update(networkTick: Int )
  {
    println("updating " +networkTick) 
    
    

    
    
  }
  
  
  
}