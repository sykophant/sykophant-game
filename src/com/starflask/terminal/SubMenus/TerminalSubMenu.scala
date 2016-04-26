package com.starflask.terminal.SubMenus

import com.starflask.events.CoreEventPublisher.CoreEvent
import com.starflask.events.CoreEventPublisher.NoEvent

 class TerminalSubMenu {
  
  
	var menuOptions = List[TerminalMenuOption]() 
	
  def TerminalSubMenu()
	{
		build();
	}
	
	protected def render():String = { 

		var output = "-- "+getTitle()+ " --" + "\n";
		
		if(getDescription() !=null && getDescription().length() > 0)
		{
			output+= getDescription() + "\n";
		}
		
		for(i <- 0 to menuOptions.size )
		{
			var option = menuOptions.lift(i)
			
			option match
			{
			  case m:Some[TerminalMenuOption] =>	output += (i+1) +". "+  m.get.render() + "\n";
			  case _ => println("Missing menu option");
			}
		}
		
		output+= "\n";
		
	   output;
	}
	
	

	  def  respondToCommand( cmd:String):CoreEvent = new NoEvent;
	def  build():Unit = Unit;
	def   getTitle():String = "No Title";
	def   getDescription():String = "No Description";
	
	
}