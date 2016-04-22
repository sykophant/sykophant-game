package com.starflask.networking

import com.jme3.network.Server;
import com.jme3.network.Client;
import com.jme3.network.MessageListener; 
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializer;
import com.jme3.network.serializing.Serializable;
import com.starflask.gameinterface.GameActionPublisher.CustomGameAction
import com.starflask.gameinterface.GameActionPublisher._
import java.nio.ByteBuffer

import scala.reflect.ClassTag

object NetworkUtils {  //like static
  
   
     @Serializable class NetworkMessage(a: CustomGameAction ) extends AbstractMessage  
     {
        
        
        var action = a
        
         def this() {
            this(  NoAction( Map()   ))
          }  
        
     }
  
    
     val allMessageTypes = List(classOf[NetworkMessage],classOf[ JoinServerAction ], classOf[ MoveAction ])
     

   def registerMessageTypes(networkInterface: Any, listener: MessageListener[Any]) = networkInterface match 
  {
         case s: Server => allMessageTypes.map{t => Serializer.registerClass( t ) } ; allMessageTypes.map{t => s.addMessageListener( listener , t) } ;  
                  
        case c: Client => allMessageTypes.map{t => Serializer.registerClass( t   ) } ; allMessageTypes.map{t => c.addMessageListener( listener , t) } ;  
                    
        case _ => println("invalid type"); 
            
  }
    
   
    
   
}
   