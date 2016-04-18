package com.starflask.peripherals;

public class GlobalInputEvent {

	PeripheralType peripheralType;
	int keyValue; 
	
	public GlobalInputEvent(PeripheralType peripheralType,	int keyValue )
	{
		this.peripheralType=peripheralType;
		this.keyValue=keyValue;
	}
	
	public enum PeripheralType
	{
		MOUSE,
		JOYPAD,
		KEYBOARD	
		
	}
	
	@Override
	public boolean equals(Object otherEvt)
	{ 
		return this.keyValue == ((GlobalInputEvent)otherEvt).keyValue && this.peripheralType == ((GlobalInputEvent)otherEvt).peripheralType;
		 
	}
}
