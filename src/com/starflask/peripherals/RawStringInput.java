package com.starflask.peripherals;

import com.jme3.input.event.KeyInputEvent;

public class RawStringInput {
	
		boolean active = false;
		
		String value;
		
		public boolean isActive() { 
			return active;
		}

		public void onKeyEvent(KeyInputEvent evt) {
			 //add the key to the value.. .etc
			
		}

		public void setActive(boolean active) {
			 this.active=active;
			
		}
		
		
}
