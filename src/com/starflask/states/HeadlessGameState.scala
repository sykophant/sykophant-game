package com.starflask.states;



import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.starflask.MonkeyApplication;
import com.starflask.assets.AssetLibrary;
import com.starflask.gameinterface.LocalChatManager;
import com.starflask.gameinterface.LocalGameActionManager;
import com.starflask.gameinterface._;
import com.starflask.networking.RemoteClientConnection;
import com.starflask.peripherals.InputActionComponent;
import com.starflask.peripherals.InputActionExecutor;
import com.starflask.peripherals.InputActionType;
import com.starflask.renderable.GuiNodeComponent;
import com.starflask.renderable.NodeComponent; 
import com.starflask.terminal.TerminalConsoleInterface;
import com.starflask.terminal.TerminalMenu;
import com.starflask.terminal.TerminalRenderer;
import com.starflask.util.EntityAppState;
import com.starflask.world.World;
import com.starflask.networking.GameServerProcess



  class HeadlessGameState extends EntityAppState {
	  
	  
	  //This is the gamestate of the server
	 
		var world = new World();
		//var localActionManager  = new LocalGameActionManager();
		
		var serverProcess = new GameServerProcess( world.gameActionExecutor  ); //our network connection with the server 
	
		
		//var chatManager = new LocalChatManager();
		//var characterController = new CharacterController();
		
		 
		
		override def initialize( stateManager: AppStateManager,  app: Application) {
		      super.initialize(stateManager, app); 
		       
		      
		      
		      var thread = new Thread(serverProcess);
          thread.start( );
    
    
		      this.add(new NodeComponent() ); 
		      
		      app match { case a: MonkeyApplication =>  world.build( this.getComponent(classOf[NodeComponent]), a.getAssetLibrary()   ); }
		      //var lib = ((MonkeyApplication) app).getAssetLibrary();
		      
		      
		      
 
		      setEnabled(true);
		    
		        
		   }
		 
		 /*
		 @Override
			public void stateAttached(AppStateManager stateManager) {
			 
							
			}
			 
			 @Override
			public void stateDetached(AppStateManager stateManager) {
					
			}*/
		 


			override def setEnabled(enabled: Boolean) {
			      // Pause and unpause
			      super.setEnabled(enabled);
			      if(enabled){
			        // init stuff that is in use while this state is RUNNING
			    	  getRootNode().attachChild( this.getComponent(classOf[NodeComponent]) );
			         
			      } else {
			        // take away everything not needed while this state is PAUSED
			    	   getRootNode().detachChild( this.getComponent(classOf[NodeComponent]) );
			         
			      }
			    }
			 	
			 	 
		override def update(tpf: Float)
		{
			super.update(tpf);
			
			world.update(tpf);
			
		}

	 

 
	  
	}