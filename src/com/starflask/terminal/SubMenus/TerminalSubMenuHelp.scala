package com.starflask.terminal.SubMenus

import com.starflask.events.CoreEventPublisher.CoreEvent
import com.starflask.events.CoreEventPublisher._
import com.starflask.terminal.SubMenus.TerminalMenuOption

class TerminalSubMenuHelp extends TerminalSubMenu{
  
  
  
	 override def build()
	{
		/*menuOptions.add(new TerminalMenuOption("First Option"){
			
			@Override
			public void execute(){
				System.out.println("Do a cool thing!");
			}
			
		});*/
		menuOptions.+:( new TerminalMenuOption("Host - Host a new match"));
		menuOptions.+:(new TerminalMenuOption("Join - Join a multiplayer match using a remote or local IP address"));
		menuOptions.+:(new TerminalMenuOption("List - Display a list of public matches to join"));
		menuOptions.+:(new TerminalMenuOption("Library - Display a list of your blackprints"));
		menuOptions.+:(new TerminalMenuOption("Get - Find and download custom maps and blackprints"));
		menuOptions.+:(new TerminalMenuOption("Edit - Enter the map editor"));
		
	//	menuOptions.add(new TerminalMenuOption("bots - Add AI bots to a match you are hosting"));
	}
	

	override def  respondToCommand(cmd: String):CoreEvent ={
	 
		if(cmd.toLowerCase().startsWith("help"))  
		{
			  render();
		}
		
		new NoEvent()
	}
	
	
	


	override def  getTitle():String=
	{
		 "Help Menu";
	}
	
	override def  getDescription():String=
	{
		 "";
	}
}