package com.starflask.terminal.SubMenus;

import java.util.ArrayList;
import java.util.List;

public abstract class TerminalSubMenu {
	
	TerminalSubMenu()
	{
		build();
	}
	
	protected String render() { 

		String output = "-- "+getTitle()+ " --" + "\n";
		
		if(getDescription() !=null && getDescription().length() > 0)
		{
			output+= getDescription() + "\n";
		}
		
		for(int i=0;i <  menuOptions.size();i++)
		{
			TerminalMenuOption option = menuOptions.get(i);
			
			output += (i+1) +". "+option.render() + "\n";
		}
		
		output+= "\n";
		
		return output;
	}
	
	
	List<TerminalMenuOption> menuOptions = new ArrayList<TerminalMenuOption>();

	public abstract String respondToCommand(String cmd);
	public abstract void build();
	public abstract String getTitle();
	public abstract String getDescription();

}
