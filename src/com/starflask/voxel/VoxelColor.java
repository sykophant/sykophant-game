package com.starflask.voxel;

/**
 * Container for an OpenGL RGBA Color
 * 
 * @author Lane Aasen <laneaasen@gmail.com>
 *
 */

import static org.lwjgl.opengl.GL11.glColor4f;

public class VoxelColor {
	float r, g, b, a;
	
	/**
	 * Contructs a Color with RBGA
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public VoxelColor(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	/**
	 * Constructs a Color with RGB and infers 1.0 for A
	 * 
	 * @param r
	 * @param g
	 * @param b
	 */
	public VoxelColor(float r, float g, float b) {
		this(r, g, b, 1.0f);
	}
	
	/**
	 * Sets the OpenGL Color to this Color
	 */
	public void color() {
		glColor4f(this.r, this.g, this.b, this.a);
	}
}
