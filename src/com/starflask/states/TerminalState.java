package com.starflask.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.starflask.MonkeyApplication;
import com.starflask.peripherals.InputActionComponent;
import com.starflask.peripherals.InputActionExecutor;
import com.starflask.peripherals.InputActionType;
import com.starflask.terminal.TerminalConsoleInterface;
import com.starflask.terminal.TerminalMenu;
import com.starflask.util.EntityAppState; 

public class TerminalState extends EntityAppState implements InputActionExecutor{

	  
	TerminalConsoleInterface consoleInterface;
	TerminalMenu terminalMenu;
	
	 @Override
	    public void initialize(AppStateManager stateManager, Application app) {
	      super.initialize(stateManager, app); 
	        
	      this.addComponent(new InputActionComponent( this ));
	      this.getComponent(InputActionComponent.class).getRawStringInput().setActive(true);
	      
	      consoleInterface = new TerminalConsoleInterface();
	      consoleInterface.start();
	   }
	 
	 
	  


	@Override
	    public void setEnabled(boolean enabled) {
	      // Pause and unpause
	      super.setEnabled(enabled);
	      if(enabled){
	        // init stuff that is in use while this state is RUNNING
	         
	      } else {
	        // take away everything not needed while this state is PAUSED
	         
	      }
	    }
	 
	 
	 
	@Override
	public void update(float tpf)
	{
		super.update(tpf);
		
		
		
	}


	public void log(String string) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void executeInputAction(InputActionType inputAction, boolean pressed) {
		// Register actions here..
		
		
		
	}





	public void toggle() {
		 setEnabled(!this.isEnabled());
		 
		 System.out.println( "toggling terminal state ");
		
	}


	
}
