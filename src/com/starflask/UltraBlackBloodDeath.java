package com.starflask;

import com.jme3.app.Application;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
import com.starflask.gameinterface.*;

public class UltraBlackBloodDeath extends MonkeyApplication {

	static WindowBuilder windowBuilder;
	
	public static void main(String args[])
	{
		
		UltraBlackBloodDeath app = new UltraBlackBloodDeath();
		
		//AppSettings settings = new AppSettings(false);
		//settings.setAudioRenderer(AppSettings.JOAL);
		//app.setSettings(settings);  
		
		app.start();
	}

	@Override
	public void simpleInitApp() {
		  Box b = new Box(1, 1, 1);
	        Geometry geom = new Geometry("Box", b);

	        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
	        mat.setColor("Color", ColorRGBA.Blue);
	        geom.setMaterial(mat);
	        
	        
	         

	        rootNode.attachChild(geom);
		
	}
	
	
	
	
		
}
