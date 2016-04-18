package com.starflask.terminal;

import com.badlogic.ashley.core.Entity;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.starflask.assets.AssetLibrary;
import com.starflask.renderable.GuiNodeComponent;
import com.starflask.renderable.gui.GUIFilledRect;

public class TerminalRenderer extends Entity{
	
	//component for node to attach
	
	
	
	AssetLibrary assetLibrary;
	
	GUIFilledRect background;
	
	public TerminalRenderer(AssetLibrary assetLibrary)
	{
		this.assetLibrary=assetLibrary;
		this.add(new GuiNodeComponent());
		
		
		 
	}
	
	public void build()
	{
		Material backgroundMat = assetLibrary.findMaterial("console_background");

		
		
		background = new GUIFilledRect(100,100,new ColorRGBA(0,0,0,0.9f));
		background.build(backgroundMat);		
		getNode().attachChild(background); 
		
		System.out.println( "term render pos " + getNode().getWorldTranslation() );
		
		
		BitmapFont guiFont = assetLibrary.findFont("console_font");
		
		BitmapText terminalText = new BitmapText(guiFont, false);          
		terminalText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
		terminalText.setColor(ColorRGBA.White);                             // font color
		terminalText.setText("You can write any string here");             // the text
		terminalText.setLocalTranslation(300, terminalText.getLineHeight(), 0); // position
		getNode().attachChild(terminalText);
		
		
	}
	
	
	
	private Node getNode() { 
		return this.getComponent(GuiNodeComponent.class);
	}

	public void print(String s)
	{
		//also print this out on a textbox or whatever
		
		System.out.print(s);
	}
	

}
