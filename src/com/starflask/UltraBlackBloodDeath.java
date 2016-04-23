package com.starflask;

import com.jme3.app.Application;
import com.jme3.app.FlyCamAppState;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
import com.starflask.display.WindowBuilder;
import com.starflask.gameinterface.*;
import com.starflask.states.GameState; 

public class UltraBlackBloodDeath extends MonkeyApplication {

	static WindowBuilder windowBuilder;
	
	public static void main(String args[])
	{
		
		UltraBlackBloodDeath app = new UltraBlackBloodDeath();
		
		//AppSettings settings = new AppSettings(false);
		//settings.setAudioRenderer(AppSettings.JOAL);
		//app.setSettings(settings);  
		
	 //app.start( JmeContext.Type.Headless );
		app.start(JmeContext.Type.Display);
	}

	@Override
	public void simpleInitApp() {
		 
	        
	        this.getStateManager().attach( new  GameState() ) ;
	        
	       
	}
	
	
	
	
		
}
