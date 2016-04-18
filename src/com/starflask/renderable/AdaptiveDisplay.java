package com.starflask.renderable;

import org.lwjgl.opengl.Display;

import com.starflask.util.Vector2Int;

public class AdaptiveDisplay {

	
	public AdaptiveDisplay()
	{
		 setDimensions(  Display.getWidth(),  Display.getHeight());
		  
	}
	
	Vector2Int dimensions = new Vector2Int();
		
	public Vector2Int getDimensions() {
		return dimensions;
	}

	public void setDimensions(int width, int height) {
		dimensions.x = width;
		dimensions.y = height;
		
	}

}
