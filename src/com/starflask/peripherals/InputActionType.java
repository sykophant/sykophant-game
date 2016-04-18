package com.starflask.peripherals;

import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.starflask.peripherals.GlobalInputEvent.PeripheralType;

public enum InputActionType {
	TOGGLE_CONSOLE(new GlobalInputEvent(PeripheralType.KEYBOARD,KeyInput.KEY_GRAVE)),
	RETURN(new GlobalInputEvent(PeripheralType.KEYBOARD,KeyInput.KEY_RETURN)),
	PRIMARY(new GlobalInputEvent(PeripheralType.MOUSE,MouseInput.BUTTON_LEFT)),
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
