package com.starflask.peripherals;

import java.util.HashMap;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.KeyInput;
import com.jme3.input.RawInputListener;
import com.jme3.input.event.JoyAxisEvent;
import com.jme3.input.event.JoyButtonEvent;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.input.event.TouchEvent;
import com.starflask.MonkeyApplication;
import com.starflask.peripherals.GlobalInputEvent.PeripheralType;
import com.starflask.states.TerminalState;

public class RawInputManager extends AbstractAppState implements RawInputListener{

	
	 private MonkeyApplication app;

	 HashMap<Integer,InputActionType> mouseBindings;  //controls all input mappings
	 HashMap<Integer,InputActionType> keyboardBindings; 
	 
	 @Override
	    public void initialize(AppStateManager stateManager, Application app) {
	      super.initialize(stateManager, app); 
	      this.app = (MonkeyApplication)app;          // cast to a more specific class
	 
	      
	      buildDefaultBindings();
	      
	      this.app.getTerminalState().log("Booted the input manager");
	   }
	 
	 
	 
	 /**
	  * All actions (mouse or keyboard or whatever) are all just an integer value and then a boolean for up or down
	  * 
	  */
	   private void buildDefaultBindings() {
		   mouseBindings = new HashMap<Integer,InputActionType>();
		   keyboardBindings = new HashMap<Integer,InputActionType>();
		   
		   
		   for(InputActionType actionType: InputActionType.values())
		   {
			   if(actionType.defaultBinding.peripheralType == PeripheralType.KEYBOARD )
			   {
				   keyboardBindings.put(actionType.defaultBinding.keyValue, actionType);  //keys have to be unique
			   }
			   if(actionType.defaultBinding.peripheralType == PeripheralType.MOUSE )
			   {
				   mouseBindings.put(actionType.defaultBinding.keyValue, actionType);  //keys have to be unique
			   }
			   
		   }
		   
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
	@Override
	public void beginInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onJoyAxisEvent(JoyAxisEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onJoyButtonEvent(JoyButtonEvent evt) {
		onGlobalInputEvent(new GlobalInputEvent(PeripheralType.JOYPAD,evt.getButtonIndex()), evt.isPressed());
		
	}

	@Override
	public void onKeyEvent(KeyInputEvent evt) { 
		if(focusedInputElement() != null && focusedInputElement().requestingRawStringInput() )
		{
			focusedInputElement().getRawStringInput().onKeyEvent(evt);
		} 
		 		
		onGlobalInputEvent(new GlobalInputEvent(PeripheralType.KEYBOARD,evt.getKeyCode() ), evt.isPressed());
		 
	}

	@Override
	public void onMouseButtonEvent(MouseButtonEvent evt) {
		onGlobalInputEvent(new GlobalInputEvent(PeripheralType.MOUSE,evt.getButtonIndex() ), evt.isPressed());
		
	}

	@Override
	public void onMouseMotionEvent(MouseMotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTouchEvent(TouchEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void onGlobalInputEvent(GlobalInputEvent evt, boolean pressed)
	{
		 
		 
		InputActionType action = null;
			
		if(evt.peripheralType == PeripheralType.MOUSE)
		{
			action = mouseBindings.get(evt.keyValue);
		}
			
		if(evt.peripheralType == PeripheralType.KEYBOARD)
		{
			action = keyboardBindings.get(evt.keyValue);
		}
	 
		
		if(focusedInputElement() != null)
		{
			focusedInputElement().inputAction( action , pressed);
		}
		
		
		//put global events here
		executeGlobalAction( action , pressed );
		
		
	}
	
	/**
	 * These are special actions that can always execute no matter which element has focus
	 */
	private void executeGlobalAction(InputActionType action, boolean pressed) {
		 if(pressed)
		 {
			 System.out.println("meep " + action );
			 if(action == InputActionType.TOGGLE_CONSOLE)
			 {
				 app.getStateManager().getState(TerminalState.class).toggle();
			 }			 
		 }
		
	}



	/**
	 * This is the element that commands will be redirected to. Only one can be focused on at a time!
	 */
	public InputActionComponent focusedInputElement() 
	{
		return app.getStateManager().getState(TerminalState.class).getComponent(InputActionComponent.class);
		
		
	}
	 

}
