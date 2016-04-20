package com.starflask.networking

import com.jme3.network._;
import com.starflask.networking.NetworkUtils;
import com.starflask.networking.ClientListener;

import com.jme3.network.service.serializer.ClientSerializerRegistrationsService;

object TestClient {
  def main(args: Array[String]): Unit = {
    var testclient = new RemoteClientConnection();
    testclient.build();
  }
}

class RemoteClientConnection {
  
   
  
  def build()
  {
      var myClient = Network.connectToServer("localhost", 6143);
       myClient.getServices().removeService(myClient.getServices().getService( classOf[ClientSerializerRegistrationsService] ));
     
       myClient.start();
       
       var myListener = new ClientListener();
   
     NetworkUtils.registerMessageTypes(myClient, myListener );
    
  }
  
  
}