package com.starflask.terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.starflask.states.TerminalState;

public class TerminalConsoleInterface {
	
	
	ConsoleInputListener consoleInputListener;
	
	TerminalState terminalState;
	
	public TerminalConsoleInterface(TerminalState terminalState)
	{
		this.terminalState=terminalState;
	}

	public void start() {
		consoleInputListener = new ConsoleInputListener(this);
		
		Thread consoleListener = new Thread(consoleInputListener);  
		consoleListener.start();
		    
		    
		     
	}

	public void inputConsoleString(String s) {
		if(s!=null)
		{ 
			terminalState.processCommand(s);
		}
		
	}
	
	
	class ConsoleInputListener implements Runnable
	{
		BufferedReader br;
		TerminalConsoleInterface terminalConsoleInterface;

		public ConsoleInputListener(TerminalConsoleInterface terminalConsoleInterface) {
			 this.terminalConsoleInterface=terminalConsoleInterface;
		}

		@Override
		public void run() {
			 br = new BufferedReader(new InputStreamReader(System.in));
			 
			 while(true)
			 {
				 
				 
				 
				 
				 
			        try {
						String s = br.readLine();
						
						terminalConsoleInterface.inputConsoleString( s );
					} catch (IOException e) {
						e.printStackTrace();
					}
				 
			        
			 }
			
		}
		
	}





}
