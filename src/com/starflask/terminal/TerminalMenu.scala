package com.starflask.terminal

import com.starflask.events.CoreEventPublisher
import com.starflask.terminal.SubMenus.TerminalSubMenu
import com.starflask.terminal.SubMenus._
import com.starflask.events.CoreEventPublisher.CoreEvent
import com.starflask.events.CoreEventPublisher.NoEvent
import com.starflask.events.CoreEventPublisher.TerminalRenderEvent

class TerminalMenu( ) {
  
  var subMenus =   List[TerminalSubMenu](new TerminalSubMenuHelp(), new TerminalSubMenuHost(),new TerminalSubMenuJoin());
	
	var coreEventPublisher = new CoreEventPublisher();
			 
	//var terminalRenderer = tr;
	
	 val randomHelpReminder  =  Array("Hey dumbass, try typing 'help'"+"\n",
			 "Mmmm a fresh noob... Try typing 'help' next time"+"\n",
			 "You do know you can type 'help'...right?"+"\n",
			 "What are you trying to do to me?? just type 'help'"+"\n" 
			 );
	

	 
	 def TerminalMenu(  ) { 
		
		build();
	}

 
	

	  def  build()
	{
		 
		
	}
	
	
	  def receiveCommand(cmd: String )
	{
		respondToCommand(cmd.toLowerCase());
	}
	
	
	private def respondToCommand(cmd: String )
	{
		//for(TerminalSubMenu sm: subMenus)
	  for( sm <- subMenus  )
		{
			var response = sm.respondToCommand(cmd);
			 
			response match   {
			  case n : NoEvent => print("no event");
			  case ev : CoreEvent => print(ev.toString() ); coreEventPublisher.publish(ev); return;
			  
			}
			
			/*if(response != null &&  !(response instanceof NoEvent))
			{
				print(response.toString());
				 
				coreEventPublisher.publish(response);  //should do this in scala
				//publish response 
				return;
			} */
			
			
			
		}
		 
		
		
		//print(randomHelpReminder[ (Math.random()*randomHelpReminder.length).toInt ]);
	}
	
 
	

	  def print(s: String )
	{
		//terminalRenderer.print(s);
		coreEventPublisher.publish(new TerminalRenderEvent( s  ));
	}
	
	
	
	
	
}