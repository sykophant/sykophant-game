package com.starflask.terminal.SubMenus

class TerminalMenuOption {
  var title = new String("no title");
	
	def TerminalMenuOption( title:String) {
		 this.title=title;
	}

	def  render():String = {
		 
		 title 
	}
	
	
	def  execute(){}

}