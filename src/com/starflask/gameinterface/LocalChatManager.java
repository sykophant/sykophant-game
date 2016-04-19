package com.starflask.gameinterface;

import com.badlogic.ashley.core.Entity;
import com.starflask.peripherals.InputActionComponent;
import com.starflask.peripherals.InputActionExecutor;
import com.starflask.peripherals.InputActionType;


/*
 * When the chat window is open, this becomes the 'focus' action executor for the keyboard and mouse inputs.
 * 
 * 
 */
public class LocalChatManager extends Entity   implements InputActionExecutor{

	public LocalChatManager()
	{
	 this.add(new InputActionComponent( this ));
	 this.getComponent(InputActionComponent.class).getRawStringInput().setActive(true);
	}

	@Override
	public void executeInputAction(InputActionType inputAction, boolean pressed) {
		// TODO Auto-generated method stub
		
	}

	public boolean chatIsActive() {
		// TODO Auto-generated method stub
		return false;
	} 
	

}
