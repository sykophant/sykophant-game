package com.starflask.networking

import com.jme3.network.MessageListener; 
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.starflask.networking.NetworkUtils.NetworkMessage 
import com.starflask.events.GameActionPublisher._ 
import com.starflask.util.Vector3f 
import com.starflask.events.GameActionQueue

// see https://github.com/jMonkeyEngine/jmonkeyengine/tree/master/jme3-networking/src/main/java/com/jme3/network
class ServerListener(q: GameActionQueue ) extends MessageListener[Any] {
  
    var gameActionQueue = q
    
    def messageReceived(x: Any, y: Message) = y match  //x is the connection
    {
         case n: NetworkMessage => processAction( rebuildGameActionMessage( n ) ) ; 
         
          case _ => println("No message") ; println(x.getClass())
    }
    
    def rebuildGameActionMessage(message: NetworkMessage):CustomGameAction =
    {
       message.action
       
    }
     
    
 
      
    
     case class ServerListEntryRequestMessage(x: Int)
     
      def processAction(action: CustomGameAction)  
     {
         println("server listener added event");
           gameActionQueue.addEvent( action  );
     }
     
     
   
     /*
     
     def matchList[A: TypeTag](list: List[A]) = list match {
  case strlist: List[String @unchecked] if typeOf[A] =:= typeOf[String] => println("A list of strings!")
  case intlist: List[Int @unchecked] if typeOf[A] =:= typeOf[Int] => println("A list of ints!")
}*/
     
     
    
}