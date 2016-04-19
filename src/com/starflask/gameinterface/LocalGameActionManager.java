package com.starflask.gameinterface;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.starflask.peripherals.InputActionComponent;
import com.starflask.peripherals.InputActionExecutor;
import com.starflask.peripherals.InputActionType;

public class LocalGameActionManager extends Entity   implements InputActionExecutor{

	public LocalGameActionManager()
	{
		this.add(new InputActionComponent( this ));
	}

	@Override
	public void executeInputAction(InputActionType inputAction, boolean pressed) {
		// TODO Auto-generated method stub
		
	} 
	

}
