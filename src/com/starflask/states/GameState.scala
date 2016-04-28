package com.starflask.states


import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.starflask.MonkeyApplication;
import com.starflask.assets.AssetLibrary;
import com.starflask.gameinterface.LocalChatManager;
import com.starflask.gameinterface.LocalGameActionManager; 
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
import com.starflask.gameinterface.CharacterController
import com.starflask.events.GameActionPublisher.CustomGameAction;

class  GameState extends EntityAppState {
   
 
	var world = new World();
	var localActionManager  = new LocalGameActionManager();
	var chatManager = new LocalChatManager();
	var characterController = new CharacterController();
	
	var remoteClientConnection = new RemoteClientConnection( world.gameActionExecutor  ); //our network connection with the server 
	
	override def initialize( stateManager: AppStateManager,  app: Application) {
	      super.initialize(stateManager, app); 
	      
	      
	      
	      var clientConnection = new Thread( remoteClientConnection)
	      clientConnection.start() 
	      
	       
	      this.add(new NodeComponent() ); 
	      
	      app match { case a: MonkeyApplication =>  world.build( this.getComponent(classOf[NodeComponent]), a.getAssetLibrary()   ); }
	      //var lib = ((MonkeyApplication) app).getAssetLibrary();
	      
	      	      
		   this.getComponent(classOf[NodeComponent]).attachChild( world.getComponent(classOf[NodeComponent])  );
		   
	      
	      setEnabled(true);
	      
	   
	      localActionManager.setReactiveGameData(world.gamedata)    //(this is bad practice) we do this so it can combine actions with the world state data to then send info to the server 
	     
       //send local actions (like pressing the FIRE button) to the remoteClientConnection
	      localActionManager.actionPublisher.subscribe( (ev)  =>  remoteClientConnection.gameActionQueue.addEvent(ev)  )   //this should work now
	     
	    localActionManager.actionPublisher.subscribe( (ev)  =>  characterController.gameActionQueue.addEvent(ev)  )
	        
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
		 	
		 	
		 	/*   Chain of events...
		 	 *   
		 	 *   When a movement button like FORWARD is pressed or the mouse is moved, the charcters pos is instantly changed for self 
		 	 *   
		 	 *   When a button like FIRE is pressed, this is a GameAction and will be put into a queue-list of game actions
		 	 *   
		 	 *   
		 	 *   Every network tick, the selfs character pos and rotation is sent to the server
		 	 *   Also, the queued gameactions are flushed and sent to the server where they execute. They are also simulated on the client (just animation).
		 	 * 
		 	 * 
		 	 * 
		 	 * 
		 	 * 
		 	 */
	  
	override def update(tpf: Float)
	{
		super.update(tpf);
		
		world.update(tpf);
		
	}

 


	def  getFocusedInputActionComponent() = {
		 if(chatManager.chatIsActive())
		 {
			   chatManager.getComponent(classOf[InputActionComponent]);
		 }
		 
		  localActionManager.getComponent(classOf[InputActionComponent]); //controls character movement 
	}
  
}