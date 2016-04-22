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
  
   
     //make this a tuple of twos ...??
     val allMessageTypes = List(classOf[NetworkMessage],classOf[ JoinServerAction ], classOf[ MoveAction ])
     

   def registerMessageTypes(networkInterface: Any, listener: MessageListener[Any]) = networkInterface match 
  {
        //case s: Server => Serializer.registerClass(classOf[NetworkMessage]);   s.addMessageListener( listener , classOf[NetworkMessage]);
        case s: Server => allMessageTypes.map{t => Serializer.registerClass( t ) } ; allMessageTypes.map{t => s.addMessageListener( listener , t) } ;  
                  
        case c: Client => allMessageTypes.map{t => Serializer.registerClass( t   ) } ; allMessageTypes.map{t => c.addMessageListener( listener , t) } ;  
                    
        case _ => println("invalid type"); 
            
  }
     
     ///ARGGGGGG 
      //make a diff serializer for each message type 
     
     
     /*case class CustomSerializer[T <: Object :ClassTag](  factory: () => T) extends Serializer  {

        
     val x = classOf[JoinServerAction]
     
    override def   readObject( data:ByteBuffer,  c:Class[T] ):  T  =  c.getClass  match{
        case x =>  factory() 
    }

     override def  writeObject(buffer: ByteBuffer , o: Object) = o.getClass match  {
       
       case j   =>  buffer.putLong(1)
       
       
        /*UUID uuid = (UUID) object;
        
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());*/
    }

}*/
   
    
   
}
   