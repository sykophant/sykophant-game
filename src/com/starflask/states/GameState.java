package com.starflask.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.starflask.gameinterface.LocalChatManager;
import com.starflask.gameinterface.LocalGameActionManager;
import com.starflask.peripherals.InputActionComponent;
import com.starflask.peripherals.InputActionExecutor;
import com.starflask.peripherals.InputActionType;
import com.starflask.renderable.GuiNodeComponent;
import com.starflask.renderable.NodeComponent;
import com.starflask.starvoxel.VoxelWorld;
import com.starflask.terminal.TerminalConsoleInterface;
import com.starflask.terminal.TerminalMenu;
import com.starflask.terminal.TerminalRenderer;
import com.starflask.util.EntityAppState;

public class GameState extends EntityAppState  {
	
	
	

	 
	VoxelWorld world;
	LocalGameActionManager gameActionManager  ;
	LocalChatManager chatManager;
	
	
	 @Override
	    public void initialize(AppStateManager stateManager, Application app) {
	      super.initialize(stateManager, app); 
	       
	      this.add(new NodeComponent() ); 
	      
	      this.getComponent(InputActionComponent.class).getRawStringInput().setActive(true);
	      
	      world = new VoxelWorld( app );
	      world.build();
	      
		   this.getComponent(NodeComponent.class).attachChild( world.getComponent(NodeComponent.class)  );
		   
	      
	      setEnabled(true);
	      
	      gameActionManager = new LocalGameActionManager();
	      chatManager = new LocalChatManager();
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
		    	  getRootNode().attachChild( this.getComponent(NodeComponent.class) );
		         
		      } else {
		        // take away everything not needed while this state is PAUSED
		    	   getRootNode().detachChild( this.getComponent(NodeComponent.class) );
		         
		      }
		    }
		 	
		 	
		 	
	 
	@Override
	public void update(float tpf)
	{
		super.update(tpf);
		
		world.update(tpf);
		
	}


	public void log(String string) {
		// TODO Auto-generated method stub
		
	}

	 


	public void toggle() {
		 setEnabled(!this.isEnabled());
		  
		
	}


	public InputActionComponent getFocusedInputActionComponent() {
		 if(chatManager.chatIsActive())
		 {
			 chatManager.getComponent(InputActionComponent.class);
		 }
		 
		return gameActionManager.getComponent(InputActionComponent.class); //controls character movement 
	}
	
	

}
