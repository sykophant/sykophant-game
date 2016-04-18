package com.starflask.renderable.gui;

import com.jme3.scene.Node;
import com.starflask.MonkeyApplication;
import com.starflask.util.Vector2Int;

/**
 * 
 * Has special rendering techniques to be more like html css
 *  
 *
 */
public class GUINode extends Node{

	Vector2Int padding = new Vector2Int();
	Vector2Int margin = new Vector2Int();
	
	
	
	
	
	public Vector2Int getDisplayDimensions()
	{
		return MonkeyApplication.getAdaptiveDisplay().getDimensions();
	}
}
