package com.starflask.terminal.SubMenus

abstract class TerminalSubMenu {
  
  
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
	
	
	var menuOptions : List[TerminalMenuOption];

	  def  respondToCommand( cmd:String):String ;
	def  build();
	def   getTitle():String;
	def   getDescription():String;
	
	
}