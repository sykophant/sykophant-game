package com.starflask.terminal.SubMenus

import com.starflask.events.CoreEventPublisher.CoreEvent
import com.starflask.events.CoreEventPublisher._  

class TerminalSubMenuJoin extends TerminalSubMenu{
  
  
  
	 override def build()
	{
		 
	}
	

	override def  respondToCommand(cmd: String):CoreEvent ={
	 
		if(cmd.toLowerCase().startsWith("join"))
		{
			var args = cmd.split(" "); 
			
			return new JoinServerEvent(args);
		}
		 
		
		new NoEvent()
	}
	
	
	 


	override def  getTitle():String=
	{
		 "Join Menu";
	}
	
	override def  getDescription():String=
	{
		 "";
	}
}