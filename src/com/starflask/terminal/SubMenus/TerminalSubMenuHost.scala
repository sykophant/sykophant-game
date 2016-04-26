package com.starflask.terminal.SubMenus

import com.starflask.events.CoreEventPublisher.CoreEvent
import com.starflask.events.CoreEventPublisher._  

class TerminalSubMenuHost extends TerminalSubMenu{
  
  
  
	 override def build()
	{
		 
	}
	

	override def  respondToCommand(cmd: String):CoreEvent ={
	 
		if(cmd.toLowerCase().startsWith("host"))
		{
			var args = cmd.split(" "); 
			
			 
			return new HostServerEvent(args);
		}
		 
		
		new NoEvent()
	}
	
	
	 


	override def  getTitle():String=
	{
		 "Host Menu";
	}
	
	override def  getDescription():String=
	{
		 "";
	}
}