package com.starflask.networking

import com.jme3.network.MessageListener; 
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.starflask.networking.NetworkUtils.NetworkMessage
import com.starflask.events.GameActionPublisher.CustomGameAction
import com.starflask.events.GameActionQueue

// see https://github.com/jMonkeyEngine/jmonkeyengine/tree/master/jme3-networking/src/main/java/com/jme3/network
class  ClientListener(q: GameActionQueue ) extends MessageListener[Any] {
   var gameActionQueue = q
    
    def messageReceived(x: Any, y: Message) = x match
    {
        case n: NetworkMessage => processAction( rebuildGameActionMessage( n ) ) ; 
         
          case _ => println("No message") ; println(x.getClass())
    }
    
  //   case class ServerListEntryRequestMessage(x: Int)
    
     
      def rebuildGameActionMessage(message: NetworkMessage):CustomGameAction =
    {
       message.action
       
    }
      
      def processAction(action: CustomGameAction)
      {
      
          gameActionQueue.addEvent(action);
      }
     
      
    
 
      
     
}