package com.starflask.peripherals;

import com.badlogic.ashley.core.Component;
import com.jme3.input.RawInputListener;


/*
 * 
 * 
 * 
 */
public class InputActionComponent implements Component  {

	
	InputActionExecutor actionExecutor;
	
	public InputActionComponent(InputActionExecutor actionExecutor) {
		registerActionExecutor(actionExecutor);
	}

	public void inputAction(InputActionType inputAction, boolean pressed)
	{
		actionExecutor.executeInputAction(inputAction, pressed);
	}
	
	public void registerActionExecutor(InputActionExecutor actionExecutor)
	{
		this.actionExecutor=actionExecutor;
	}
	
	
	RawStringInput rawStringInput = new RawStringInput(); //for terminal and ingame chat

	public boolean requestingRawStringInput() {   
		 
		return rawStringInput.isActive();
	}

	public RawStringInput getRawStringInput() {
		 
		return rawStringInput;
	}
	
	

}
