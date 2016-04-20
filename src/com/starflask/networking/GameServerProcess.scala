package com.starflask.networking

 import java.lang.Runnable;

import com.jme3.network._;
import com.starflask.networking._;
import com.jme3.network.service.serializer.ServerSerializerRegistrationsService;

object TestServer {
  def main(args: Array[String]): Unit = {
    var testserver = new GameServerProcess();
    var thread = new Thread(testserver);
    thread.start( );
  }
}


class GameServerProcess extends Runnable{
  
  override def run()
  {
    
    build();
    
    while(true)
    {
      update();
      
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
  
  def update()
  {
    
    
  }
  
  
  
}