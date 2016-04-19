package com.starflask.states;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.starflask.peripherals.InputActionComponent;
import com.starflask.peripherals.InputActionExecutor;
import com.starflask.peripherals.InputActionType;
import com.starflask.renderable.GuiNodeComponent;
import com.starflask.terminal.TerminalConsoleInterface;
import com.starflask.terminal.TerminalMenu;
import com.starflask.terminal.TerminalRenderer;
import com.starflask.util.EntityAppState;

public class TerminalState extends EntityAppState implements InputActionExecutor{

	
	TerminalRenderer terminalRenderer;
	TerminalConsoleInterface consoleInterface;
	
	
	TerminalMenu terminalMenu;
	
	
	 @Override
	    public void initialize(AppStateManager stateManager, Application app) {
	      super.initialize(stateManager, app); 
	       
	      this.addComponent(new GuiNodeComponent() );
	      this.addComponent(new InputActionComponent( this ));
	      this.getComponent(InputActionComponent.class).getRawStringInput().setActive(true);
	      
	      consoleInterface = new TerminalConsoleInterface( this );
	      consoleInterface.start();
	      
	      terminalRenderer = new TerminalRenderer( getAssetLibrary() );
	      //this.getComponent(GuiNodeComponent.class).attachChild( terminalRenderer.getComponent( GuiNodeComponent.class  )  );
	       
	      
	      terminalRenderer.build();
	      this.getComponent(GuiNodeComponent.class).attachChild( terminalRenderer.getComponent( GuiNodeComponent.class  )  );
	    	 
	      
	      terminalMenu = new TerminalMenu( terminalRenderer );
	       
	      setEnabled(false);
	   }
	 
	 
	 @Override
		public void stateAttached(AppStateManager stateManager) {
		 
						
		}
		 
		 @Override
		public void stateDetached(AppStateManager stateManager) {
				
		}
	 


	 	@Override
	    public void setEnabled(boolean enabled) {
	      // Pause and unpause
	      super.setEnabled(enabled);
	      if(enabled){
	        // init stuff that is in use while this state is RUNNING
	    	  getRootGUINode().attachChild( this.getComponent(GuiNodeComponent.class) );
	         
	      } else {
	        // take away everything not needed while this state is PAUSED
	    	   getRootGUINode().detachChild( this.getComponent(GuiNodeComponent.class) );
	         
	      }
	    }
	 
	 
	 
	@Override
	public void update(float tpf)
	{
		super.update(tpf);
		
		
		
	}


	public void log(String message) {
		System.out.println(message);
		
	}


	@Override
	public void executeInputAction(InputActionType inputAction, boolean pressed) {
		// Register actions here..
		
		
		
	}


	public void processCommand(String cmd)
	{
		terminalMenu.receiveCommand(cmd);
	}


	public void toggle() {
		 setEnabled(!this.isEnabled());
		  
		
	}


	
}
