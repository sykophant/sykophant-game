package com.starflask.renderable;

import com.badlogic.ashley.core.Component;
import com.jme3.math.Vector3f;

public class PositioningComponent implements Component{
	
		Vector3f pos = new Vector3f();

		public Vector3f getPos() {
			return pos;
		}

		public void setPos(Vector3f pos) {
			this.pos = pos;
		}
		
		
		
}
