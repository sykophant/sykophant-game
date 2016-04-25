package com.starflask.terminal.SubMenus;

public class TerminalSubMenHelp extends TerminalSubMen {
	
	
	@Override public void build()
	{
		/*menuOptions.add(new TerminalMenuOption("First Option"){
			
			@Override
			public void execute(){
				System.out.println("Do a cool thing!");
			}
			
		});*/
		menuOptions.add(new TerminalMenuOpti("Host - Host a new match"));
		menuOptions.add(new TerminalMenuOpti("Join - Join a multiplayer match using a remote or local IP address"));
		menuOptions.add(new TerminalMenuOpti("List - Display a list of public matches to join"));
		menuOptions.add(new TerminalMenuOpti("Library - Display a list of your blackprints"));
		menuOptions.add(new TerminalMenuOpti("Get - Find and download custom maps and blackprints"));
		menuOptions.add(new TerminalMenuOpti("Edit - Enter the map editor"));
		
	//	menuOptions.add(new TerminalMenuOption("bots - Add AI bots to a match you are hosting"));
	}
	
	@Override
	public String respondToCommand(String cmd) {
	 
		if(cmd.toLowerCase().startsWith("help"))
		{
			return render();
		}
		
		return null;
	}
	
	
	


	public String getTitle()
	{
		return "Help Menu";
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
