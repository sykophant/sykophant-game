package com.starflask.networking

import com.jme3.network._;
import com.starflask.networking.NetworkUtils;
import com.starflask.networking.ClientListener;
import com.starflask.gameinterface._;

import com.jme3.network.service.serializer.ClientSerializerRegistrationsService;
 import java.lang.Runnable;
//This is opposite of 'GameServerProcess.scala'



object TestClient {
  
  def main(args: Array[String]): Unit = {
    var testclient = new RemoteClientConnection();
    var thread = new Thread(testclient);
    thread.start( );
  }
}

/*
 * 
 * IF the server is a listen server, one of these will be run by the 'host' as well..
 * 
 */
class RemoteClientConnection extends Runnable{
  
    var lastSampleTime = System.currentTimeMillis
  final val ACTION_SAMPLING_RATE = 33   //ms... this is about 30 fps
  
  
  val actionListener = new GameActionListenComponent()  //localgameactionmanager talks to this
  
  
  def build()
  {
      var myClient = Network.connectToServer("localhost", 6143);
       myClient.getServices().removeService(myClient.getServices().getService( classOf[ClientSerializerRegistrationsService] ));
     
       myClient.start();
       
       var myListener = new ClientListener();
   
     NetworkUtils.registerMessageTypes(myClient, myListener );
    
  }
  
  
  override def run()
  {
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
     
    
    
  }
    
    
    
  
  
}