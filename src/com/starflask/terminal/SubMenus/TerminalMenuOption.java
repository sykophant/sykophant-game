package com.starflask.terminal.SubMenus;

public class TerminalMenuOption {
	
	String title;
	
	public TerminalMenuOption(String title) {
		 this.title=title;
	}

	public String render() {
		 
		return title;
	}
	
	
	public void execute(){}

}
