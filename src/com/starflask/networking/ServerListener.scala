package com.starflask.networking

import com.jme3.network.MessageListener; 
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.starflask.networking.NetworkUtils.NetworkMessage 
import com.starflask.events.GameActionPublisher._ 
import com.starflask.util.Vector3f 

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
          case j: JoinServerAction => onJoinServer( j.playerName )  //throw into a queue?
          case _ => "No message"
     }
     
     
     def onJoinServer(playerName: String )
     {
       println("somebody joined our server " + playerName)
       
      
     }
     /*
     
     def matchList[A: TypeTag](list: List[A]) = list match {
  case strlist: List[String @unchecked] if typeOf[A] =:= typeOf[String] => println("A list of strings!")
  case intlist: List[Int @unchecked] if typeOf[A] =:= typeOf[Int] => println("A list of ints!")
}*/
     
     
    
}