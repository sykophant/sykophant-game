package com.starflask.peripherals;

import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.starflask.peripherals.GlobalInputEvent.PeripheralType;

public enum InputActionType {
	TOGGLE_CONSOLE(new GlobalInputEvent(PeripheralType.KEYBOARD,KeyInput.KEY_GRAVE)),
	RETURN(new GlobalInputEvent(PeripheralType.KEYBOARD,KeyInput.KEY_RETURN)),
	PRIMARY(new GlobalInputEvent(PeripheralType.MOUSE,MouseInput.BUTTON_LEFT)),
	FORWARD(new GlobalInputEvent(PeripheralType.KEYBOARD,KeyInput.KEY_W)),
	BACKWARD(new GlobalInputEvent(PeripheralType.KEYBOARD,KeyInput.KEY_S)),
	LEFT(new GlobalInputEvent(PeripheralType.KEYBOARD,KeyInput.KEY_A)),
	RIGHT(new GlobalInputEvent(PeripheralType.KEYBOARD,KeyInput.KEY_D)),
	;
	
	
	
	
	
	GlobalInputEvent defaultBinding;
	
	 InputActionType(GlobalInputEvent evt)
	{
		this.defaultBinding = evt;
		
		
	}
	 
	 public GlobalInputEvent getDefaultBinding()
		{
			return defaultBinding;
		}
}
