package com.starflask.util;

import com.jme3.math.Vector3f;
import com.starflask.renderable.PositioningComponent;

public class Vector3Int {
		public int x;
		public int y;
		public int z;

		public Vector3Int(int x, int y, int z) {
			this.x=x;
			this.y=y;
			this.z=z;
		}

		public Vector3Int() {
			// TODO Auto-generated constructor stub
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getZ() {
			return z;
		}

		public void setZ(int z) {
			this.z = z;
		}

		public void set(int x, int y, int z) {
			this.x=x;
			this.y=y;
			this.z=z;
		}

		public Vector3f toVector3f() {
		 
			return new Vector3f(x,y,z);
		}

		
		
		
}
