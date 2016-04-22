package com.starflask.networking

import com.jme3.network.MessageListener; 
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.starflask.networking.NetworkUtils.NetworkMessage 
import com.starflask.gameinterface.GameActionPublisher._ 

// see https://github.com/jMonkeyEngine/jmonkeyengine/tree/master/jme3-networking/src/main/java/com/jme3/network
class ServerListener extends MessageListener[Any] {
  
    
    def messageReceived(x: Any, y: Message) = y match  //x is the connection
    {
         case n: NetworkMessage => processMessage( rebuildGameActionMessage( n ) ) ; println("Got message")
         
          case _ => println("No message") ; println(x.getClass())
    }
    
    def rebuildGameActionMessage(message: NetworkMessage):CustomGameAction =
    {
       message.action
       
    }
     
      
    
     case class ServerListEntryRequestMessage(x: Int)
     
     
     def processMessage(action: CustomGameAction) = action match
     {
          case j: JoinServerAction => onJoinServer( /*j.typeName*/ )
          case _ => "No message"
     }
     
     
     def onJoinServer()
     {
       println("somebody joined our server ")
     }
     
     
    
}