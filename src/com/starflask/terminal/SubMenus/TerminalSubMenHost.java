package com.starflask.terminal.SubMenus;

public class TerminalSubMenHost extends TerminalSubMen {
	
	
	@Override public void build()
	{
		 
		 
	 
	}
	
	@Override
	public String respondToCommand(String cmd) {
	 
		if(cmd.toLowerCase().startsWith("host"))
		{
			String[] args = cmd.split(" ");
			
			hostWithParams( args );
			
			return render();
		}
		
		return null;
	}
	 

	private void hostWithParams(String[] args) {
		 
		
	}

	public String getTitle()
	{
		return "Host Menu";
	}
	
	public String getDescription()
	{
		return "";
	}
	
	/*
	 * String s = "It was the best of times, it was the worst of times,\n"
         + "it was the age of wisdom, it was the age of foolishness,\n"
         + "it was the epoch of belief, it was the epoch of incredulity,\n"
         + "it was the season of Light, it was the season of Darkness,\n"
         + "it was the spring of hope, it was the winter of despair,\n"
         + "we had everything before us, we had nothing before us";
	 */

}
