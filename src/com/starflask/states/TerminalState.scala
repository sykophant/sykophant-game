package com.starflask.states

import com.jme3.app.state.AppStateManager
import com.starflask.peripherals.InputActionType
import com.starflask.renderable.GuiNodeComponent
import com.starflask.peripherals.InputActionComponent
import com.starflask.util.EntityAppState
import com.starflask.peripherals.InputActionExecutor
import com.starflask.terminal.TerminalRenderer
import com.starflask.terminal.TerminalConsoleInterface

import com.jme3.app.Application;
import com.starflask.terminal.TerminalMenu

class TerminalState extends EntityAppState with InputActionExecutor {
  
	
	//TerminalRenderer terminalRenderer;
	//TerminalConsoleInterface consoleInterface;
	
	
	//TerminalMenu terminalMenu;
	var terminalMenu = new TerminalMenu();
	
	  override def initialize(stateManager: AppStateManager , app: Application ) {
	      super.initialize(stateManager, app); 
	       
	      this.add(new GuiNodeComponent() );
	      this.add(new InputActionComponent( this ));
	      this.getComponent(classOf[InputActionComponent]).getRawStringInput().setActive(true);
	      
	      val consoleInterface = new TerminalConsoleInterface( this );
	      consoleInterface.start();
	      
	      val terminalRenderer = new TerminalRenderer( getAssetLibrary() );
	      //this.getComponent(GuiNodeComponent.class).attachChild( terminalRenderer.getComponent( GuiNodeComponent.class  )  );
	       
	      // terminalRenderer.print on coreevent
	      
	      terminalRenderer.build();
	      this.getComponent(classOf[GuiNodeComponent]).attachChild( terminalRenderer.getComponent( classOf[GuiNodeComponent]  )  );
	    	 
	       
	      terminalMenu.build()
	       
	       
	      setEnabled(false);
	   }
	 
	 
	 override def  stateAttached(stateManager: AppStateManager ) {
		 
						
		}
		 
		 override def stateDetached(stateManager: AppStateManager ) {
				
		}
	 


	 	override def setEnabled(enabled: Boolean ) {
	      // Pause and unpause
	      super.setEnabled(enabled);
	      if(enabled){
	        // init stuff that is in use while this state is RUNNING
	    	  getRootGUINode().attachChild( this.getComponent(classOf[GuiNodeComponent]) );
	         
	      } else {
	        // take away everything not needed while this state is PAUSED
	    	   getRootGUINode().detachChild( this.getComponent(classOf[GuiNodeComponent]) );
	         
	      }
	    }
	 
	 
	 
	override def update(tpf: Float)
	{
		super.update(tpf);
		
		
		
	}


	def log(message: String ) {
		System.out.println(message);
		
	}

 
	override def executeInputAction(inputAction: InputActionType ,pressed: Boolean ) {
		// Register actions here..
		
		
		
	}


	  def processCommand(cmd: String )
	{
		terminalMenu.receiveCommand(cmd);
	
	}


	  def toggle() {
		 setEnabled(!this.isEnabled());
		  
		
	}


}