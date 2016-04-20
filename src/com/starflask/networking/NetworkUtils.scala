package com.starflask.networking

import com.jme3.network.Server;
import com.jme3.network.Client;
import com.jme3.network.MessageListener; 
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializer;
import com.jme3.network.serializing.Serializable;

object NetworkUtils {  //like static
  
   
     @Serializable class HelloMessage() extends AbstractMessage  ;
  
   def registerMessageTypes(networkInterface: Any, listener: MessageListener[Any]) = networkInterface match 
  {
        case s: Server => println("hIII");  Serializer.registerClass(classOf[HelloMessage]);   s.addMessageListener( listener , classOf[HelloMessage]);
                  
        case c: Client => Serializer.registerClass(classOf[HelloMessage]);   c.addMessageListener( listener , classOf[HelloMessage]);
                    
        case _ => println("invalid type");
          
            
  }
  
  
  
}