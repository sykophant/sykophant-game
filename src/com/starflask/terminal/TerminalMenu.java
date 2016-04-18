package com.starflask.terminal;

public class TerminalMenu {
	
	TerminalRenderer terminalRenderer;
	
	 String randomHelpReminder[] = {"Hey dumbass, try typing 'help'",
			 "Mmmm a fresh noob... Try typing 'help' next time",
			 "You do know you can type 'help'...right?",
			 "What are you trying to do to me?? just type 'help'",
			 };
	
	public TerminalMenu(TerminalRenderer terminalRenderer) {
		this.terminalRenderer=terminalRenderer;
	}


	public void init()
	{
		build();	
		
	}
	

	private void build()
	{
		
		
	}
	
	
	public void receiveCommand(String cmd)
	{
		respondToCommand(cmd);
	}
	
	
	private void respondToCommand(String cmd)
	{
		if(cmd.startsWith("help"))
		{
			print("Help Menu:"+"\n");
			
			
			return;
		}
		
		
		
		
		
		print(randomHelpReminder[ (int)(Math.random()*randomHelpReminder.length) ]);
	}
	
 
	

	public void print(String s)
	{
		terminalRenderer.print(s);
	}
	
	
	
	
}
