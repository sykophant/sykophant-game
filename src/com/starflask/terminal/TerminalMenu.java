package com.starflask.terminal;

import java.util.ArrayList;
import java.util.List;

import com.starflask.terminal.SubMenus.TerminalSubMenu;
import com.starflask.terminal.SubMenus.TerminalSubMenuHelp;

public class TerminalMenu {
	
	List<TerminalSubMenu> subMenus = new ArrayList<TerminalSubMenu>();
	
	
	TerminalRenderer terminalRenderer;
	
	 String randomHelpReminder[] = {"Hey dumbass, try typing 'help'"+"\n",
			 "Mmmm a fresh noob... Try typing 'help' next time"+"\n",
			 "You do know you can type 'help'...right?"+"\n",
			 "What are you trying to do to me?? just type 'help'"+"\n",
			 };
	
	public TerminalMenu(TerminalRenderer terminalRenderer) {
		this.terminalRenderer=terminalRenderer;
		
		build();
	}

 
	

	private void build()
	{
		subMenus.add( new TerminalSubMenuHelp() );
		
		
	}
	
	
	public void receiveCommand(String cmd)
	{
		respondToCommand(cmd);
	}
	
	
	private void respondToCommand(String cmd)
	{
		for(TerminalSubMenu sm: subMenus)
		{
			String response = sm.respondToCommand(cmd);
			
			if(response != null )
			{
				print(response);
				return;
			} 
		}
		 
		
		
		print(randomHelpReminder[ (int)(Math.random()*randomHelpReminder.length) ]);
	}
	
 
	

	public void print(String s)
	{
		terminalRenderer.print(s);
	}
	
	
	
	
	
	
}
