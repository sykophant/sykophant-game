package com.starflask.networking

import com.jme3.network.MessageListener; 
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;

// see https://github.com/jMonkeyEngine/jmonkeyengine/tree/master/jme3-networking/src/main/java/com/jme3/network
class ServerListener extends MessageListener[Any] {
  
    
    def messageReceived(x: Any, y: Message) = x match
    {
         case x: ServerListEntryRequestMessage => "one"
        case 2 => "two"
        case _ => "many"
    }
    
     case class ServerListEntryRequestMessage(x: Int)
    
}